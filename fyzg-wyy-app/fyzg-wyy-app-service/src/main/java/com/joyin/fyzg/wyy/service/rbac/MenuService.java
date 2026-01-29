package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageActionDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageRequestDO;
import com.joyin.fyzg.wyy.vo.rbac.ContainerVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuVO;

import java.util.List;
import java.util.Map;

public interface MenuService {

	List<Map<String,Object>> getTypeList();

	List<ContainerVO> listAllTree();

	List<ContainerVO> listAllTree4Publish();

	List<MenuVO> listTree(String busType, String menuName, String enabled);

	List<MenuVO> listAllTree4RoleRelate();

	Map<String, List> mapActionAndRequestAndRoleRelate(String roleCode, String pageCode);

	MethodResponse saveRoleRelatePage4ActionAndRequest(String roleCode, String pageCode, List<MenuPageActionDO> menuPageActionDOList, List<MenuPageRequestDO> menuPageRequestDOList);

}
