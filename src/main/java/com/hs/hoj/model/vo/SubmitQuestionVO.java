package com.hs.hoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hs.hoj.model.dto.question.JudegConfig;
import com.hs.hoj.model.dto.submitquestion.JudegInfo;
import com.hs.hoj.model.entity.Question;
import com.hs.hoj.model.entity.SubmitQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目提交表
 * @TableName submit_question
 */
@TableName(value ="submit_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitQuestionVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 标题
     */
    private String title;

    /**
     * 判题状态状态
     */
    private Integer status;

    /**
     * 代码
     */
    private String code;

    /**
     * 判题信息json
     */
    private JudegInfo judgeInfo;

    /**
     *  题目id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;


    public static SubmitQuestion voToObj(SubmitQuestionVO submitQuestionVO) {
        if (submitQuestionVO == null) {
            return null;
        }
        SubmitQuestion submitQuestion = new SubmitQuestion();
        BeanUtils.copyProperties(submitQuestionVO, submitQuestion);
        JudegInfo judgeInfo = submitQuestionVO.getJudgeInfo();
        if (judgeInfo != null){
            submitQuestion.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }
        return submitQuestion;
    }

    /**
     * 对象转包装类
     *
     * @param submitQuestion
     * @return
     */
    public static SubmitQuestionVO objToVo(SubmitQuestion submitQuestion) {
        if (submitQuestion == null) {
            return null;
        }
        SubmitQuestionVO submitQuestionVO = new SubmitQuestionVO();
        BeanUtils.copyProperties(submitQuestion, submitQuestionVO);
        String judgeInfo = submitQuestion.getJudgeInfo();
        submitQuestionVO.setJudgeInfo(JSONUtil.toBean(judgeInfo,JudegInfo.class));
        return submitQuestionVO;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}