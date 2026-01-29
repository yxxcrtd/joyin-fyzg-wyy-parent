package com.joyin.fyzg.wyy.service.roleRelevanceUser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2024/11/12 15:56
 */
public interface RoleRelevanceUserService {
	IPage<UserDO> listUserByRoleCodeAndUserName(String roleCode, String userName, Integer page, Integer pageSize);

	MethodResponse insertRoleRelevanceUser(String roleCode, String userCode);

	MethodResponse deleteRoleRelevanceUser(String roleCode, String userCode);
}
