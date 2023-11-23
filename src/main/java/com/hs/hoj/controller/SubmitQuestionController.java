package com.hs.hoj.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.hoj.common.BaseResponse;
import com.hs.hoj.common.ErrorCode;
import com.hs.hoj.common.ResultUtils;
import com.hs.hoj.exception.BusinessException;
import com.hs.hoj.exception.ThrowUtils;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionQueryRequest;
import com.hs.hoj.model.dto.submitquestion.SubmitQuestionRequest;
import com.hs.hoj.model.entity.SubmitQuestion;
import com.hs.hoj.model.entity.User;
import com.hs.hoj.model.vo.SubmitQuestionVO;
import com.hs.hoj.service.SubmitQuestionService;
import com.hs.hoj.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提交题目接口
 */
public class SubmitQuestionController {

    @Resource
    private SubmitQuestionService submitQuestionService;

    @Resource
    private UserService userService;

    /**
     * 提交題目
     *
     * @param submitQuestionRequest
     * @param request
     * @return
     */


 /*   @PostMapping("/")
    public BaseResponse<Long> SubmitQuestion(@RequestBody SubmitQuestionRequest submitQuestionRequest,
                                         HttpServletRequest request) {
        if (submitQuestionRequest == null || submitQuestionRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        Long result = submitQuestionService.SubmitQuestion(submitQuestionRequest, loginUser);
        return ResultUtils.success(result);
    }


    @PostMapping("/list/page")
    public BaseResponse<Page<SubmitQuestionVO>> listSubmitQuestionPage(@RequestBody SubmitQuestionQueryRequest submitQuestionPageRequest,
                                                                       HttpServletRequest request) {

        if (submitQuestionPageRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        submitQuestionPageRequest.setUserId(loginUser.getId());
        long current = submitQuestionPageRequest.getCurrent();
        long size = submitQuestionPageRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        Page<SubmitQuestion> questionPage = submitQuestionService.page(new Page<>(current, size),
                submitQuestionService.getQueryWrapper(submitQuestionPageRequest));

        return ResultUtils.success(submitQuestionService.getSubmitQuestionVOPage(questionPage,loginUser));
    }*/

}
