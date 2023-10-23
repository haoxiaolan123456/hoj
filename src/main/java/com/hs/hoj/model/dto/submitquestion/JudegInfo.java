package com.hs.hoj.model.dto.submitquestion;

import lombok.Data;

/**
 * 判题信息
 */
@Data


public class JudegInfo {
    /**
     * 信息
     */
    private String  message;
    /**
     * 内存
     */
    private Long  memory;

    /**
     * 时间
     */
    private Long time;
}
