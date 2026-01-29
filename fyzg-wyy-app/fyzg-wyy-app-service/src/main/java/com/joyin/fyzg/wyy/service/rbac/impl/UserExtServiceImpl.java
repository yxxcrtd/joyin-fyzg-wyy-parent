package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.constant.WyyConstants;
import com.joyin.fyzg.wyy.entity.rbac.UserExtDO;
import com.joyin.fyzg.wyy.mapper.rbac.UserExtMapper;
import com.joyin.fyzg.wyy.service.rbac.UserExtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joyin.fyzg.enums.BaseExceptionEnum.INSERT_FAIL;
import static com.joyin.fyzg.enums.BaseExceptionEnum.UPDATE_FAIL;

@Service
@Slf4j
public class UserExtServiceImpl implements UserExtService {

	@Autowired
	UserExtMapper userExtMapper;

	@Override
	public MethodResponse insertUserExt(UserExtDO userExtDO) {
		try {
			userExtMapper.insert(userExtDO);
			return MethodResponse.success(userExtDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(UserExtDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(UserExtDO.class, e));
		}
	}

	@Override
	public MethodResponse updateUserExtById(UserExtDO userExtDO) {
		try {
			userExtMapper.updateById(userExtDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserExtDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserExtDO.class, e));
		}
	}

	@Override
	public MethodResponse saveUserExt(UserExtDO userExtDO) {
		try {
			UserExtDO oldUserExt = this.getUserExtByCode(userExtDO.getUserOCode(), userExtDO.getCfgCode());
			if (null!=oldUserExt) {
				//	用新的配置数据更新旧的
				oldUserExt.setCfgContent(userExtDO.getCfgContent());
				return this.updateUserExtById(oldUserExt);
			} else {
				return this.insertUserExt(userExtDO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(UserExtDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(UserExtDO.class, e));
		}
	}

	@Override
	public UserExtDO getUserExtByCode(String userCode, String cfgCode) {
		QueryWrapper<UserExtDO> query = new QueryWrapper<>();
		query.lambda().eq(UserExtDO::getUserOCode, userCode).eq(UserExtDO::getCfgCode, cfgCode);
		return userExtMapper.selectOne(query);
	}

	@Override
	public UserExtDO getLastFinancierByUserCode(String userCode) {
		return this.getUserExtByCode(userCode, WyyConstants.UserExtCfgCode.LAST_FINANCIER);
	}

}
