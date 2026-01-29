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
 * 弹窗答案结果表 答题后记录
 * @TableName SYS_GENPAGE_WINDOW_ANSWER
 */
@TableName(value ="SYS_GENPAGE_WINDOW_ANSWER")
@Data
public class SysGenpageWindowAnswer  {
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
     * 答题人
     */
    private String userName;

    /**
     * 问题编号
     */
    private Long questionSeq;

    /**
     * 答案编号
     */
    private Long answerSeq;

    /**
     * 文本问题答案
     */
    private String answerText;

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