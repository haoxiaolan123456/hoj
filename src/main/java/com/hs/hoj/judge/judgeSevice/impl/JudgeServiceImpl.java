package com.hs.hoj.judge.judgeSevice.impl;

import cn.hutool.json.JSONUtil;
import com.hs.hoj.common.ErrorCode;
import com.hs.hoj.exception.BusinessException;
import com.hs.hoj.judge.CodeSandBox.CodeSandBox;
import com.hs.hoj.judge.CodeSandBox.CodeSandBoxFactory;
import com.hs.hoj.judge.CodeSandBox.impl.CodeSandBosProxy;
import com.hs.hoj.judge.judgeSevice.JudgeService;
import com.hs.hoj.judge.judgeSevice.strategy.DefaultJudgeStragegy;
import com.hs.hoj.judge.judgeSevice.strategy.JudegStrategy;
import com.hs.hoj.judge.judgeSevice.strategy.JudgeContxt;
import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import com.hs.hoj.model.dto.question.JudegCase;
import com.hs.hoj.model.dto.question.JudegConfig;
import com.hs.hoj.model.dto.submitquestion.JudegInfo;
import com.hs.hoj.model.entity.Question;
import com.hs.hoj.model.entity.SubmitQuestion;
import com.hs.hoj.model.enums.SubmitStatueEnum;
import com.hs.hoj.model.vo.SubmitQuestionVO;
import com.hs.hoj.service.QuestionService;
import com.hs.hoj.service.SubmitQuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private SubmitQuestionService submitQuestionService;


    @Override
    public SubmitQuestionVO doJudge(Long submitQuestionId) {
         System.out.println("---->判题服务");
        //通过题目id获取题目提交的信息
        SubmitQuestion submitQuestion = submitQuestionService.getById(submitQuestionId);
        if (submitQuestion == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = submitQuestion.getQuestionId();

        Question question = questionService.getById(questionId);
        if (question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        Integer status = submitQuestion.getStatus();
        if (!status.equals(SubmitStatueEnum.WAUTTING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题");
        }
        SubmitQuestion submitQuestion1 = new SubmitQuestion();
        submitQuestion1.setId(submitQuestionId);
        submitQuestion1.setStatus(SubmitStatueEnum.RUNNING.getValue());
        boolean update = submitQuestionService.updateById(submitQuestion1);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目题目更新错误");
        }

        Long id = submitQuestion.getId();
        String judgeInfo = submitQuestion.getJudgeInfo();
        Long userId = submitQuestion.getUserId();
        Date createTime = submitQuestion.getCreateTime();
        Date updateTime = submitQuestion.getUpdateTime();
        Integer isDelete = submitQuestion.getIsDelete();


        //通过沙箱执行代码
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        CodeSandBox codeSandBosProxy = new CodeSandBosProxy(codeSandBox);

        //获取参数
        String judgeCaseStr = question.getJudgeCase();
        List<JudegCase> JudegCaselist = JSONUtil.toList(judgeCaseStr, JudegCase.class);
        String code = submitQuestion.getCode();
        String language = submitQuestion.getLanguage();
        List<String> inputList = JudegCaselist.stream().map(JudegCase::getInput).collect(Collectors.toList());




        //为执行代码的方法传参
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(inputList);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage(language);

        //执行代码
        ExecuteCodeResponse executeCodeResponse = codeSandBosProxy.executeCode(executeCodeRequest);

        //获取执行代码后的结果
        List<String> outputList = executeCodeResponse.getOutputList();
        String message = executeCodeResponse.getMessage();
        Integer status1 = executeCodeResponse.getStatus();
        JudegInfo judegInfo = executeCodeResponse.getJudegInfo();
        System.out.println("结果"+executeCodeResponse);

        //判题参数
        JudgeContxt judgeContxt = new JudgeContxt();
        judgeContxt.setOutputs(outputList);
        judgeContxt.setJudegInfo(judegInfo);
        judgeContxt.setQuestion(question);
        judgeContxt.setJudegCaseList(JudegCaselist);
        //判题
        JudegStrategy judegStrategy = new DefaultJudgeStragegy();
        JudegInfo judegInfo1 = judegStrategy.doJudge(judgeContxt);


        // 更新数据库状态
        submitQuestion1.setId(submitQuestionId);
        submitQuestion1.setStatus(status1);
        submitQuestion1.setJudgeInfo(JSONUtil.toJsonStr(judegInfo1));
        update = submitQuestionService.updateById(submitQuestion1);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目题目更新错误");
        }
        SubmitQuestion submitQuestion2 = submitQuestionService.getById(questionId);
        SubmitQuestionVO submitQuestionVO = SubmitQuestionVO.objToVo(submitQuestion2);
        return submitQuestionVO;
    }
}
