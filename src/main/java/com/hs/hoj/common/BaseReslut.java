package com.hs.hoj.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接受代码沙箱返回结果
 *
 * @param <T>
 */
@Data
public class BaseReslut<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseReslut(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
