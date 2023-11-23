package com.hs.hoj.judge.judgeSevice.strategy;

import cn.hutool.json.JSONUtil;
import com.hs.hoj.model.dto.question.JudegCase;
import com.hs.hoj.model.dto.question.JudegConfig;
import com.hs.hoj.model.dto.submitquestion.JudegInfo;
import com.hs.hoj.model.entity.Question;
import org.springframework.stereotype.Service;

import java.util.List;
public class DefaultJudgeStragegy implements JudegStrategy{
    @Override
    public JudegInfo doJudge(JudgeContxt judgeContxt) {

        List<String> outputList = judgeContxt.getOutputs();
        List<JudegCase> judegCaseList = judgeContxt.getJudegCaseList();
        JudegInfo judegInfo = judgeContxt.getJudegInfo();
        Question question = judgeContxt.getQuestion();



        JudegInfo judegInfo1 = new JudegInfo();
        judegInfo1.setMessage("提交成功");
        //判题
        if (outputList.isEmpty()){
            judegInfo1.setMessage("答案错误");
            return judegInfo1;
        }

        for (int i = 0; i  <judegCaseList.size() ; i++ ){
            JudegCase judegCase = judegCaseList.get(i);
            if(!judegCase.getOutput().equals(outputList.get(i))){
                judegInfo1.setMessage("答案错误");
            }
        }
        Long memory = judegInfo.getMemory();
        Long time = judegInfo.getTime();
        String judgeConfig = question.getJudgeConfig();
        JudegConfig judegConfig = JSONUtil.toBean(judgeConfig, JudegConfig.class);

        if (memory > judegConfig.getMemoryLimit() || time > judegConfig.getTimeLimit()){
            judegInfo1.setMessage("代码超出限制");
        }
        judegInfo1.setMemory(memory);
        judegInfo1.setTime(time);
        return judegInfo1;
    }
}
