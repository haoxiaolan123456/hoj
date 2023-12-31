package com.hs.hoj.model.vo;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.gson.reflect.TypeToken;
import com.hs.hoj.model.dto.question.JudegConfig;
import com.hs.hoj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 *
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 题目答案
     */
    private String answer;

    /**
     *
     * 判题用例 json数组
     */
    private String judgeCase;


    /**
     * 判题配置 json数组
     */
    private JudegConfig judgeConfig;

    /**
     * 创建用户 id
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


    private UserVO user;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 包装类转对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudegConfig judegConfigvo = questionVO.getJudgeConfig();
        if (judegConfigvo != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(judegConfigvo));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);

        String tags = question.getTags();
        String judgeConfig = question.getJudgeConfig();

        List<String> tagslist = JSONUtil.toList(tags, String.class);
        questionVO.setTags(tagslist);

        JudegConfig judegConfig = JSONUtil.toBean(judgeConfig, JudegConfig.class);
        questionVO.setJudgeConfig(judegConfig);
        return questionVO;
    }


}