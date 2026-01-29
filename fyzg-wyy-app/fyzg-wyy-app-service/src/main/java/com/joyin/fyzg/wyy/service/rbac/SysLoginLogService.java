package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;

/**
 * <br/>
 *
 * @author pidong
 * @date 2023/11/17 15:57
 */
public interface SysLoginLogService {

    MethodResponse saveLoginLog(String ipAddress, String account, String productCode, String state, String msg, String terminalType);

    MethodResponse saveLogoutLog(String ipAddress, String userCode, String productCode, String state, String msg, String terminalType);

    MethodResponse save3rdLoginLog(String ipAddress, String account, String productCode, String state, String msg, String terminalType, String loginType);
}
