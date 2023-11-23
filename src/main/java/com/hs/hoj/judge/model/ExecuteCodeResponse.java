package com.hs.hoj.judge.model;

import com.hs.hoj.model.dto.submitquestion.JudegInfo;
import lombok.Data;

import java.util.List;

@Data
public class ExecuteCodeResponse {
    private List<String> outputList;

    private String message;

    private Integer status;


    private JudegInfo judegInfo;
}
