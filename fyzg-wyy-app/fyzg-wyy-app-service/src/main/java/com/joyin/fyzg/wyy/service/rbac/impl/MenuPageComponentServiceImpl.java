package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageActionDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageComponentDO;
import com.joyin.fyzg.wyy.mapper.rbac.MenuPageComponentMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuPageComponentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_PAGE_COMPONENT_BATCH_SAVE_FAIL;


@Service
@Slf4j
@Transactional
public class MenuPageComponentServiceImpl implements MenuPageComponentService {

	@Autowired
	MenuPageComponentMapper menuPageComponentMapper;

	@Override
	public MethodResponse insertMenuPageComponent(MenuPageComponentDO menuPageComponentDO) {
		try {
			menuPageComponentMapper.insert(menuPageComponentDO);
			return MethodResponse.success(menuPageComponentDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MenuPageComponentDO.class, e));
		}
	}

	@Override
	public MethodResponse batchInsertMenuPageComponent(List<MenuPageComponentDO> menuPageComponentDOList) {
		try {
			menuPageComponentDOList.forEach(menuPageComponentMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.formatEntity(MenuPageComponentDO.class, e), null);
		}
	}

	@Override
	public MethodResponse updateMenuPageComponentById(MenuPageComponentDO menuPageComponentDO) {
		try {
			menuPageComponentMapper.updateById(menuPageComponentDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MenuPageComponentDO.class, e));
		}
	}

	private MethodResponse batchUpdateMenuPageComponent(List<MenuPageComponentDO> menuPageComponentDOList) {
		try {
			menuPageComponentDOList.forEach(menuPageComponentMapper::updateById);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_UPDATE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.formatEntity(MenuPageComponentDO.class, e), null);
		}
	}

	@Override
	public MethodResponse deleteMenuPageComponentById(String rId) {
		try {
			menuPageComponentMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MenuPageComponentDO.class, e));
		}
	}

	private MethodResponse batchDeleteMenuPageComponent(List<String> idList) {
		try {
			if (CollectionUtils.isNotEmpty(idList)){
				menuPageComponentMapper.delete(new QueryWrapper<MenuPageComponentDO>()
						.lambda()
						.in(MenuPageComponentDO::getRId, idList));
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteMenuPageComponentByPageId(String pageId) {
		try {
			menuPageComponentMapper.delete(new QueryWrapper<MenuPageComponentDO>()
					.lambda()
					.eq(MenuPageComponentDO::getPageId, pageId));
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.formatEntity(MenuPageComponentDO.class, e), null);
		}
	}

	@Override
	public MenuPageComponentDO getMenuPageComponentById(String rId) {
		return menuPageComponentMapper.selectById(rId);
	}

	@Override
	public List<MenuPageComponentDO> listAll() {
		return menuPageComponentMapper.selectList();
	}

	@Override
	public List<MenuPageComponentDO> listMenuPageComponentByPageId(String pageId) {
		return menuPageComponentMapper.selectList(new QueryWrapper<MenuPageComponentDO>()
				.lambda()
				.eq(MenuPageComponentDO::getPageId, pageId));
	}

	@Override
	public MethodResponse batchSaveMenuPageComponent(List<MenuPageComponentDO> menuPageComponentDOList, String pageId) {
		try {
			List<MenuPageComponentDO> insertList = menuPageComponentDOList.stream().filter(i -> i.getRId() == null).collect(Collectors.toList());
			List<MenuPageComponentDO> updateList = menuPageComponentDOList.stream().filter(i -> i.getRId() != null).collect(Collectors.toList());
			List<String> existsList = updateList.stream().map(i -> i.getRId()).collect(Collectors.toList());
			List<MenuPageComponentDO> menuPageComponentDOList1 = this.listMenuPageComponentByPageId(pageId);
			List<String> deleteList = menuPageComponentDOList1.stream().filter(i -> !existsList.contains(i.getRId())).map(i -> i.getRId()).collect(Collectors.toList());

			this.batchInsertMenuPageComponent(insertList);
			this.batchUpdateMenuPageComponent(updateList);
			this.batchDeleteMenuPageComponent(deleteList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_SAVE_FAIL.formatEntity(MenuPageComponentDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.formatEntity(MenuPageComponentDO.class, e), null);
		}
	}
}
