package com.joyin.fyzg.wyy.entity.genPage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 内页配置信息定义表
 *
 * @TableName SYS_GENPAGE_CONF_DEFINE
 */
@TableName(value = "SYS_GENPAGE_CONF_DEFINE")
@Data
public class SysGenpageConfDefine implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单id
     */
    private String pageId;

    /**
     * 是否启用弹框 Y 是，N 否
     */
    private String showWindow;

    /**
     * 弹框类型 0 提示，1 问卷调查
     */
    private String windowType;

    /**
     * 提示型弹框文本内容
     */
    private String windowPrompt;

    /**
     * 题库id
     */
    private Long windowId;

    /**
     * 是否显示标题栏  Y 是，N 否
     */
    private String showTitle;

    /**
     * 标题栏是否显示满意度评价 Y 是，N 否
     */
    private String showTitleEva;

    /**
     * 标题栏是否显示操作指南 Y 是，N 否
     */
    private String showTitleOpr;

    /**
     * 是否显示温馨提示  Y 是，N 否
     */
    private String showPrompt;

    /**
     * 温馨提示全部展示  Y 是，N 否
     */
    private String promptExpand;

    /**
     * 温馨提示标题
     */
    private String promptTitle;

    /**
     * 温馨提示内容
     */
    private String promptInifo;

    /**
     * 是否显示汇总信息  Y 是，N 否
     */
    private String showCollectInfo;

    /**
     * 汇总信息id,多个用，隔离
     */
    private String collectInfoId;

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