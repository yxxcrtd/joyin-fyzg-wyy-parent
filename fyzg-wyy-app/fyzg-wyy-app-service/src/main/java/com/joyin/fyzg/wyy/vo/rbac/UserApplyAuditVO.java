package com.joyin.fyzg.wyy.vo.rbac;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserApplyAuditVO {
    private Long rId;
    private String applyId;
    private String auditor;
    private LocalDateTime auditTime;
    private String auditRemark;
    private LocalDateTime createTime;
}
