package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageRequestDO;

import java.util.List;

public interface MenuPageRequestService {

    MethodResponse insertMenuPageRequest(MenuPageRequestDO menuPageRequestDO);

	MethodResponse batchInsertMenuPageRequest(List<MenuPageRequestDO> menuPageRequestDOList);

	MethodResponse updateMenuPageRequestById(MenuPageRequestDO menuPageRequestDO);

    MethodResponse deleteMenuPageRequestById(String rId);

	MethodResponse deleteMenuPageRequestByPageId(String pageId);

	MenuPageRequestDO getMenuPageRequestById(String rId);

	List<MenuPageRequestDO> listMenuPageRequestByPageId(String pageId);

	List<MenuPageRequestDO> listAll();

	MethodResponse batchSaveMenuPageRequest(List<MenuPageRequestDO> menuPageRequestDOList, String pageId);
}
