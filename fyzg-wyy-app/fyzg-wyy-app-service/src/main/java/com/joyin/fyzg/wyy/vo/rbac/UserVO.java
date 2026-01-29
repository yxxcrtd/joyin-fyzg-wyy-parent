package com.joyin.fyzg.wyy.vo.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserVO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;


    /** USER_O_CODE : 用户代码 */
    private String userOCode;


    /** USER_O_NAME : 用户名称 */
    private String userOName;


    /** ACCOUNT : 登录账号 */
    private String account;


    /** ORG :用户所属的组织机构*/
    private String org;


    /** FINANCIER :用户所属的管理人*/
    private String financier;

    /**financierName 用户所属的管理人名称*/
    private String financierName;


    /** ENABLED : 状态：0禁用；1:启用 */
    private String enabled;


    /**  LOGIN_TIME: 用户登录时间 */
    private String loginTime ;


    /** JY_INSERT_TIME : 创建时间 */
    private String jyInsertTime;


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

    private String flag; //0-用户表  1-流程表

    private String applyId;

    /** roleOName :角色代码*/
    private String roleCode;

    /** roleOName :角色名称*/
    private String roleOName;
}
