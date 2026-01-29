package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("SYS_RBAC_USER_APPLY_AUDIT")
public class UserApplyAuditDO {

    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;

    @TableField("APPLY_ID")
    private String applyId;

    @TableField("AUDITOR")
    private String auditor;

    @TableField("AUDIT_TIME")
    private String auditTime;

/*    @TableField("AUDIT_STATUS")
    private String auditStatus;*/

    @TableField("AUDIT_REMARK")
    private String auditRemark;

    @TableField(value = "CREATE_TIME")
    private String jyInsertTime;


    private String account;


    private String userOCode;



    private String userOName;



    /** ORG :用户所属的组织机构*/
    private String org;


    /** FINANCIER :用户所属的管理人*/
    private String financier;


    /** ENABLED : 状态：0禁用；1:启用 */
    private String enabled;


    /**  LOGIN_TIME: 用户登录时间 */
    private String loginTime ;



    /** JY_UPDATE_TIME : 修改时间 */
    private String jyUpdateTime;


    /** THEME_CFG_JSON : 主題配置的Json*/
    private String themeCfgJson;


    /** COLLECT_CFG_JSON : 收藏菜单配置的Json*/
    private String collectCfgJson;


    /** TEL : 座机号码 */
    private String tel ;


    /** EMAIL : 电子邮箱 */
    private String email ;


    /** MOBILE : 手机号码 */
    private String mobile ;

    /**
     用户类型  0-管理员、 1-普通用户  2-划款用户
     */
    private String userType;

    private String userManager; //用户管理人

    private String certType; //证件类型

    private String certNo; //证件号码

    private String isInnerUser; //是否内部用户  1-是，0-否

    private String approveFlag;//0:退回；1：通过
}
