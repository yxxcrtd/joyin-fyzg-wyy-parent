package com.joyin.fyzg.wyy.mapper.roleRelevanceUser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.AccountDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * RBAC_USER表的DAO层类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
public interface RoleRelevanceUserMapper extends SuperMapper {

	IPage<UserDO> listUserByRoleCodeAndUserName(IPage<UserDO> page,
			@Param("rType") String rType,
			@Param("roleCode") String roleCode,
			@Param("userName") String userName
	);
}

