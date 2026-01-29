package com.joyin.fyzg.wyy.service.roleRelevanceUser.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.common.enums.RoleRelateType;
import com.joyin.fyzg.wyy.entity.rbac.RoleRelateDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.rbac.RoleRelateMapper;
import com.joyin.fyzg.wyy.mapper.roleRelevanceUser.RoleRelevanceUserMapper;
import com.joyin.fyzg.wyy.service.roleRelevanceUser.RoleRelevanceUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleRelevanceUserServiceImpl implements RoleRelevanceUserService {

	@Autowired
	RoleRelateMapper roleRelateMapper;

	@Autowired
	RoleRelevanceUserMapper roleRelevanceUserMapper;


	@Override
	public IPage<UserDO> listUserByRoleCodeAndUserName(String roleCode, String userName, Integer page, Integer pageSize) {
		return roleRelevanceUserMapper.listUserByRoleCodeAndUserName(new Page<>(page, pageSize), RoleRelateType.USER.getValue(), roleCode, userName);
	}

	@Override
	public MethodResponse insertRoleRelevanceUser(String roleCode, String userCode) {
		QueryWrapper<RoleRelateDO> wrapper = new QueryWrapper<>();
		wrapper.lambda()
				.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
				.eq(RoleRelateDO::getRoleCode, roleCode)
				.eq(RoleRelateDO::getRCode1, userCode);
		if (roleRelateMapper.selectCount(wrapper)>0){
			return MethodResponse.success("duplicated");
		}
		RoleRelateDO roleRelateDO = RoleRelateDO.builder()
				.rType(RoleRelateType.USER.getValue())
				.roleCode(roleCode)
				.rCode1(userCode)
				.build();
		roleRelateMapper.insert(roleRelateDO);
		return MethodResponse.success();
	}

	@Override
	public MethodResponse deleteRoleRelevanceUser(String roleCode, String userCode) {
		QueryWrapper<RoleRelateDO> wrapper = new QueryWrapper<>();
		wrapper.lambda()
				.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
				.eq(RoleRelateDO::getRoleCode, roleCode)
				.eq(RoleRelateDO::getRCode1, userCode);
		roleRelateMapper.delete(wrapper);
		return MethodResponse.success();
	}
}
