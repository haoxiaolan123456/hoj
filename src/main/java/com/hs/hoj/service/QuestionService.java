package com.hs.hoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.hoj.model.dto.question.QuestionQueryRequest;
import com.hs.hoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.hoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author haoshuai
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-10-21 11:54:15
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     * @param Question
     * @param add
     */
    void validQuestion(Question Question, boolean add);

    /**
     * 获取查询条件
     *
     * @param QuestionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest QuestionQueryRequest);


    /**
     * 获取題目封装
     * @param Question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question Question, HttpServletRequest request);

    /**
     * 分页获取題目封装
     * @param QuestionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> QuestionPage, HttpServletRequest request);
}
