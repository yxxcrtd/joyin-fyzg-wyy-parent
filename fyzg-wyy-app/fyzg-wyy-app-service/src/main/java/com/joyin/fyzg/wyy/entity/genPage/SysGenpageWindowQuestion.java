package com.joyin.fyzg.wyy.entity.genPage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 弹窗问题表 区分不同题型
 * @TableName SYS_GENPAGE_WINDOW_QUESTION
 */
@TableName(value ="SYS_GENPAGE_WINDOW_QUESTION")
@Data
public class SysGenpageWindowQuestion  {
    /**
     * 唯一标识
     */
    @TableId
    private Long id;

    /**
     * 题库定义表id
     */
    private Long windowId;

    /**
     * 问题编号
     */
    private Long questionSeq;

    /**
     * 问题类型：0单选，1多选，3文本
     */
    private String questionType;

    /**
     * 问题文本
     */
    private String question;

    /**
     * 答案编号
     */
    private Long answerSeq;

    /**
     * 答案文本
     */
    private String answer;

    /**
     * 是否正确答案 Y 是， N 否， 空 没有正确答案
     */
    private String answerResult;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}