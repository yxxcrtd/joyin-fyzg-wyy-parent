package com.joyin.fyzg.wyy.vo.rbac;

import com.baomidou.mybatisplus.annotation.TableField;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserApplyVO {
    private Long rId;
    private String applyId;
    private String userOCode;
    private String userOName;
    private String account;
    private String org;
    private String financier;
    private String enabled;
    private String tel;
    private String mobile;
    private String email;
    private String remark;
    private String applicant;
    private LocalDateTime applyTime;
    private String status;
    private String userType;
    private String userManager; //用户管理人
    private String certType; //证件类型
    private String certNo; //证件号码
    private String isInnerUser; //是否内部用户  1-是，0-否
    private String AUDIT_REMARK; //批注
    private List<UserApplyAttachmentDO> attachments;
    //private List<UserApplyAuditVO> auditRecords;
}
