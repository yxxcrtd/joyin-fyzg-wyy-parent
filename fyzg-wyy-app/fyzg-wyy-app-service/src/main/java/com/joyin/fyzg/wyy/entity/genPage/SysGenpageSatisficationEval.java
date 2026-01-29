package com.joyin.fyzg.wyy.entity.genPage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.beans.Transient;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 内页配置满意度评价表
 * @TableName SYS_GENPAGE_SATISFICATION_EVAL
 */
@TableName(value ="SYS_GENPAGE_SATISFICATION_EVAL")
@Data
public class SysGenpageSatisficationEval  {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 页面id
     */
    private String pageId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面功能名称
     */
    private String pageFunction;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 满意度评分
     */
    private Long score;

    /**
     * 意见建议
     */
    private String suggest;

    /**
     * 附件id
     */
    private String attaId;

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