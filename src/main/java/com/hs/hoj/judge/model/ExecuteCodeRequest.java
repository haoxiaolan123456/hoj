package com.hs.hoj.judge.model;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteCodeRequest {
    private List<String> inputList;

    private String code;

    private String language;
}
