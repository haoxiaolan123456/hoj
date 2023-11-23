package com.hs.hoj;

import com.hs.hoj.config.WxOpenConfig;
import javax.annotation.Resource;

import com.hs.hoj.judge.CodeSandBox.CodeSandBox;
import com.hs.hoj.judge.CodeSandBox.CodeSandBoxFactory;
import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * 主类测试
 *
 *  
 *   
 */
@SpringBootTest
class MainApplicationTests {

    @Value("${codesandbox.type}")
    private String type;
    @Resource
    private WxOpenConfig wxOpenConfig;

    @Test
    void contextLoads() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);

        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2"));
        executeCodeRequest.setCode("public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(a + b);\n" +
                "    }\n" +
                "}");
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }

}
