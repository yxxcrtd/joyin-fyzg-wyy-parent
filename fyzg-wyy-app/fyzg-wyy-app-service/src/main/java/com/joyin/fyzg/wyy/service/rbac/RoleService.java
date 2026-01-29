package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.rbac.RoleDO;

import java.util.List;
import java.util.Map;

public interface RoleService {

	MethodResponse insertRole(RoleDO roleDO);

	MethodResponse updateRoleById(RoleDO roleDO);

	MethodResponse deleteRoleById(Long rId);

	RoleDO getRoleById(Long rId);

	List<RoleDO> listRoleByCodeList(List<String> codeList);

	List<RoleDO> listAll();
	Map<String, Object> listRolesByCondition(String roleOCode, String roleOName, PageWrapper pageWrapper);

	List<Map> getJobPermissions();
	List<Map> getDataPermissions();

}
