package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageComponentDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageParameterDO;
import com.joyin.fyzg.wyy.mapper.rbac.MenuPageParameterMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuPageParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_PAGE_PARAMETER_BATCH_SAVE_FAIL;


@Service
@Slf4j
@Transactional
public class MenuPageParameterServiceImpl implements MenuPageParameterService {

	@Autowired
	MenuPageParameterMapper menuPageParameterMapper;

	@Value("${parameterId.bizModel}")
	String bizModel;

	@Value("${parameterId.sysModel}")
	String sysModel;
	@Override
	public MethodResponse insertMenuPageParameter(MenuPageParameterDO menuPageParameterDO) {
		try {
			menuPageParameterMapper.insert(menuPageParameterDO);
			return MethodResponse.success(menuPageParameterDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MenuPageParameterDO.class, e));
		}
	}

	@Override
	public MethodResponse batchInsertMenuPageParameter(List<MenuPageParameterDO> menuPageParameterDOList) {
		try {
			menuPageParameterDOList.forEach(menuPageParameterMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.formatEntity(MenuPageParameterDO.class, e), null);
		}
	}

	@Override
	public MethodResponse updateMenuPageParameterById(MenuPageParameterDO menuPageParameterDO) {
		try {
			menuPageParameterMapper.updateById(menuPageParameterDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MenuPageParameterDO.class, e));
		}
	}

	private MethodResponse batchUpdateMenuPageParameter(List<MenuPageParameterDO> menuPageParameterDOList) {
		try {
			menuPageParameterDOList.forEach(menuPageParameterMapper::updateById);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_UPDATE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.formatEntity(MenuPageParameterDO.class, e), null);
		}
	}

	@Override
	public MethodResponse deleteMenuPageParameterById(String rId) {
		try {
			menuPageParameterMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MenuPageParameterDO.class, e));
		}
	}

	private MethodResponse batchDeleteMenuPageParameter(List<String> idList) {
		try {
			if (CollectionUtils.isNotEmpty(idList)){
				menuPageParameterMapper.delete(new QueryWrapper<MenuPageParameterDO>()
						.lambda()
						.in(MenuPageParameterDO::getRId, idList));
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteMenuPageParameterByPageId(String pageId) {
		try {
			menuPageParameterMapper.delete(new QueryWrapper<MenuPageParameterDO>()
					.lambda()
					.eq(MenuPageParameterDO::getPageId, pageId));
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.formatEntity(MenuPageParameterDO.class, e), null);
		}
	}

	@Override
	public MenuPageParameterDO getMenuPageParameterById(String rId) {
		return menuPageParameterMapper.selectById(rId);
	}

	@Override
	public List<MenuPageParameterDO> listAll() {
		return menuPageParameterMapper.selectList();
	}

	@Override
	public List<MenuPageParameterDO> listMenuPageParameterByPageId(String pageId) {
		return menuPageParameterMapper.selectList(new QueryWrapper<MenuPageParameterDO>()
				.lambda()
				.eq(MenuPageParameterDO::getPageId, pageId));
	}

	@Override
	public MethodResponse batchSaveMenuPageParameter(List<MenuPageParameterDO> menuPageParameterDOList, String pageId) {
		try {
			List<MenuPageParameterDO> insertList = menuPageParameterDOList.stream().filter(i -> i.getRId() == null).collect(Collectors.toList());
			List<MenuPageParameterDO> updateList = menuPageParameterDOList.stream().filter(i -> i.getRId() != null).collect(Collectors.toList());
			List<String> existsList = updateList.stream().map(i -> i.getRId()).collect(Collectors.toList());
			List<MenuPageParameterDO> menuPageParameterDOList1 = this.listMenuPageParameterByPageId(pageId);
			List<String> deleteList = menuPageParameterDOList1.stream().filter(i -> !existsList.contains(i.getRId())).map(i -> i.getRId()).collect(Collectors.toList());

			this.batchInsertMenuPageParameter(insertList);
			this.batchUpdateMenuPageParameter(updateList);
			this.batchDeleteMenuPageParameter(deleteList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_SAVE_FAIL.formatEntity(MenuPageParameterDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.formatEntity(MenuPageParameterDO.class, e), null);
		}
	}

	@Override
	public MethodResponse updateMenuParameter(RequestParamMap params) {
		MenuPageParameterDO menuPageParameterDO = new MenuPageParameterDO();
		if ("MLDFSYSModelDefine".equals(params.getStringValueOfNullable("modelType"))){
			menuPageParameterDO.setRId(sysModel);
			menuPageParameterDO.setParamValue(params.getStringValueOfNullable("oTypeCode"));
			this.updateMenuPageParameterById(menuPageParameterDO);
			menuPageParameterDO.setRId(bizModel);
			this.updateMenuPageParameterById(menuPageParameterDO);
		}
		return MethodResponse.success();
	}
}
