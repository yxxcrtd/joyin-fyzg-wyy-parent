package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageComponentDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageRequestDO;
import com.joyin.fyzg.wyy.mapper.rbac.MenuPageRequestMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuPageRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.MENU_PAGE_REQUEST_BATCH_SAVE_FAIL;


@Service
@Slf4j
@Transactional
public class MenuPageRequestServiceImpl implements MenuPageRequestService {

	@Autowired
	MenuPageRequestMapper menuPageRequestMapper;

	@Override
	public MethodResponse insertMenuPageRequest(MenuPageRequestDO menuPageRequestDO) {
		try {
			menuPageRequestMapper.insert(menuPageRequestDO);
			return MethodResponse.success(menuPageRequestDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MenuPageRequestDO.class, e));
		}
	}

	@Override
	public MethodResponse batchInsertMenuPageRequest(List<MenuPageRequestDO> menuPageRequestDOList) {
		try {
			menuPageRequestDOList.forEach(menuPageRequestMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.formatEntity(MenuPageRequestDO.class, e), null);
		}
	}

	@Override
	public MethodResponse updateMenuPageRequestById(MenuPageRequestDO menuPageRequestDO) {
		try {
			menuPageRequestMapper.updateById(menuPageRequestDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MenuPageRequestDO.class, e));
		}
	}

	private MethodResponse batchUpdateMenuPageRequest(List<MenuPageRequestDO> menuPageRequestDOList) {
		try {
			menuPageRequestDOList.forEach(menuPageRequestMapper::updateById);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_UPDATE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.formatEntity(MenuPageRequestDO.class, e), null);
		}
	}

	@Override
	public MethodResponse deleteMenuPageRequestById(String rId) {
		try {
			menuPageRequestMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MenuPageRequestDO.class, e));
		}
	}

	private MethodResponse batchDeleteMenuPageRequest(List<String> idList) {
		try {
			if (CollectionUtils.isNotEmpty(idList)){
				menuPageRequestMapper.delete(new QueryWrapper<MenuPageRequestDO>()
						.lambda()
						.in(MenuPageRequestDO::getRId, idList));
			}
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse deleteMenuPageRequestByPageId(String pageId) {
		try {
			menuPageRequestMapper.delete(new QueryWrapper<MenuPageRequestDO>()
					.lambda()
					.eq(MenuPageRequestDO::getPageId, pageId));
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_DELETE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.formatEntity(MenuPageRequestDO.class, e), null);
		}
	}

	@Override
	public MenuPageRequestDO getMenuPageRequestById(String rId) {
		return menuPageRequestMapper.selectById(rId);
	}

	@Override
	public List<MenuPageRequestDO> listMenuPageRequestByPageId(String pageId) {
		return menuPageRequestMapper.selectList(new QueryWrapper<MenuPageRequestDO>()
				.lambda()
				.eq(MenuPageRequestDO::getPageId, pageId));
	}

	@Override
	public List<MenuPageRequestDO> listAll() {
		return menuPageRequestMapper.selectList();
	}

	@Override
	public MethodResponse batchSaveMenuPageRequest(List<MenuPageRequestDO> menuPageRequestDOList, String pageId) {
		try {
			List<MenuPageRequestDO> insertList = menuPageRequestDOList.stream().filter(i -> i.getRId() == null).collect(Collectors.toList());
			List<MenuPageRequestDO> updateList = menuPageRequestDOList.stream().filter(i -> i.getRId() != null).collect(Collectors.toList());
			List<String> existsList = updateList.stream().map(i -> i.getRId()).collect(Collectors.toList());
			List<MenuPageRequestDO> menuPageRequestDOList1 = this.listMenuPageRequestByPageId(pageId);
			List<String> deleteList = menuPageRequestDOList1.stream().filter(i -> !existsList.contains(i.getRId())).map(i -> i.getRId()).collect(Collectors.toList());

			this.batchInsertMenuPageRequest(insertList);
			this.batchUpdateMenuPageRequest(updateList);
			this.batchDeleteMenuPageRequest(deleteList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_SAVE_FAIL.formatEntity(MenuPageRequestDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.formatEntity(MenuPageRequestDO.class, e), null);
		}
	}
}
