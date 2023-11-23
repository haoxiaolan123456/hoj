package com.hs.hoj.judge.CodeSandBox.impl;

import com.hs.hoj.judge.CodeSandBox.CodeSandBox;
import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandBosProxy implements CodeSandBox {


    private CodeSandBox codeSandBox;

    public CodeSandBosProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("日志1");
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("日志2");
        return executeCodeResponse;
    }
}
