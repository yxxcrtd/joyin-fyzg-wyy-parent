package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserExtDO;

public interface UserExtService {

    MethodResponse insertUserExt(UserExtDO userExtDO);

    MethodResponse updateUserExtById(UserExtDO userExtDO);

    MethodResponse saveUserExt(UserExtDO userExtDO);

	UserExtDO getUserExtByCode(String userCode, String cfgCode);

	UserExtDO getLastFinancierByUserCode(String userCode);
}
