package com.joyin.fyzg.wyy.controller.rbac;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.utils.JwtTokenUtil;
import com.joyin.fyzg.utils.RsaUtils;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.permission.PwdService;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@Slf4j
public class PwdController extends BaseController {

	@Autowired
	PwdService pwdService;

	@PostMapping("updateUser4Pwd")
	public RestResponse updateUser4Pwd(@RequestBody RequestParamMap paramMap) {
		String oldPwd = paramMap.getStringValueOf("oldPwd");
		String pwd = paramMap.getStringValueOf("pwd");
		String pubkey = paramMap.getStringValueOfNullable("pubkey");
		boolean firstModifyPwd = paramMap.getBooleanValueOfNullable("firstModifyPwd");

		oldPwd = RsaUtils.decryptDataStr(oldPwd, pubkey);
		pwd = RsaUtils.decryptDataStr(pwd, pubkey);
		if(org.apache.commons.lang.StringUtils.isEmpty(oldPwd) || org.apache.commons.lang.StringUtils.isEmpty(pwd)){
			return RestResponse.error("密码解析失败");
		}
		return RestResponse.transMethodResponse(pwdService.updateUser4Pwd(oldPwd, pwd, super.getTokenClaim(a -> a.get(JwtTokenUtil.USER_LOGIN_ACCOUNT, String.class)), super.getLoginUserCode(), firstModifyPwd));
	}

	@PostMapping("updateUser4ResetPwd")
	public RestResponse updateUser4ResetPwd(@RequestParam("rId") Long rId) {
		return RestResponse.transMethodResponse(pwdService.updateUser4ResetPwd(rId));
	}
}
