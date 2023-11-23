package com.hs.hoj.judge.judgeSevice.strategy;

import com.hs.hoj.model.dto.question.JudegCase;
import com.hs.hoj.model.dto.submitquestion.JudegInfo;
import com.hs.hoj.model.entity.Question;
import lombok.Data;

import java.util.List;


@Data
public class JudgeContxt {
    /**
     * 执行代码后的输出
     */
    private List<String> outputs;
    /**
     *代码执行后的
     */
    private List<JudegCase> JudegCaseList;
    /**
     *
     */
    private JudegInfo judegInfo;
    /**
     *题目
     */
    private Question question;
}
