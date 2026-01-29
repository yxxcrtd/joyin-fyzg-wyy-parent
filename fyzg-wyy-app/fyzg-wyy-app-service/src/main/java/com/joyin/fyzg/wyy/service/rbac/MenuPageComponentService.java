package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageComponentDO;

import java.util.List;

public interface MenuPageComponentService {

    MethodResponse insertMenuPageComponent(MenuPageComponentDO menuPageComponentDO);

	MethodResponse batchInsertMenuPageComponent(List<MenuPageComponentDO> menuPageComponentDOList);

    MethodResponse updateMenuPageComponentById(MenuPageComponentDO menuPageComponentDO);

    MethodResponse deleteMenuPageComponentById(String rId);

	MethodResponse deleteMenuPageComponentByPageId(String pageId);

	MenuPageComponentDO getMenuPageComponentById(String rId);

	List<MenuPageComponentDO> listAll();

	List<MenuPageComponentDO> listMenuPageComponentByPageId(String pageId);

	MethodResponse batchSaveMenuPageComponent(List<MenuPageComponentDO> menuPageComponentDOList, String pageId);
}
