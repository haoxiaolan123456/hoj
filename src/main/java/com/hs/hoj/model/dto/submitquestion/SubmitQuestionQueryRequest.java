package com.hs.hoj.model.dto.submitquestion;

import com.hs.hoj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
public class SubmitQuestionQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private Integer status;

    /**
     *  题目id
     */
    private Long questionId;

    /**
     *  用户id
     */
    private Long userId;


    private String sortField;


    private static final long serialVersionUID = 1L;
}