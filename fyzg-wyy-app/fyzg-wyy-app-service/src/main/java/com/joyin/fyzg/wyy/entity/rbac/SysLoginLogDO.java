package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * <br/>
 *
 * @author pidong
 * @date 2023/11/17 15:52
 */
@Data
@TableName("SYS_LOGIN_LOG")
@Builder
public class SysLoginLogDO {

    /** LOG_ID : 日志ID主键 */
    @TableId(value = "LOG_ID", type = IdType.UUID)
    private String logId;

    /** ACCOUNT 账号 **/
    private String account;

    /** PRODUCT_CODE 业务线 **/
    private String productCode;

    /** OPER_TIME 操作时间 **/
    private String operTime;

    /** IP_ADDRESS ip地址 **/
    private String ipAddress;

    /** STATE 操作结果状态 **/
    private String state;

    /** MSG 操作结果信息 **/
    private String msg;

    /** TERMINAL_TYPE 操作结果状态 **/
    private String terminalType;

    /** LOGIN_TYPE 登陆登出类型 **/
    private String loginType;

}
