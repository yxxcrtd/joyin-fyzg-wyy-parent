package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageParameterDO;

import java.util.List;

public interface MenuPageParameterService {

    MethodResponse insertMenuPageParameter(MenuPageParameterDO menuPageParameterDO);

	MethodResponse batchInsertMenuPageParameter(List<MenuPageParameterDO> menuPageParameterDOList);

	MethodResponse updateMenuPageParameterById(MenuPageParameterDO menuPageParameterDO);

    MethodResponse deleteMenuPageParameterById(String rId);

	MethodResponse deleteMenuPageParameterByPageId(String pageId);

	MenuPageParameterDO getMenuPageParameterById(String rId);

	List<MenuPageParameterDO> listAll();

	List<MenuPageParameterDO> listMenuPageParameterByPageId(String pageId);

	MethodResponse batchSaveMenuPageParameter(List<MenuPageParameterDO> menuPageParameterDOList, String pageId);

	MethodResponse updateMenuParameter(RequestParamMap params);
}
