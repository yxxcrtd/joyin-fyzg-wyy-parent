package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@TableName("SYS_RBAC_USER_APPLY")
public class UserApplyDO {

    @TableId(value = "R_ID", type = IdType.ID_WORKER)
    private Long rId;

    @TableField("APPLY_ID")
    private String applyId;

    @TableField("USER_O_CODE")
    private String userOCode;

    @TableField("USER_O_NAME")
    private String userOName;

    @TableField("ACCOUNT")
    private String account;

    @TableField("ORG")
    private String org;

    @TableField("FINANCIER")
    private String financier;

    @TableField("ENABLED")
    private String enabled;

    @TableField("TEL")
    private String tel;

    @TableField("MOBILE")
    private String mobile;

    @TableField("EMAIL")
    private String email;

    @TableField("REMARK")
    private String remark;

    @TableField("APPLICANT")
    private String applicant;

    @TableField("APPLY_TIME")
    private String applyTime;

    @TableField("STATUS")
    private String status;

    /**
     用户类型  0-管理员、 1-普通用户  2-划款用户
     */
    @TableField("USER_TYPE")
    private String userType;

    @TableField("USER_MANAGER")
    private String userManager; //用户管理人

    @TableField("CERT_TYPE")
    private String certType; //证件类型

    @TableField("CERT_NO")
    private String certNo; //证件号码

    @TableField("IS_INNER")
    private String isInnerUser; //是否内部用户  1-是，0-否

    @TableField("MODI_ADMIN_FLAG")
    private String modiAdminFlag; //是否调整管理员 1-是，0-否
}
