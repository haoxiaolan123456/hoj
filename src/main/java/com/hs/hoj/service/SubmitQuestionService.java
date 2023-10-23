package com.hs.hoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.hoj.model.dto.question.QuestionQueryRequest;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionQueryRequest;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionRequest;
import com.hs.hoj.model.entity.Question;
import com.hs.hoj.model.entity.SubmitQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.hoj.model.entity.User;
import com.hs.hoj.model.vo.QuestionVO;
import com.hs.hoj.model.vo.SubmitQuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haoshuai
 * @description 针对表【submit_question(题目提交表)】的数据库操作Service
 * @createDate 2023-10-21 12:59:14
 */
public interface SubmitQuestionService extends IService<SubmitQuestion> {
    /**
     * 題目提交
     *
     * @param submitQuestionRequest
     * @param loginUser
     * @return
     */
    Long SubmitQuestion(SubmitQuestionRequest submitQuestionRequest, User loginUser);




    /**
     * 获取查询条件
     *
     * @param submitQuestionQueryRequest
     * @return
     */
    QueryWrapper<SubmitQuestion> getQueryWrapper(SubmitQuestionQueryRequest submitQuestionQueryRequest);


    /**
     * 获取題目封装
     * @param submitQuestion
     * @param request
     * @return
     */
    SubmitQuestionVO getSubmitQuestionVO(SubmitQuestion submitQuestion, User loginuser);

    /**
     * 分页获取題目封装
     * @param SubmitQuestionPage
     * @param request
     * @return
     */
    Page<SubmitQuestionVO> getSubmitQuestionVOPage(Page<SubmitQuestion> SubmitQuestionPage, User loginuser);





    /**
     * 題目提交（内部服务）
     *
     * @param userId
     * @param questionid
     * @return
     */
    // int SubmitQuestionInner(long userId, long questionid);

}
