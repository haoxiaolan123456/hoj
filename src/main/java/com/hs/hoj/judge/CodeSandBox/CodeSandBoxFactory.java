package com.hs.hoj.judge.CodeSandBox;

import com.github.mustachejava.Code;
import com.hs.hoj.judge.CodeSandBox.impl.ECodeSandBoxImpl;
import com.hs.hoj.judge.CodeSandBox.impl.RemoteCodeSandBoxImpl;

public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "example":
            default:
                return new ECodeSandBoxImpl();
        }
    }
}
