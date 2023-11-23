package com.hs.hoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.hoj.common.ErrorCode;
import com.hs.hoj.constant.CommonConstant;
import com.hs.hoj.exception.BusinessException;
import com.hs.hoj.judge.judgeSevice.JudgeService;
import com.hs.hoj.model.dto.question.QuestionQueryRequest;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionQueryRequest;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionRequest;
import com.hs.hoj.model.entity.Question;
import com.hs.hoj.model.entity.SubmitQuestion;
import com.hs.hoj.model.entity.User;
import com.hs.hoj.model.enums.SubmitLuguageEnum;
import com.hs.hoj.model.enums.SubmitStatueEnum;
import com.hs.hoj.model.vo.QuestionVO;
import com.hs.hoj.model.vo.SubmitQuestionVO;
import com.hs.hoj.model.vo.UserVO;
import com.hs.hoj.service.QuestionService;
import com.hs.hoj.service.SubmitQuestionService;
import com.hs.hoj.mapper.SubmitQuestionMapper;
import com.hs.hoj.service.UserService;
import com.hs.hoj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author haoshuai
 * @description 针对表【submit_question(题目提交表)】的数据库操作Service实现
 * @createDate 2023-10-21 12:59:14
 */
@Service
public class SubmitQuestionServiceImpl extends ServiceImpl<SubmitQuestionMapper, SubmitQuestion>
        implements SubmitQuestionService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private JudgeService judgeService;

    /**
     * 題目提交
     *
     * @param submitQuestionRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long SubmitQuestion(SubmitQuestionRequest submitQuestionRequest, User loginUser) {
        String language = submitQuestionRequest.getLanguage();
        SubmitLuguageEnum enumByValue = SubmitLuguageEnum.getEnumByValue(language);
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }


        // 判断实体是否存在，根据类别获取題目实体
        Question question = questionService.getById(submitQuestionRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        long userId = loginUser.getId();


        String code = submitQuestionRequest.getCode();
        Long questionId = submitQuestionRequest.getQuestionId();


        SubmitQuestion submitQuestion = new SubmitQuestion();
        submitQuestion.setUserId(userId);
        submitQuestion.setQuestionId(questionId);
        submitQuestion.setCode(code);
        submitQuestion.setLanguage(language);
        submitQuestion.setStatus(SubmitStatueEnum.WAUTTING.getValue());
        submitQuestion.setJudgeInfo("{}");
        boolean save = save(submitQuestion);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交失败");
        }
        Long submitQuestionId = submitQuestion.getId();

        //判题服务
        judgeService.doJudge(submitQuestionId);
        return submitQuestionId;
    }


    /**
     * 获取查询包装类
     *
     * @param submitQuestionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<SubmitQuestion> getQueryWrapper(SubmitQuestionQueryRequest submitQuestionQueryRequest) {
        QueryWrapper<SubmitQuestion> queryWrapper = new QueryWrapper<>();
        if (submitQuestionQueryRequest == null) {
            return queryWrapper;
        }

        String language = submitQuestionQueryRequest.getLanguage();
        Integer status = submitQuestionQueryRequest.getStatus();
        Long questionId = submitQuestionQueryRequest.getQuestionId();
        Long userId = submitQuestionQueryRequest.getUserId();
        long current = submitQuestionQueryRequest.getCurrent();
        long pageSize = submitQuestionQueryRequest.getPageSize();
        String sortField = submitQuestionQueryRequest.getSortField();
        String sortOrder = submitQuestionQueryRequest.getSortOrder();
        queryWrapper.like(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_DESC),
                sortField);
        return queryWrapper;
    }


    @Override
    public SubmitQuestionVO getSubmitQuestionVO(SubmitQuestion submitQuestion, User loginuser) {
        SubmitQuestionVO submitQuestionVO = SubmitQuestionVO.objToVo(submitQuestion);
        // 1. 关联查询用户信息
        Long userId = submitQuestion.getUserId();
        //权限判断
        if (userId != loginuser.getId() || !userService.isAdmin(loginuser)) {
            submitQuestionVO.setCode(null);
        }
        submitQuestionVO.setUserId(userId);
        return submitQuestionVO;
    }

    @Override
    public Page<SubmitQuestionVO> getSubmitQuestionVOPage(Page<SubmitQuestion> submitQuestionPage, User loginuser) {
        List<SubmitQuestion> submitQuestions = submitQuestionPage.getRecords();
        Page<SubmitQuestionVO> submitQuestionVOPage = new Page<>(submitQuestionPage.getCurrent(), submitQuestionPage.getSize(), submitQuestionPage.getTotal());
        if (CollectionUtils.isEmpty(submitQuestions)) {
            return submitQuestionVOPage;
        }
        // // 1. 关联查询用户信息
        // Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        // Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
        //         .collect(Collectors.groupingBy(User::getId));

        // 填充信息
        List<SubmitQuestionVO> submitQuestionVOList = submitQuestions.stream().map(submitquestion -> {
            /*Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUser(userService.getUserVO(user));*/
            return this.getSubmitQuestionVO(submitquestion, loginuser);
        }).collect(Collectors.toList());
        submitQuestionVOPage.setRecords(submitQuestionVOList);
        return submitQuestionVOPage;
    }


    /**
     * 封装了事务的方法  
     * @param userId
     * @param questionId
     * @return
     */
   /* @Override
    @Transactional(rollbackFor = Exception.class)
    public int SubmitQuestionInner(long userId, long questionId) {
        SubmitQuestion submitQuestion = new SubmitQuestion();
        submitQuestion.setUserId(userId);
        submitQuestion.setQuestionId(questionId);
        QueryWrapper<SubmitQuestion> thumbQueryWrapper = new QueryWrapper<>(submitQuestion);
        SubmitQuestion oldPostThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        if (oldPostThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {

                result = questionService.update()
                        .eq("id", questionId)
                        .gt("thumbNum", 0)
                        .setSql("thumbNum = thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {

            result = this.save(submitQuestion);
            if (result) {
                result = questionService.update()
                        .eq("id", questionId)
                        .setSql("thumbNum = thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }*/
}




