package com.joyin.fyzg.wyy.service.rbac.impl;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserMicroMappingDO;
import com.joyin.fyzg.wyy.mapper.rbac.UserMicroMappingMapper;
import com.joyin.fyzg.wyy.service.rbac.UserMicroMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joyin.fyzg.enums.BaseExceptionEnum.INSERT_FAIL;
import static com.joyin.fyzg.enums.BaseExceptionEnum.UPDATE_FAIL;

@Service
@Slf4j
public class UserMicroMappingServiceImpl implements UserMicroMappingService {

	@Autowired
	UserMicroMappingMapper userMicroMappingMapper;

	@Override
	public MethodResponse insertUserMicroMapping(UserMicroMappingDO userMicroMappingDO) {
		try {
			userMicroMappingMapper.insert(userMicroMappingDO);
			return MethodResponse.success(userMicroMappingDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(UserMicroMappingDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(UserMicroMappingDO.class, e));
		}
	}

	@Override
	public MethodResponse updateUserMicroMappingById(UserMicroMappingDO userMicroMappingDO) {
		try {
			userMicroMappingMapper.updateById(userMicroMappingDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserMicroMappingDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserMicroMappingDO.class, e));
		}
	}
}
