package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuModuleDO;

import java.util.List;

public interface MenuModuleService {

    MethodResponse insertMenuModule(MenuModuleDO menuModuleDO);

    MethodResponse updateMenuModuleById(MenuModuleDO menuModuleDO);

    MethodResponse deleteMenuModuleById(String rId);

	MenuModuleDO getMenuModuleById(String rId);

	List<MenuModuleDO> listMenuModuleByBusType(String busType);

	List<MenuModuleDO> listAllEnabled();

	List<MenuModuleDO> listAll();
}
