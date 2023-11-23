package com.hs.hoj.judge.judgeSevice.strategy;

import com.hs.hoj.model.dto.submitquestion.JudegInfo;

public interface JudegStrategy {
    JudegInfo doJudge(JudgeContxt judgeContxt);
}
