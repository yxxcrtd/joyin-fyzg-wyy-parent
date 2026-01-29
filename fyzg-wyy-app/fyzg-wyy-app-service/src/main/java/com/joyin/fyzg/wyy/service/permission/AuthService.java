package com.joyin.fyzg.wyy.service.permission;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.common.dto.LoginDTO;
import com.joyin.fyzg.wyy.vo.rbac.UserAccountVO;
import com.joyin.fyzg.wyy.vo.rbac.UserFinancierVO;

import java.util.List;

public interface AuthService {

	MethodResponse<LoginDTO> loginByAccount(String account, String password, String pubKey, String verifyCode, String clientId);

    MethodResponse<LoginDTO> login4Cas(String account, String clientId);

    MethodResponse<LoginDTO> switchPermission(String userCode, String financier, String clientId);

	MethodResponse<String> writeUserSession(String userCode);

	void logout(String userCode, String token);

	MethodResponse<List<UserAccountVO>> listUserAccount(String account);

	MethodResponse<List<UserFinancierVO>> listUserFinancier(String userCode);
}

