package com.joyin.fyzg.wyy.service.rbac.impl;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.common.enums.LoginEnum;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageRequestDO;
import com.joyin.fyzg.wyy.entity.rbac.SysLoginLogDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.rbac.SysLoginLogMapper;
import com.joyin.fyzg.wyy.service.rbac.SysLoginLogService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.utils.DateUtil8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

import static com.joyin.fyzg.wyy.common.enums.LoginExceptionEnum.*;

/**
 * <br/>
 *
 * @author pidong
 * @date 2023/11/17 15:57
 */
@Service
@Slf4j
@Transactional
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Resource
    SysLoginLogMapper sysLoginLogMapper;
    @Autowired
    UserService userService;
    @Value("${syslog.loginLogEnabled:false}")
    public boolean loginLogEnabled;


    @Override
    public MethodResponse saveLoginLog(String ipAddress, String account, String productCode, String state, String msg, String terminalType) {
        try {
            log.info("系统登陆日志开关loginLogEnabled=" + loginLogEnabled);
            if(!loginLogEnabled){
                return MethodResponse.success();
            }
            SysLoginLogDO loginLogDO = SysLoginLogDO.builder()
                    .logId(UUID.randomUUID().toString())
                    .account(account)
                    .productCode(productCode)
                    .ipAddress(ipAddress)
                    .operTime(DateUtil8.getNowTime_EN())
                    .state(state)
                    .msg(msg)
                    .terminalType(terminalType)
                    .loginType(LoginEnum.LOGIN.getType())
                    .build();

            sysLoginLogMapper.insert(loginLogDO);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(LOGIN_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(LOGIN_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e));
        }
    }

    @Override
    public MethodResponse saveLogoutLog(String ipAddress, String userCode, String productCode, String state, String msg, String terminalType) {
        try {
            log.info("系统登陆日志开关loginLogEnabled=" + loginLogEnabled);
            if(!loginLogEnabled){
                return MethodResponse.success();
            }
            SysLoginLogDO loginLogDO = SysLoginLogDO.builder()
                    .logId(UUID.randomUUID().toString())
                    .account(getAccountByUserCode(userCode))
                    .productCode(productCode)
                    .ipAddress(ipAddress)
                    .operTime(DateUtil8.getNowTime_EN())
                    .state(state)
                    .msg(msg)
                    .terminalType(terminalType)
                    .loginType(LoginEnum.LOGOUT.getType())
                    .build();

            sysLoginLogMapper.insert(loginLogDO);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(LOGOUT_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(LOGOUT_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e));
        }
    }

    private String getAccountByUserCode(String userCode) {
        UserDO userDO = userService.getUserByUserCode(userCode);
        return userDO == null ? "" : userDO.getAccount();
    }

    @Override
    public MethodResponse save3rdLoginLog(String ipAddress, String account, String productCode, String state, String msg, String terminalType, String loginType) {
        try {
            log.info("系统登陆日志开关loginLogEnabled=" + loginLogEnabled);
            if(!loginLogEnabled){
                return MethodResponse.success();
            }
            SysLoginLogDO loginLogDO = SysLoginLogDO.builder()
                    .logId(UUID.randomUUID().toString())
                    .account(account)
                    .productCode(productCode)
                    .ipAddress(ipAddress)
                    .operTime(DateUtil8.getNowTime_EN())
                    .state(state)
                    .msg(msg)
                    .terminalType(terminalType)
                    .loginType(loginType)
                    .build();

            sysLoginLogMapper.insert(loginLogDO);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(LOGIN_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(LOGIN_SAVE_LOG.formatEntity(MenuPageRequestDO.class, e));
        }
    }
}
