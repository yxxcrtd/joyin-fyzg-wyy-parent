package com.joyin.fyzg.wyy.service.permission;

import com.joyin.fyzg.common.MethodResponse;

public interface PwdService {

	MethodResponse updateUser4ResetPwd(Long rId);

	MethodResponse updateUser4Pwd(String oldPwd, String pwd, String account, String userCode, boolean firstModifyPwd);

	String getPwdValidDate(String loginUserCode);
}

