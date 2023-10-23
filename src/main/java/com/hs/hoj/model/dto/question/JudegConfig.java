package com.hs.hoj.model.dto.question;

import lombok.Data;

/**
 * 题目用例
 */
@Data
public class JudegConfig {
    /**
     * 时间限制ms
     */
    private Long timeLimit;
    /**
     * 内存限制kb
     */
    private Long  memoryLimit;


    /**
     * 堆栈限制kb
     */
    private Long  stackLimit;
}
