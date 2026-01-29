package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageVO;

import java.util.List;

public interface MenuPageService {

    MethodResponse insertMenuPage(MenuPageVO menuPageVO);

    MethodResponse updateMenuPageById(MenuPageVO menuPageVO);

    MethodResponse deleteMenuPageById(String rId);

    MenuPageDO getMenuPageById(String rId);

    List<MenuPageDO> listMenuPageByBusType(String busType);

    List<MenuPageDO> listAll();

    List<MenuPageDO> listAllEnabled();

    Boolean existsChildren(String rId);

    MethodResponse updateMenuMovePageById(MenuPageDO menuPageDO);

    MethodResponse copyMenuPageById(MenuPageDO menuPageDO);
}
