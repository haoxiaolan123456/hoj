package com.hs.hoj.judge.CodeSandBox.impl;


import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hs.hoj.common.ErrorCode;
import com.hs.hoj.exception.BusinessException;
import com.hs.hoj.judge.CodeSandBox.CodeSandBox;
import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

//示例
public class RemoteCodeSandBoxImpl implements CodeSandBox {
    private static final String AUHT_REQUEST_HEAEDER = "auth";
    private static final String AUHT_REQUEST_STR = "set";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("远程代码沙箱");
        String url = "http://localhost:8083/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String response =  HttpUtil
                .createPost(url)
                .header(AUHT_REQUEST_HEAEDER,AUHT_REQUEST_STR)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(response)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROT,"响应失败");
        }
        return JSONUtil.toBean(response,ExecuteCodeResponse.class);
    }
}
