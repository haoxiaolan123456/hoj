package com.hs.hoj.model.dto.submitquestion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hs.hoj.model.dto.question.JudegCase;
import com.hs.hoj.model.dto.question.JudegConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 */
@Data
public class SubmitQuestionRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;


    /**
     * 代码
     */
    private String code;


    /**
     *  题目id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;
}