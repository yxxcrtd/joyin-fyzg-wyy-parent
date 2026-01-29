package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表RBAC_USER的实体类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@KeySequence(value = "S_SYS_RBAC_ACCOUNT",clazz = Long.class)
@TableName("SYS_RBAC_ACCOUNT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;


    /** ACCOUNT : 登录账号 */
    private String account;


    /** PASSWORD : 密码 */
    private String password;


    /** ENABLED : 状态：0禁用；1:启用 */
    private String enabled;


    /* LOCKED ： 锁定：0 锁定；1 未锁定  */
    private String locked ;


    /**  LOGIN_TIME: 用户登录时间 */
    private String loginTime ;


    /**  PW_FLUSH_DATE: 密码刷新日期 */
    private String pwFlushDate ;


    /**  VALID_DATE: 密码失效日期 */
    private String validDate ;


    /** UNLOCK_TIME: 用户解锁时间 */
    private String unlockTime ;


    /** PWD_ERROR_NUM: 密码错误次数 */
    private Long pwdErrorNum ;


    /** PWD_ERROR_TIME: 密码错误时间 */
    private String pwdErrorTime ;
}
