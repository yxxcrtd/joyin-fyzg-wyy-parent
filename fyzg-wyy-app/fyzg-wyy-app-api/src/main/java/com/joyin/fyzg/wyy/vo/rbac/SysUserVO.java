package com.joyin.fyzg.wyy.vo.rbac;

import lombok.Data;

import java.util.Date;

/**
 * 表SYS_USER的VO类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
public class SysUserVO {

    /** USER_ID : 用户ID */
    private Long userId;

    /** DEPT_ID : 部门ID */
    private Long deptId;

    /** USER_NAME : 用户账号 */
    private String userName;

    /** NICK_NAME : 用户昵称 */
    private String nickName;

    /** USER_TYPE : 用户类型（00系统用户） */
    private String userType;

    /** EMAIL : 用户邮箱 */
    private String email;

    /** PHONENUMBER : 手机号码 */
    private String phonenumber;

    /** SEX : 用户性别（0男 1女 2未知） */
    private String sex;

    /** AVATAR : 头像地址 */
    private String avatar;

    /** PASSWORD : 密码 */
    private String password;

    /** STATUS : 帐号状态（0正常 1停用） */
    private String status;

    /** DEL_FLAG : 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** LOGIN_IP : 最后登录IP */
    private String loginIp;

    /** LOGIN_DATE : 最后登录时间 */
    private Date loginDate;


}
