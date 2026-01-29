package com.joyin.fyzg.wyy.controller.permission;

import com.joyin.fyzg.common.*;
import com.joyin.fyzg.utils.JedisUtil;
import com.joyin.fyzg.utils.PageSecurityUtil;
import com.joyin.fyzg.utils.RsaUtils;
import com.joyin.fyzg.wyy.common.dto.LoginDTO;
import com.joyin.fyzg.wyy.common.enums.LoginEnum;
import com.joyin.fyzg.wyy.common.enums.LoginType;
import com.joyin.fyzg.wyy.common.utils.ImageValidateCodeUtils;
import com.joyin.fyzg.wyy.config.cas.constant.Constant;
import com.joyin.fyzg.wyy.constant.WyyConstants;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.permission.AuthService;
import com.joyin.fyzg.wyy.service.rbac.SysLoginLogService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.utils.JwtTokenUtil;
import com.joyin.fyzg.wyy.vo.rbac.UserAccountVO;
import com.joyin.fyzg.wyy.vo.rbac.UserFinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("auth")
public class AuthController extends BaseController {

	@Autowired
	private AuthService authService;
    
	@Autowired
	UserService userService;

	@Autowired
	private SysLoginLogService sysLoginLogService;

    @RequestMapping(value = "getTokenByST")
    public RestResponse<String> getTokenByST(String st) {
        final String token = JedisUtil.HASH.hget(Constant.ST_TOKENS, st);
        JedisUtil.HASH.hdel(Constant.ST_TOKENS, st);
        return RestResponse.success(token);
    }

    @RequestMapping(value = "logout")
    public RestResponse<String> logout(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        Optional.ofNullable(session).ifPresent(HttpSession::invalidate);
        return RestResponse.success("ok");
    }

	@GetMapping("getEncryptParams")
	public RestResponse<String> getEncryptParams() {
		return RestResponse.success(RsaUtils.geneEncryptInfo());
	}

	@GetMapping("getPageToken")
	public RestResponse<String> getPageToken() {
		try {
			String loginUserCode = super.getLoginUserCode();
			String pageTokenStr = PageSecurityUtil.genePageToken(loginUserCode);

			return RestResponse.success(pageTokenStr);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return RestResponse.error("generate token value error!!!");
		}
	}

	@GetMapping("getImgCode")
	public RestResponse<String> getImgCode() {
		return RestResponse.success(ImageValidateCodeUtils.getCode());
	}

	@PostMapping("login")
	public RestResponse<LoginDTO> login(@RequestBody RequestParamMap params) {
        String account = params.getStringValueOfNullable("username");
        String password = params.getStringValueOfNullable("password");
        String pubKey = params.getStringValueOfNullable("pubkey");
        String verifyCode = params.getStringValueOfNullable("code");
        String clientId = params.getStringValueOfNullable("type");
        String state = LoginEnum.SUCCESS.getType();
        String msg = "";
        MethodResponse<LoginDTO> login = null;
        try {
            login = authService.loginByAccount(account, password, pubKey, verifyCode, clientId);
            WithCodeMsgEnum loginType = login.getData().getLoginType();
            if(LoginType.LOGIN_SUCCESS.getCode().equals(loginType.getCode())){
                state = LoginEnum.SUCCESS.getType();
            }else{
                state = LoginEnum.FAIL.getType();
                msg = loginType.getMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
            state = LoginEnum.FAIL.getType();
            msg = e.getMessage();
        } finally {
            sysLoginLogService.saveLoginLog(super.getIpAddress(), account, clientId, state, msg, WyyConstants.TerminalType.PC);
        }

        return RestResponse.transMethodResponse(login);
    }

	@PostMapping("switchPermission")
	public RestResponse<LoginDTO> switchPermission(@RequestBody RequestParamMap params) {
		String account = params.getStringValueOfNullable("username");
        String userCode = params.getStringValueOfNullable("userCode");
        String financier = params.getStringValueOfNullable("financier");
		String clientId = params.getStringValueOfNullable("type");
        String state = LoginEnum.SUCCESS.getType();
        String msg = "";
        MethodResponse<LoginDTO> login = null;
        try {
            login = authService.switchPermission(userCode, financier, clientId);
            WithCodeMsgEnum loginType = login.getData().getLoginType();
            if(LoginType.LOGIN_SUCCESS.getCode().equals(loginType.getCode())){
                state = LoginEnum.SUCCESS.getType();
            }else{
                state = LoginEnum.FAIL.getType();
                msg = loginType.getMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
            state = LoginEnum.FAIL.getType();
            msg = e.getMessage();
        } finally {
            sysLoginLogService.saveLoginLog(super.getIpAddress(), account, clientId, state, msg, WyyConstants.TerminalType.PC);
        }
        return RestResponse.transMethodResponse(login);
    }

	@GetMapping("writeUserSession")
	public RestResponse writeUserSession(@RequestParam("userCode") String userCode) {
		return RestResponse.transMethodResponse(authService.writeUserSession(userCode));
	}

	@PostMapping("save3rdLoginLog")
	public RestResponse save3rdLoginLog(@RequestParam("ipAddress") String ipAddress,
                                        @RequestParam("account") String account,
                                        @RequestParam("productCode") String productCode,
                                        @RequestParam("state") String state,
                                        @RequestParam("msg") String msg,
                                        @RequestParam("terminalType") String terminalType,
                                        @RequestParam("loginType") String loginType) {
		return RestResponse.transMethodResponse(sysLoginLogService.save3rdLoginLog(ipAddress, account, productCode, state, msg, terminalType, loginType));
	}

	@GetMapping(value = "getLoginState")
	public RestResponse<String> getLoginState() {
		return RestResponse.success(super.getTokenClaim(a -> a.get(JwtTokenUtil.USER_LOGIN_STATE, String.class)));
	}

	@RequestMapping("logout1")
	public RestResponse logout1() {
        String loginUserCode = super.getLoginUserCode();
        String clientIp = super.getIpAddress();
        String clientId = super.getClientId();
        String state = "";
        String msg = "";
        try {
            authService.logout(super.getLoginUserCode(), super.getToken());
            state = LoginEnum.SUCCESS.getType();
            return RestResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            state = LoginEnum.FAIL.getType();
            msg = e.getMessage();
            return RestResponse.error();
        } finally {
            sysLoginLogService.saveLogoutLog(clientIp, loginUserCode, clientId, state, msg, WyyConstants.TerminalType.PC);
        }
	}

	@GetMapping("listUserAccount")
	public RestResponse<List<UserAccountVO>> listUserAccount(@RequestParam("account") String account) {
        try {
            String loginUserCode = super.getLoginUserCode();
            if (ObjectUtils.isEmpty(loginUserCode)) {
                return RestResponse.error("用户未认证，请先登录");
            }
            UserDO currentUser = userService.getUserByUserCode(loginUserCode);
            if (null == currentUser) {
                return RestResponse.error("获取当前用户信息失败");
            }
            if (!account.equals(currentUser.getAccount())) {
                log.warn("越权访问尝试: 用户 {} 尝试访问账号 {} 的信息", currentUser.getAccount(), account);
                return RestResponse.error("无权访问该用户信息，存在访问越权!!!");
            }
            return RestResponse.transMethodResponse(authService.listUserAccount(account));
        } catch (Exception e) {
            log.error("获取用户账号列表失败", e);
            return RestResponse.error("获取用户账号列表失败：" + e.getMessage());
        }
	}

	@GetMapping("listUserFinancier")
	public RestResponse<List<UserFinancierVO>> listUserFinancier(@RequestParam("userCode") String userCode) {
		return RestResponse.transMethodResponse(authService.listUserFinancier(userCode));
	}
}
