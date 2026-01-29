package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuModuleDO;
import com.joyin.fyzg.wyy.mapper.rbac.MenuModuleMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuModuleService;
import com.joyin.fyzg.wyy.service.rbac.MenuPageService;
import com.joyin.fyzg.vo.enums.FlagType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_MODULE_DELETE_EXIST_SUB_MODULE_FAIL;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_MODULE_DELETE_EXIST_SUB_PAGE_FAIL;

;

@Service
@Slf4j
@Transactional
public class MenuModuleServiceImpl implements MenuModuleService {

	@Autowired
	MenuModuleMapper menuModuleMapper;
	@Autowired
	MenuPageService menuPageService;

	@Override
	public MethodResponse insertMenuModule(MenuModuleDO menuModuleDO) {
		try {
			menuModuleMapper.insert(menuModuleDO);
			return MethodResponse.success(menuModuleDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuModuleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MenuModuleDO.class, e));
		}
	}

	@Override
	public MethodResponse updateMenuModuleById(MenuModuleDO menuModuleDO) {
		try {
			menuModuleMapper.updateById(menuModuleDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuModuleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MenuModuleDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteMenuModuleById(String rId) {
		try {
			if (existsChildren(rId)) {
				log.warn(MENU_MODULE_DELETE_EXIST_SUB_MODULE_FAIL.format().getErrorMsg());
				return MethodResponse.transWithCodeMsgEnum(MENU_MODULE_DELETE_EXIST_SUB_MODULE_FAIL.format());
			}
			else if (menuPageService.existsChildren(rId)){
				log.warn(MENU_MODULE_DELETE_EXIST_SUB_PAGE_FAIL.format().getErrorMsg());
				return MethodResponse.transWithCodeMsgEnum(MENU_MODULE_DELETE_EXIST_SUB_PAGE_FAIL.format());
			}
			else{
				menuModuleMapper.deleteById(rId);
				return MethodResponse.success();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuModuleDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MenuModuleDO.class, e));
		}
	}

	private Boolean existsChildren(String pMdlId) {
		QueryWrapper<MenuModuleDO> query = new QueryWrapper<>();
		query.lambda().eq(MenuModuleDO::getPMdlId, pMdlId);
		return menuModuleMapper.selectCount(query) > 0;
	}

	@Override
	public MenuModuleDO getMenuModuleById(String rId) {
		return menuModuleMapper.selectById(rId);
	}

	@Override
	public List<MenuModuleDO> listMenuModuleByBusType(String busType) {
		QueryWrapper<MenuModuleDO> query = new QueryWrapper<MenuModuleDO>();
		if (StringUtils.isNotEmpty(busType)) {
			query.lambda().eq(MenuModuleDO::getBusType, busType);
		}
		return menuModuleMapper.selectList(query);
	}

	@Override
	public List<MenuModuleDO> listAllEnabled() {
		QueryWrapper<MenuModuleDO> query = new QueryWrapper<MenuModuleDO>();
		query.lambda().eq(MenuModuleDO::getEnabled, FlagType.YES.getValue());
		return menuModuleMapper.selectList(query);
	}

	@Override
	public List<MenuModuleDO> listAll() {
		return menuModuleMapper.selectList();
	}
}
