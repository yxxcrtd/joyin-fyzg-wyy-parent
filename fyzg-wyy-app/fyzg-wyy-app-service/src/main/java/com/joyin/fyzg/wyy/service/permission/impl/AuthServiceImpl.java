package com.joyin.fyzg.wyy.service.permission.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.user.UserProperties;
import com.joyin.fyzg.constant.SessionConstant;
import com.joyin.fyzg.wyy.common.dto.LoginDTO;
import com.joyin.fyzg.wyy.common.enums.RbacUserEnable;
import com.joyin.fyzg.wyy.common.enums.RbacUserLocked;
import com.joyin.fyzg.wyy.common.utils.ImageValidateCodeUtils;
import com.joyin.fyzg.wyy.constant.WyyConstants;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.entity.rbac.UserExtDO;
import com.joyin.fyzg.wyy.service.financier.FinancierMpService;
import com.joyin.fyzg.wyy.service.financier.FinancierService;
import com.joyin.fyzg.wyy.service.permission.AuthService;
import com.joyin.fyzg.wyy.service.permission.PwdService;
import com.joyin.fyzg.wyy.service.rbac.AccountService;
import com.joyin.fyzg.wyy.service.rbac.UserExtService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.utils.*;
import com.joyin.fyzg.vo.ChatUserVO;
import com.joyin.fyzg.vo.DecryptVO;
import com.joyin.fyzg.wyy.vo.rbac.UserAccountVO;
import com.joyin.fyzg.wyy.vo.rbac.UserFinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static com.joyin.fyzg.wyy.common.enums.LoginType.*;
import static com.joyin.fyzg.utils.JwtTokenUtil.*;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	UserService userService;

	@Autowired
	PwdService pwdService;

	@Autowired
	AccountService accountService;

	@Autowired
	UserExtService userExtService;

	@Autowired
	FinancierService financierService;

	@Autowired
	FinancierMpService financierMpService;

	@Autowired
	private UserProperties userProperties;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public MethodResponse<LoginDTO> loginByAccount(String account, String password, String pubKey, String verifyCode, String clientId) {
		if (StringUtils.isNotEmpty(verifyCode) && !ImageValidateCodeUtils.chkVerifyCode(verifyCode)) {
			return MethodResponse.success(new LoginDTO(LOGIN_VERIFY_CODE_FAIL.format()));
		}
		DecryptVO decryptVO = RsaUtils.decryptData(password, pubKey);
		if (decryptVO.isStatus()) {
			password = decryptVO.getRPassw();
		}
		else {
			String errorMsg = StringUtils.isNotEmpty(decryptVO.getReason()) ? decryptVO.getReason() : "密码解析失败";
			return MethodResponse.success(new LoginDTO(LOGIN_PAGE_FAIL.format(errorMsg)));
		}
		UserDO userDO = userService.getFirstUserByAccount(account);
		if (userDO == null) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		AccountDO accountDO = accountService.getAccountByAccount(account);
		if (accountDO == null) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		/* 账户状态校验 */
		if (!RbacUserEnable.ENABLE.getValue().equals(accountDO.getEnabled())) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_DISABLED_FAIL.format()));
		}
		try {
			authenticate(accountDO, password);
		}
		catch (Exception e) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		return innerLogin(clientId, null, userDO, accountDO);
	}

	@Override
	public MethodResponse<LoginDTO> login4Cas(String account,String clientId) {
		UserDO userDO = userService.getFirstUserByAccount(account);
		if (userDO == null) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		AccountDO accountDO = accountService.getAccountByAccount(account);
		if (accountDO == null) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		/* 账户状态校验 */
		if (!RbacUserEnable.ENABLE.getValue().equals(accountDO.getEnabled())) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_DISABLED_FAIL.format()));
		}
		return innerLogin(clientId, null, userDO, accountDO);
	}

	private MethodResponse<LoginDTO> innerLogin(String clientId, String financier, UserDO userDO, AccountDO accountDO) {
		String lastFinancier= Optional.ofNullable(financier).orElseGet(()->{
			List<FinancierDO> financierDOList = financierService.listFinancierByCustOCode(userDO.getFinancier());
			return this.getLastFinancier(userDO.getUserOCode(),financierDOList);
		});
		final String token = jwtTokenUtil.generateToken4ClaimsWithExpiration(userDO.getUserOCode(), new HashMap(1 << 1) {{
			put(USER_LOGIN_ACCOUNT, accountDO.getAccount().toString());
			put(USER_LOGIN_STATE, UUID.randomUUID().toString());
			put(CLIENT_ID, clientId);
			put(FINANCIER, lastFinancier);
		}});

		// 写入 USER_LOGIN_STATE
		JedisUtil.STRINGS.setEx(jwtTokenUtil.buildTokenKey(userDO.getUserOCode(), token), jwtTokenUtil.buildTtl(), DateUtil8.getNowTime_EN());

		// 单点登录：记录该用户当前最新 token（仅在开启 singleSignOn 时生效）
        if (userProperties.isSingleSignOn()) {
            JedisUtil.STRINGS.setEx("wyy:single:login:" + userDO.getUserOCode(), jwtTokenUtil.buildTtl(), token);
        }
		
		/* 账户是否需要修改密码 */
		if (userProperties.isForcedModify() && StringUtils.isEmpty(accountDO.getLoginTime())) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_FIRST_LOGIN_FAIL.format(), token));
		}
		if (userProperties.isForcedModify() && DateUtil8.compareDate(accountDO.getValidDate(), DateUtil8.getNowDate_EN()) == -1) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_PWD_VALID_FAIL.format(), token));
		}
		/*增加最后一次登录当前所属机构记录*/
		UserExtDO userExtDO = UserExtDO.builder()
				.userOCode(userDO.getUserOCode())
				.cfgCode(WyyConstants.UserExtCfgCode.LAST_FINANCIER)
				.cfgContent(lastFinancier)
				.build();
		userExtService.saveUserExt(userExtDO);

		writeUserSession(userDO.getUserOCode());

		// 更新登录时间
		userService.updateUserLoginTime(userDO.getUserOCode());
		return MethodResponse.success(new LoginDTO(LOGIN_SUCCESS.format(), token));
	}

	@Override
	public MethodResponse<LoginDTO> switchPermission(String userCode, String financier, String clientId) {
		UserDO userDO = userService.getUserByUserCode(userCode);
		if (userDO == null) {
			return MethodResponse.success(new LoginDTO(LOGIN_USER_NAME_OR_PASSWORD_FAIL.format()));
		}
		AccountDO accountDO = accountService.getAccountByAccount(userDO.getAccount());
//		UserExtDO userExtDO = UserExtDO.builder()
//				.userOCode(userCode)
//				.cfgCode(WyyConstants.UserExtCfgCode.LAST_FINANCIER)
//				.cfgContent(financier)
//				.build();
//		userExtService.saveUserExt(userExtDO);
		return innerLogin(clientId, financier, userDO, accountDO);
	}

	/***
	 * 写入userSession的数据
	 * <br/>
	 * @param userCode
	 * @return void
	 * @author jinyuan.lin
	 * @date 2023/10/28 15:00
	 */
	@Override
	public MethodResponse<String> writeUserSession(String userCode) {
		Map result = userService.getCurrentUserSession(userCode);
		Map data = (Map) result.get("data");
		data.forEach((key, val) -> {
			log.info("Redis 写入数据key：" + key + "_Values：" + val);
			JedisUtil.HASH.hset(SessionConstant.SESSION_USER_INFO_PRE + userCode
					, (key + "").replaceAll(SessionConstant.SESSION_SEPARATE, SessionConstant.SESSION_PLACEHOLDER_SEPARATE)
					, val + "");
		});
		return MethodResponse.success();
	}

	private void authenticate(AccountDO accountDO, String password) {
		Objects.requireNonNull(password, LOGIN_USER_NAME_OR_PASSWORD_FAIL.getMsg());
		Objects.requireNonNull(accountDO, LOGIN_USER_NAME_OR_PASSWORD_FAIL.getMsg());
		// 密码校验
		try {
			String password1 = accountDO.getPassword();
			Assert.isTrue(passwordEncoder.matches(password, password1), LOGIN_USER_NAME_OR_PASSWORD_FAIL.getMsg());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void logout(String userCode, String token) {
		JedisUtil.KEYS.del(jwtTokenUtil.buildTokenKey(userCode, token));
	}

	@Override
	public MethodResponse<List<UserAccountVO>> listUserAccount(String account) {
		List<UserDO> userDOList = userService.listUserByAccount(account);
		List<String> financierList = userDOList.stream()
				.map(UserDO::getFinancier)
				.collect(Collectors.toList());

		List<FinancierDO> financierDOS = financierService.listFinancierByCodeList(financierList);
		Map<String, String> financierMap = financierDOS.stream()
				.collect(Collectors.toMap(
						FinancierDO::getCustOCode,
						FinancierDO::getCustOName
				));
		List<UserAccountVO> userAccountVOList = userDOList.stream().map(item ->
				new UserAccountVO(item.getUserOCode(), item.getUserOName(),
						item.getFinancier(), financierMap.get(item.getFinancier()))).collect(Collectors.toList());
		return MethodResponse.success(userAccountVOList);
	}

	@Override
	public MethodResponse<List<UserFinancierVO>> listUserFinancier(String userCode) {
		UserDO userDO = userService.getUserByUserCode(userCode);
		List<UserFinancierVO> userFinancierVOList = Lists.newArrayList();
		if (StringUtils.isNotEmpty(userDO.getFinancier())) {
			List<FinancierDO> financierDOList = financierService.listFinancierByCustOCode(userDO.getFinancier());
			String lastFinancier = getLastFinancier(userCode, financierDOList);
			userFinancierVOList = financierDOList.stream().map(item -> {
				UserFinancierVO userFinancierVO = new UserFinancierVO(item.getCustOCode(), item.getCustOName(), false);
				if (lastFinancier.equals(userFinancierVO.getFinancierCode())) {
					userFinancierVO.setSelected(true);
				}
				return userFinancierVO;
			}).collect(Collectors.toList());
		}
		return MethodResponse.success(userFinancierVOList);
	}

	private String getLastFinancier(String userCode, List<FinancierDO> financierDOList) {
		UserExtDO userExtFinancier = userExtService.getLastFinancierByUserCode(userCode);
		// 优先从SYS_RBAC_USER_EXT取用户最后切换的机构
		return Optional.ofNullable(userExtFinancier).map(i -> {
			// 存在最后切换的机构且在机构集合中，就返回最后切换的机构
			return financierDOList.stream().anyMatch(j -> j.getCustOCode().equals(i.getCfgContent())) ? i.getCfgContent() : null;
		}).orElseGet(() -> {
			// 否则返回第一个机构
			return financierDOList.stream().sorted(Comparator.comparing(FinancierDO::getCustOCode)).findFirst().map(item -> item.getCustOCode()).get();
		});
	}
}

