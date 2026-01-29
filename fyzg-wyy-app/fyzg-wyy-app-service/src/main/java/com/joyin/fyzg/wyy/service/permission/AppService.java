package com.joyin.fyzg.wyy.service.permission;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.vo.rbac.UserAccountVO;
import com.joyin.fyzg.wyy.vo.rbac.UserFinancierVO;

import java.util.List;

public interface AppService {

	MethodResponse mapRbac4App(String opUserCode, String busType);

}

