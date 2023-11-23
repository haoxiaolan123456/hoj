package com.hs.hoj.judge.CodeSandBox;

import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;

public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
