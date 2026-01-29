package com.joyin.fyzg.wyy.vo.rbac;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserApplyAttachmentVO {
    private Long rId;
    private String applyId;
    private String attachmentId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private String uploader;
    private LocalDateTime uploadTime;
    private String remark;
    private LocalDateTime createTime;
}
