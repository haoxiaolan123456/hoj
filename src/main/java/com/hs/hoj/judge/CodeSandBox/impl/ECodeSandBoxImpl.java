package com.hs.hoj.judge.CodeSandBox.impl;
import com.hs.hoj.judge.CodeSandBox.CodeSandBox;
import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import com.hs.hoj.model.dto.submitquestion.JudegInfo;

import java.util.ArrayList;
import java.util.List;

//示例
public class ECodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> list = new ArrayList<>();
        JudegInfo judegInfo = new JudegInfo();
        judegInfo.setTime(100L);
        judegInfo.setMemory(100L);
        list.add("1");
        executeCodeResponse.setOutputList(list);
        executeCodeResponse.setJudegInfo(judegInfo);
        return executeCodeResponse;
    }
}
