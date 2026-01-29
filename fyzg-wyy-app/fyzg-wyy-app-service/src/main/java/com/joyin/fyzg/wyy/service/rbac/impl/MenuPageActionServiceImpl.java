package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageActionDO;
import com.joyin.fyzg.wyy.mapper.rbac.MenuPageActionMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuPageActionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_PAGE_ACTION_BATCH_SAVE_FAIL;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.ROLE_RELATE_BATCH_INSERT_4_WF_TO_ROLE_FAIL;


@Service
@Slf4j
@Transactional
public class MenuPageActionServiceImpl implements MenuPageActionService {

	@Autowired
	MenuPageActionMapper menuPageActionMapper;

	@Override
	public MethodResponse insertMenuPageAction(MenuPageActionDO menuPageActionDO) {
		try {
			menuPageActionMapper.insert(menuPageActionDO);
			return MethodResponse.success(menuPageActionDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MenuPageActionDO.class, e));
		}
	}

	@Override
	public MethodResponse batchInsertMenuPageAction(List<MenuPageActionDO> menuPageActionDOList) {
		try {
			menuPageActionDOList.forEach(menuPageActionMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse updateMenuPageActionById(MenuPageActionDO menuPageActionDO) {
		try {
			menuPageActionMapper.updateById(menuPageActionDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MenuPageActionDO.class, e));
		}
	}

	private MethodResponse batchUpdateMenuPageAction(List<MenuPageActionDO> menuPageActionDOList) {
		try {
			menuPageActionDOList.forEach(menuPageActionMapper::updateById);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_UPDATE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteMenuPageActionById(String rId) {
		try {
			menuPageActionMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MenuPageActionDO.class, e));
		}
	}

	private MethodResponse batchDeleteMenuPageAction(List<String> idList) {
		try {
			if (CollectionUtils.isNotEmpty(idList)){
				menuPageActionMapper.delete(new QueryWrapper<MenuPageActionDO>()
						.lambda()
						.in(MenuPageActionDO::getRId, idList));
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteMenuPageActionByPageId(String pageId) {
		try {
			menuPageActionMapper.delete(new QueryWrapper<MenuPageActionDO>()
					.lambda()
					.eq(MenuPageActionDO::getPageId, pageId));
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.formatEntity(MenuPageActionDO.class, e), null);
		}
	}

	@Override
	public MenuPageActionDO getMenuPageActionById(String rId) {
		return menuPageActionMapper.selectById(rId);
	}

	@Override
	public List<MenuPageActionDO> listMenuPageActionByPageId(String pageId) {
		return menuPageActionMapper.selectList(new QueryWrapper<MenuPageActionDO>()
				.lambda()
				.eq(MenuPageActionDO::getPageId, pageId));
	}

	@Override
	public List<MenuPageActionDO> listAll() {
		return menuPageActionMapper.selectList();
	}

	@Override
	public MethodResponse batchSaveMenuPageAction(List<MenuPageActionDO> menuPageActionDOList, String pageId) {
		try {
			List<MenuPageActionDO> insertList = menuPageActionDOList.stream().filter(i -> i.getRId() == null).collect(Collectors.toList());
			List<MenuPageActionDO> updateList = menuPageActionDOList.stream().filter(i -> i.getRId() != null).collect(Collectors.toList());
			List<String> existsList = updateList.stream().map(i -> i.getRId()).collect(Collectors.toList());
			List<MenuPageActionDO> menuPageActionDOList1 = this.listMenuPageActionByPageId(pageId);
			List<String> deleteList = menuPageActionDOList1.stream().filter(i -> !existsList.contains(i.getRId())).map(i -> i.getRId()).collect(Collectors.toList());

			this.batchInsertMenuPageAction(insertList);
			this.batchUpdateMenuPageAction(updateList);
			this.batchDeleteMenuPageAction(deleteList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_SAVE_FAIL.formatEntity(MenuPageActionDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.formatEntity(MenuPageActionDO.class, e), null);
		}
	}
}
