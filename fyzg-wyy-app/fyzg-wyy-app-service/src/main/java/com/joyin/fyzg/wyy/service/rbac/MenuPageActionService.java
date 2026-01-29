package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageActionDO;

import java.util.List;

public interface MenuPageActionService {

    MethodResponse insertMenuPageAction(MenuPageActionDO menuPageActionDO);

	MethodResponse batchInsertMenuPageAction(List<MenuPageActionDO> menuPageActionDOList);

	MethodResponse updateMenuPageActionById(MenuPageActionDO menuPageActionDO);

    MethodResponse deleteMenuPageActionById(String rId);

	MethodResponse deleteMenuPageActionByPageId(String pageId);

	MenuPageActionDO getMenuPageActionById(String rId);

	List<MenuPageActionDO> listMenuPageActionByPageId(String pageId);

	List<MenuPageActionDO> listAll();

	MethodResponse batchSaveMenuPageAction(List<MenuPageActionDO> menuPageActionDOList, String pageId);
}
