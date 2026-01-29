package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserMicroMappingDO;

public interface UserMicroMappingService {

    MethodResponse insertUserMicroMapping(UserMicroMappingDO userMicroMappingDO);

    MethodResponse updateUserMicroMappingById(UserMicroMappingDO userMicroMappingDO);

}
