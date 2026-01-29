package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.shine.eusp.iopara.EUSPOutput;

public interface ShineSyncService {

	EUSPOutput login();
	EUSPOutput insertUser(UserDO userDO);
	EUSPOutput updateUserById(UserDO userDO);
	EUSPOutput deleteUserById(String user_id);
	EUSPOutput updateUser4ResetPwd(String user_id);

}
