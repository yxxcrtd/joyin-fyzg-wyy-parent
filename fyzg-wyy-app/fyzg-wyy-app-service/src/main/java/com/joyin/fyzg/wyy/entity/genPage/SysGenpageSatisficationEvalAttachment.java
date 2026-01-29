package com.joyin.fyzg.wyy.entity.genPage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 内页配置满意度评价附件表
 * @TableName SYS_GENPAGE_SATISFICATION_EVAL_ATTACHMENT
 */
@TableName(value ="SYS_GENPAGE_SATISFICATION_EVAL_ATTACHMENT")
@Data
public class SysGenpageSatisficationEvalAttachment  {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 菜单id
     */
    private String pageId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 附件唯一ID
     */
    private String attachmentId;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 服务器存储路径
     */
    private String filePath;

    /**
     * MIME类型
     */
    private String fileType;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 上传人
     */
    private String uploader;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String uploadTime;

    /**
     * 附件说明
     */
    private String remark;

}