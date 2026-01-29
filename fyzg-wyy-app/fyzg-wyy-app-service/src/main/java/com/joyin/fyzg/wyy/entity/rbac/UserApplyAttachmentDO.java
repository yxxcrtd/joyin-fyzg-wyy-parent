package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SYS_RBAC_USER_APPLY_ATTACHMENT")
public class UserApplyAttachmentDO {

    @TableId(value = "R_ID", type = IdType.ID_WORKER)
    private Long rId;

    @TableField("APPLY_ID")
    private String applyId;

    @TableField("ATTACHMENT_ID")
    private String attachmentId;

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("FILE_PATH")
    private String filePath;

    @TableField("FILE_TYPE")
    private String fileType;

    @TableField("FILE_SIZE")
    private Long fileSize;

    @TableField("UPLOADER")
    private String uploader;

    @TableField("UPLOAD_TIME")
    private String uploadTime;

    @TableField("REMARK")
    private String remark;

    @TableField(value = "JY_INSERT_TIME", fill = FieldFill.INSERT)
    private String jyInsertTime;

    @TableField("FILE_SOURCE")
    private String fileSource;

}