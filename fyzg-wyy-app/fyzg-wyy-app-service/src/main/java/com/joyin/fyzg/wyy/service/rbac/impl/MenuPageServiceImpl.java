package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.rbac.*;
import com.joyin.fyzg.wyy.mapper.rbac.MenuPageMapper;
import com.joyin.fyzg.wyy.service.rbac.*;
import com.joyin.fyzg.wyy.vo.rbac.*;
import com.joyin.fyzg.vo.enums.FlagType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_PAGE_COPY_FAIL;

;

@Service
@Slf4j
@Transactional
public class MenuPageServiceImpl implements MenuPageService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MenuPageMapper menuPageMapper;
	@Autowired
	MenuPageComponentService menuPageComponentService;
	@Autowired
	MenuPageParameterService menuPageParameterService;
	@Autowired
	MenuPageActionService menuPageActionService;
	@Autowired
	MenuPageRequestService menuPageRequestService;

	@Override
	public MethodResponse insertMenuPage(MenuPageVO menuPageVO) {
		try {
			MenuPageDO menuPageDO = modelMapper.map(menuPageVO, MenuPageDO.class);
			menuPageMapper.insert(menuPageDO);
			List<MenuPageComponentDO> menuPageComponentDOList = this.buildMenuPageComponentDOList(menuPageVO.getComponentList(), menuPageDO.getRId());
			menuPageComponentService.batchInsertMenuPageComponent(menuPageComponentDOList);
			List<MenuPageParameterDO> menuPageParameterDOList = this.buildMenuPageParameterDOList(menuPageVO.getParameterList(), menuPageDO.getRId());
			menuPageParameterService.batchInsertMenuPageParameter(menuPageParameterDOList);
			List<MenuPageActionDO> menuPageActionDOList = this.buildMenuPageActionDOList(menuPageVO.getActionList(), menuPageDO.getRId());
			menuPageActionService.batchSaveMenuPageAction(menuPageActionDOList, menuPageDO.getRId());
			List<MenuPageRequestDO> menuPageRequestDOList = this.buildMenuPageRequestDOList(menuPageVO.getRequestList(), menuPageDO.getRId());
			menuPageRequestService.batchInsertMenuPageRequest(menuPageRequestDOList);
			return MethodResponse.success(menuPageDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuPageDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(INSERT_FAIL.formatEntity(MenuPageDO.class, e),null);
		}
	}

	private List<MenuPageComponentDO> buildMenuPageComponentDOList(List<MenuPageComponentVO> componentList, String pageId) {
		List<MenuPageComponentDO> list = Lists.newArrayList();
		Optional.ofNullable(componentList).ifPresent(i -> i.forEach(component -> {
			MenuPageComponentDO menuPageComponentDO = modelMapper.map(component, MenuPageComponentDO.class);
			menuPageComponentDO.setPageId(pageId);
			list.add(menuPageComponentDO);
		}));
		return list;
	}

	private List<MenuPageParameterDO> buildMenuPageParameterDOList(List<MenuPageParameterVO> parameterList, String pageId) {
		List<MenuPageParameterDO> list = Lists.newArrayList();
		Optional.ofNullable(parameterList).ifPresent(i -> i.forEach(parameter -> {
			MenuPageParameterDO menuPageParameterDO = modelMapper.map(parameter, MenuPageParameterDO.class);
			menuPageParameterDO.setPageId(pageId);
			list.add(menuPageParameterDO);
		}));
		return list;
	}

	private List<MenuPageActionDO> buildMenuPageActionDOList(List<MenuPageActionVO> actionList, String pageId) {
		List<MenuPageActionDO> list = Lists.newArrayList();
		Optional.ofNullable(actionList).ifPresent(i -> i.forEach(request -> {
			MenuPageActionDO menuPageActionDO = modelMapper.map(request, MenuPageActionDO.class);
			menuPageActionDO.setPageId(pageId);
			list.add(menuPageActionDO);
		}));
		return list;
	}

	private List<MenuPageRequestDO> buildMenuPageRequestDOList(List<MenuPageRequestVO> requestList, String pageId) {
		List<MenuPageRequestDO> list = Lists.newArrayList();
		Optional.ofNullable(requestList).ifPresent(i -> i.forEach(request -> {
			MenuPageRequestDO menuPageRequestDO = modelMapper.map(request, MenuPageRequestDO.class);
			menuPageRequestDO.setPageId(pageId);
			list.add(menuPageRequestDO);
		}));
		return list;
	}

	@Override
	public MethodResponse updateMenuPageById(MenuPageVO menuPageVO) {
		try {
			MenuPageDO menuPageDO = modelMapper.map(menuPageVO, MenuPageDO.class);
			menuPageMapper.updateById(menuPageDO);
			List<MenuPageComponentDO> menuPageComponentDOList = this.buildMenuPageComponentDOList(menuPageVO.getComponentList(), menuPageDO.getRId());
			menuPageComponentService.batchSaveMenuPageComponent(menuPageComponentDOList, menuPageDO.getRId());
			List<MenuPageParameterDO> menuPageParameterDOList = this.buildMenuPageParameterDOList(menuPageVO.getParameterList(), menuPageDO.getRId());
			menuPageParameterService.batchSaveMenuPageParameter(menuPageParameterDOList, menuPageDO.getRId());
			List<MenuPageActionDO> menuPageActionDOList = this.buildMenuPageActionDOList(menuPageVO.getActionList(), menuPageDO.getRId());
			menuPageActionService.batchSaveMenuPageAction(menuPageActionDOList, menuPageDO.getRId());
			List<MenuPageRequestDO> menuPageRequestDOList = this.buildMenuPageRequestDOList(menuPageVO.getRequestList(), menuPageDO.getRId());
			menuPageRequestService.batchSaveMenuPageRequest(menuPageRequestDOList, menuPageDO.getRId());
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuPageDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(UPDATE_FAIL.formatEntity(MenuPageDO.class, e),null);
		}
	}

	@Override
	public MethodResponse deleteMenuPageById(String rId) {
		try {
			menuPageMapper.deleteById(rId);
			menuPageComponentService.deleteMenuPageComponentByPageId(rId);
			menuPageParameterService.deleteMenuPageParameterByPageId(rId);
			menuPageActionService.deleteMenuPageActionByPageId(rId);
			menuPageRequestService.deleteMenuPageRequestByPageId(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuPageDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(DELETE_FAIL.formatEntity(MenuPageDO.class, e),null);
		}
	}

	@Override
	public MenuPageDO getMenuPageById(String rId) {
		return menuPageMapper.selectById(rId);
	}

	@Override
	public List<MenuPageDO> listMenuPageByBusType(String busType) {
		QueryWrapper<MenuPageDO> query = new QueryWrapper<MenuPageDO>();
		if (StringUtils.isNotEmpty(busType)) {
			query.lambda().eq(MenuPageDO::getBusType, busType);
		}
		return menuPageMapper.selectList(query);
	}

	@Override
	public List<MenuPageDO> listAll() {
		return menuPageMapper.selectList();
	}

	@Override
	public List<MenuPageDO> listAllEnabled() {
		QueryWrapper<MenuPageDO> query = new QueryWrapper<MenuPageDO>();
		query.lambda().eq(MenuPageDO::getEnabled, FlagType.YES.getValue());
		return menuPageMapper.selectList(query);
	}

	@Override
	public Boolean existsChildren(String mdlId) {
		QueryWrapper<MenuPageDO> query = new QueryWrapper<>();
		query.lambda().eq(MenuPageDO::getMdlId, mdlId);
		return menuPageMapper.selectCount(query) > 0;
	}

	@Override
	public MethodResponse updateMenuMovePageById(MenuPageDO menuPageDO) {
		menuPageMapper.updateById(menuPageDO);
		return MethodResponse.success();
	}

	/**
	 * @Description 复制菜单
	 * @Author zihongshuai
	 * @Date 2021/5/31 9:49
	 */
	@Override
	public MethodResponse copyMenuPageById(MenuPageDO menuPageDO) {
		try {
			// 查出菜单相应的信息
			MenuPageDO pageDO = menuPageMapper.selectById(menuPageDO.getRId());
			List<MenuPageComponentDO> menuPageComponentDOS = menuPageComponentService.listMenuPageComponentByPageId(pageDO.getRId());
			List<MenuPageParameterDO> menuPageParameterDOS = menuPageParameterService.listMenuPageParameterByPageId(pageDO.getRId());
			List<MenuPageActionDO> menuPageActionDOS = menuPageActionService.listMenuPageActionByPageId(pageDO.getRId());
			List<MenuPageRequestDO> menuPageRequestDOS = menuPageRequestService.listMenuPageRequestByPageId(pageDO.getRId());

			// 页面
			pageDO.setRId(null);
			pageDO.setMdlId(menuPageDO.getMdlId());
			pageDO.setBusType(menuPageDO.getBusType());
			menuPageMapper.insert(pageDO);

			// 组件
			menuPageComponentDOS.forEach(item->{
                item.setPageId(pageDO.getRId());
                item.setRId(null);
            });
			menuPageComponentService.batchInsertMenuPageComponent(menuPageComponentDOS);

			// 参数
			menuPageParameterDOS.forEach(item->{
                item.setPageId(pageDO.getRId());
                item.setRId(null);
            });
			menuPageParameterService.batchInsertMenuPageParameter(menuPageParameterDOS);

			// 动作
			menuPageActionDOS.forEach(item->{
                item.setPageId(pageDO.getRId());
                item.setRId(null);
            });
			menuPageActionService.batchInsertMenuPageAction(menuPageActionDOS);

			// 请求
			menuPageRequestDOS.forEach(item->{
                item.setPageId(pageDO.getRId());
                item.setRId(null);
            });
			menuPageRequestService.batchInsertMenuPageRequest(menuPageRequestDOS);
			return MethodResponse.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(MENU_PAGE_COPY_FAIL.format(e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgFormatEnum(MENU_PAGE_COPY_FAIL, e);
		}
	}
}
