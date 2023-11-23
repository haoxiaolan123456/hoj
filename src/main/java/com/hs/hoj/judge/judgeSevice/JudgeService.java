package com.hs.hoj.judge.judgeSevice;

import com.hs.hoj.judge.model.ExecuteCodeRequest;
import com.hs.hoj.judge.model.ExecuteCodeResponse;
import com.hs.hoj.model.entity.SubmitQuestion;
import com.hs.hoj.model.vo.SubmitQuestionVO;

public interface JudgeService {

    SubmitQuestionVO doJudge(Long submitQuestionId);
}
