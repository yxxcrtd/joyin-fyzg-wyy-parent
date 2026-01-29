package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.common.enums.RoleRelateType;
import com.joyin.fyzg.wyy.entity.rbac.MenuModuleDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.entity.rbac.RoleRelateDO;
import com.joyin.fyzg.wyy.mapper.rbac.RoleRelateMapper;
import com.joyin.fyzg.wyy.service.rbac.MenuModuleService;
import com.joyin.fyzg.wyy.service.rbac.MenuPageService;
import com.joyin.fyzg.wyy.service.rbac.RoleRelateService;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.BATCH_INSERT_FAIL;
import static com.joyin.fyzg.enums.BaseExceptionEnum.INSERT_FAIL;
import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.*;

@Service
@Slf4j
@Transactional
public class RoleRelateServiceImpl implements RoleRelateService {

	@Autowired
	RoleRelateMapper roleRelateMapper;
	@Autowired
	MenuPageService menuPageService;
	@Autowired
	MenuModuleService menuModuleService;

	@Override
	public MethodResponse insertRoleRelate(RoleRelateDO roleRelateDO) {
		try {
			roleRelateMapper.insert(roleRelateDO);
			return MethodResponse.success(roleRelateDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(RoleRelateDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(RoleRelateDO.class, e));
		}
	}

	@Override
	public List<RoleRelateDO> listRoleRelateByRoleCodeAndRType(String roleCode, List<String> rTypeList) {
		QueryWrapper<RoleRelateDO> query = new QueryWrapper<RoleRelateDO>();
		if (StringUtils.isNotEmpty(roleCode)) {
			query.lambda().eq(RoleRelateDO::getRoleCode, roleCode);
		}
		if (CollectionUtils.isNotEmpty(rTypeList)) {
			query.lambda().in(RoleRelateDO::getRType, rTypeList);
		}
		return roleRelateMapper.selectList(query);
	}

	@Override
	public PageResponse<UserVO> listRoleRelateByRoleCodeAndRTypeByPage(String roleCode, List<String> rTypeList, PageWrapper pageWrapper) {
		Page<UserVO> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
		IPage<UserVO> roleRelateDOPage = roleRelateMapper.listRoleRelateByRoleCodeAndRTypeByPage(page, roleCode);
		PageResponse<UserVO> pageResponse = new PageResponse<>(roleRelateDOPage);
		return pageResponse;
	}

	@Override
	public List<RoleRelateDO> listRoleRelateByRoleCodeAndRType(List<String> roleCodeList, List<String> rTypeList) {
		QueryWrapper<RoleRelateDO> query = new QueryWrapper<RoleRelateDO>();
		if (CollectionUtils.isNotEmpty(roleCodeList)) {
			query.lambda().in(RoleRelateDO::getRoleCode, roleCodeList);
		}
		else {
			return Lists.newArrayList();
		}
		if (CollectionUtils.isNotEmpty(rTypeList)) {
			query.lambda().in(RoleRelateDO::getRType, rTypeList);
		}
		else {
			return Lists.newArrayList();
		}
		return roleRelateMapper.selectList(query);
	}

	@Override
	public List<RoleRelateDO> listRoleRelateByRCode1AndRType(String rCode1, List<String> rTypeList) {
		QueryWrapper<RoleRelateDO> query = new QueryWrapper<RoleRelateDO>();
		if (StringUtils.isNotEmpty(rCode1)) {
			query.lambda().eq(RoleRelateDO::getRCode1, rCode1);
		}
		if (CollectionUtils.isNotEmpty(rTypeList)) {
			query.lambda().in(RoleRelateDO::getRType, rTypeList);
		}
		return roleRelateMapper.selectList(query);
	}

	@Override
	public List<RoleRelateDO> listRoleRelateByRCode1ListAndRType(List<String> rCode1List, List<String> rTypeList) {
		QueryWrapper<RoleRelateDO> query = new QueryWrapper<RoleRelateDO>();
		if (CollectionUtils.isNotEmpty(rCode1List)) {
			query.lambda().in(RoleRelateDO::getRCode1, rCode1List);
		}
		if (CollectionUtils.isNotEmpty(rTypeList)) {
			query.lambda().in(RoleRelateDO::getRType, rTypeList);
		}
		return roleRelateMapper.selectList(query);
	}

	@Override
	public List<RoleRelateDO> listRoleRelateByRoleCodeAndRTypeAndPageCode(String roleCode, String pageCode) {
		QueryWrapper<RoleRelateDO> query = new QueryWrapper<RoleRelateDO>();
		if (StringUtils.isNotEmpty(roleCode)) {
			query.lambda().eq(RoleRelateDO::getRoleCode, roleCode);
		}
		if (StringUtils.isNotEmpty(pageCode)) {
			query.lambda().eq(RoleRelateDO::getRCode1, pageCode);
		}
		query.lambda().in(RoleRelateDO::getRType, ImmutableList.of(
				RoleRelateType.MENU_PAGE$REQUEST.getValue(),
				RoleRelateType.MENU_PAGE$ACTION.getValue()));
		return roleRelateMapper.selectList(query);
	}

    @Override
    public String listRoleRelateDataAuToRole(String roleCode) {
        return roleRelateMapper.listRoleRelateDataAuToRole(roleCode);
    }

	@Override
	public MethodResponse saveRoleRelateRoleToMenu4ModuleAndPage(String roleCode, String busCode, List<String> moduleCodeList, List<String> pageCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据模块及页面数据
			roleRelateMapper.deleteRoleRelateRoleToMenu4ModuleAndPage(roleCode, busCode, RoleRelateType.MENU_MODULE.getValue(), moduleCodeList, RoleRelateType.MENU_PAGE.getValue(), pageCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_MENU_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_MENU_FAIL.format(e.getMessage()),e);
		}
		List<Map> rCode1MapList = Lists.newArrayList();
		moduleCodeList.forEach(i -> {
			rCode1MapList.add(ImmutableMap.of("type", RoleRelateType.MENU_MODULE.getValue(), "code", i));
		});
		pageCodeList.forEach(i -> {
			rCode1MapList.add(ImmutableMap.of("type", RoleRelateType.MENU_PAGE.getValue(), "code", i));
		});
		if (rCode1MapList.size() > 0) {
			try {
				//第二步：如果有选中的模块及页面则一次性添加进去
				roleRelateMapper.insertRoleRelateRoleToMenu4ModuleAndPage(roleCode, rCode1MapList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_MENU_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_MENU_FAIL.format(e.getMessage()),e);
			}
		}
		try {
			//第三步：最后删除角色中（指的是传入的roleCode）页面的操作和请求（配置了操作和请求但未配置页面的垃圾数据）
			roleRelateMapper.deleteRoleRelateRoleToMenu4PageActionAndRequest(roleCode, RoleRelateType.MENU_PAGE.getValue(), RoleRelateType.MENU_PAGE$ACTION.getValue(), RoleRelateType.MENU_PAGE$REQUEST.getValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_MENU_ACTION_REQUEST_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_MENU_ACTION_REQUEST_FAIL.format(e.getMessage()),e);
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse saveRoleRelateMenu4PageToRole(String pageCode, String roleCode, Boolean checked) {
		if (checked) {
			try {
				//选中的页面及页面父模块（递归出来）则一次性添加进去
				List<RoleRelateDO> roleRelateDOS = Lists.newArrayList();
				roleRelateDOS.add(RoleRelateDO.builder()
						.rType(RoleRelateType.MENU_PAGE.getValue())
				        .roleCode(roleCode)
				        .rCode1(pageCode)
				        .build());
				MenuPageDO menuPageDO = menuPageService.getMenuPageById(pageCode);
				String mdlId = menuPageDO.getMdlId();
				this.getMenuModuleId4SaveRoleRelate(mdlId,roleRelateDOS,roleCode);
				roleRelateDOS.forEach(roleRelateDO -> roleRelateMapper.insert(roleRelateDO));
//				roleRelateMapper.insertRoleRelateMenu4PageToRole(roleCode, pageCode, RoleRelateType.MENU_MODULE.getValue(), RoleRelateType.MENU_PAGE.getValue());
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_INSERT_4_PAGE_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_INSERT_4_PAGE_FAIL.format(e.getMessage()),e);
			}
		}
		else {
			//删除角色关联的菜单下操作及请求数据
			this.deleteRoleRelate4PageActionAndRequest(roleCode, pageCode);
			//删除角色关联的菜单数据
			this.deleteRoleRelate4Page(roleCode, pageCode);
		}
		return MethodResponse.success();
	}
    //递归获取父节点
	private void getMenuModuleId4SaveRoleRelate(String mdlId,List<RoleRelateDO> roleRelateDOS,String roleCode){
		if (mdlId == null){
			return ;
		}
		MenuModuleDO menuModule = menuModuleService.getMenuModuleById(mdlId);
		if (menuModule == null){
			return ;
		}
		roleRelateDOS.add(RoleRelateDO.builder()
		.rType(RoleRelateType.MENU_MODULE.getValue())
		.roleCode(roleCode)
		.rCode1(menuModule.getRId())
		.build());
		this.getMenuModuleId4SaveRoleRelate(menuModule.getPMdlId(),roleRelateDOS,roleCode);
	}


	public MethodResponse deleteRoleRelate4Page(String roleCode, String pageCode) {
		try {
			roleRelateMapper.delete(new QueryWrapper<RoleRelateDO>()
					.lambda()
					.eq(RoleRelateDO::getRoleCode, roleCode)
					.eq(RoleRelateDO::getRCode1, pageCode)
					.eq(RoleRelateDO::getRType, RoleRelateType.MENU_PAGE)
			);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_DELETE_4_PAGE_FAIL.format(e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_DELETE_4_PAGE_FAIL.format(e.getMessage()), e);
		}
	}

	public MethodResponse deleteRoleRelate4PageActionAndRequest(String roleCode, String pageCode) {
		try {
			roleRelateMapper.delete(new QueryWrapper<RoleRelateDO>()
					.lambda()
					.eq(RoleRelateDO::getRoleCode, roleCode)
					.eq(RoleRelateDO::getRCode1, pageCode)
					.in(RoleRelateDO::getRType, ImmutableList.of(
							RoleRelateType.MENU_PAGE$ACTION.getValue(),
							RoleRelateType.MENU_PAGE$REQUEST.getValue()))
			);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_PAGE_FAIL.format(e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_PAGE_FAIL.format(e.getMessage()), e);
		}
	}

	private MethodResponse batchInsertRoleRelate(List<RoleRelateDO> RoleRelateDOList) {
		try {
			RoleRelateDOList.forEach(roleRelateMapper::insert);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(BATCH_INSERT_FAIL.formatEntity(RoleRelateDO.class, e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(BATCH_INSERT_FAIL.formatEntity(RoleRelateDO.class, e), null);
		}
	}

	@Override
	public MethodResponse batchSaveRoleRelate4Page(List<RoleRelateDO> roleRelateDOList, String roleCode, String pageCode) {
		try {
			this.deleteRoleRelate4PageActionAndRequest(roleCode, pageCode);
			this.batchInsertRoleRelate(roleRelateDOList);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_SAVE_4_PAGE_FAIL.format(e).getErrorMsg(), e);
			return ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_SAVE_4_PAGE_FAIL.format(e.getMessage()), e);
		}
	}

	@Override
	public MethodResponse batchSaveRoleRelate4RoleToUser(String roleCode, List<String> userCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4RoleToUser(roleCode, RoleRelateType.USER.getValue(), userCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_USER_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_USER_FAIL.format(e.getMessage()),e);
		}
		if (userCodeList.size() > 0) {
			try {
				//第二步：如果有选中的用户一次性添加进去

				LambdaQueryWrapper<RoleRelateDO> queryWrapper = new QueryWrapper<RoleRelateDO>().lambda()
						.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
						.eq(RoleRelateDO::getRoleCode, roleCode)
						.in(RoleRelateDO::getRCode1, userCodeList);
				List<RoleRelateDO> relateDOList = roleRelateMapper.selectList(queryWrapper);
				List<String> dbUserCodeList = relateDOList.stream().map(i -> i.getRCode1()).collect(Collectors.toList());
				userCodeList.stream().distinct().filter(userCode->!dbUserCodeList.contains(userCode)).forEach(wfCode->{
					RoleRelateDO roleRelateDO = RoleRelateDO.builder()
							.rType(RoleRelateType.USER.getValue())
							.roleCode(roleCode)
							.rCode1(wfCode)
							.build();
					roleRelateMapper.insert(roleRelateDO);
				});
//				roleRelateMapper.insertRoleRelate4RoleToUser(roleCode, RoleRelateType.USER.getValue(), userCodeList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_USER_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_USER_FAIL.format(e.getMessage()),e);
			}
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse batchSaveRoleRelate4UserToRole(String userCode, List<String> roleCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4UserToRole(userCode, RoleRelateType.USER.getValue(), roleCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_USER_TO_ROLE_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_USER_TO_ROLE_FAIL.format(e.getMessage()),e);
		}
		if (roleCodeList.size() > 0) {
			try {
				//第二步：如果有选中的用户一次性添加进去
				LambdaQueryWrapper<RoleRelateDO> queryWrapper = new QueryWrapper<RoleRelateDO>().lambda()
						.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
						.in(RoleRelateDO::getRoleCode, roleCodeList)
						.eq(RoleRelateDO::getRCode1, userCode);
				List<RoleRelateDO> relateDOList = roleRelateMapper.selectList(queryWrapper);
				if (CollectionUtils.isEmpty(relateDOList)){
					roleCodeList.stream().distinct().forEach(roleCode->{
						RoleRelateDO roleRelateDO = RoleRelateDO.builder()
								.rType(RoleRelateType.USER.getValue())
								.roleCode(roleCode)
								.rCode1(userCode)
								.build();
						roleRelateMapper.insert(roleRelateDO);
					});
				}
//				roleRelateMapper.insertRoleRelate4UserToRole(userCode, RoleRelateType.USER.getValue(), roleCodeList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_USER_TO_ROLE_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_USER_TO_ROLE_FAIL.format(e.getMessage()),e);
			}
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse batchSaveRoleRelate4RoleToWf(String roleCode, List<String> wfCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4RoleToWf(roleCode, RoleRelateType.WF.getValue(), wfCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_WF_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_WF_FAIL.format(e.getMessage()),e);
		}
		if (wfCodeList.size() > 0) {
			try {
				//第二步：如果有选中的流程环节一次性添加进去
				LambdaQueryWrapper<RoleRelateDO> queryWrapper = new QueryWrapper<RoleRelateDO>().lambda()
						.eq(RoleRelateDO::getRType, RoleRelateType.WF.getValue())
						.eq(RoleRelateDO::getRoleCode, roleCode)
						.in(RoleRelateDO::getRCode1, wfCodeList);
				List<RoleRelateDO> relateDOList = roleRelateMapper.selectList(queryWrapper);
				if (CollectionUtils.isEmpty(relateDOList)){
					wfCodeList.stream().distinct().forEach(wfCode->{
						RoleRelateDO roleRelateDO = RoleRelateDO.builder()
								.rType(RoleRelateType.WF.getValue())
								.roleCode(roleCode)
								.rCode1(wfCode)
								.build();
						roleRelateMapper.insert(roleRelateDO);
					});
				}
//				roleRelateMapper.insertRoleRelate4RoleToWf(roleCode, RoleRelateType.WF.getValue(), wfCodeList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_WF_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_WF_FAIL.format(e.getMessage()),e);
			}
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse batchSaveRoleRelate4RoleToCa(String roleCode, List<Map> docList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4RoleToCa(roleCode, RoleRelateType.CA.getValue(), docList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DOC_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DOC_FAIL.format(e.getMessage()),e);
		}
		if (docList.size() > 0) {
			try {
				//第二步：如果有选中的流程环节一次性添加进去
				roleRelateMapper.insertRoleRelate4RoleToCa(roleCode, RoleRelateType.CA.getValue(), docList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DOC_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DOC_FAIL.format(e.getMessage()),e);
			}
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse batchSaveRoleRelate4RoleToDesktopExtend(String roleCode, String type, List<String> blockCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4RoleToDesktop(roleCode, type, blockCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DESKTOP_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DESKTOP_FAIL.format(e.getMessage()),e);
		}
		if (blockCodeList.size() > 0) {
			try {
				//第二步：如果有选中的流程环节一次性添加进去
				roleRelateMapper.insertRoleRelate4RoleToDesktop(roleCode, type, blockCodeList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DESKTOP_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DESKTOP_FAIL.format(e.getMessage()),e);
			}
		}
		return MethodResponse.success();
	}

	@Override
	public MethodResponse batchSaveRoleRelate4WfToRole(String wfCode, List<String> roleCodeList) {
		try {
			//第一步：先删除角色中（指的是传入的roleCode）不在选中范围内的数据
			roleRelateMapper.deleteRoleRelate4WfToRole(wfCode, RoleRelateType.WF.getValue(), roleCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_WF_TO_ROLE_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_WF_TO_ROLE_FAIL.format(e.getMessage()),e);
		}
		if (roleCodeList.size() > 0) {
			try {
				//第二步：如果有选中的流程环节一次性添加进去
				roleRelateMapper.insertRoleRelate4WfToRole(wfCode, RoleRelateType.WF.getValue(), roleCodeList);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_WF_TO_ROLE_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_WF_TO_ROLE_FAIL.format(e.getMessage()),e);

			}
		}
		return MethodResponse.success();
	}

	@Override
	public List<String> listComponentByUserCodeAndType(String userCode, String type) {
		List<String> components = roleRelateMapper.listComponentByUserCodeAndType(userCode, type);
		return components;
	}

    @Override
    public MethodResponse saveRoleRelateDataAuToRole(String roleCode, String dataAu) {
        try {
            roleRelateMapper.updateRoleRelateDataAuToRole(roleCode, dataAu);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(ROLE_RELATE_UPDATE_4_ROLE_TO_DATA_AU_FAIL.format(e).getErrorMsg(), e);
            ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_UPDATE_4_ROLE_TO_DATA_AU_FAIL.format(e.getMessage()),e);
        }
        return MethodResponse.success();
    }


	@Override
	public MethodResponse batchAddRoleRelate4RoleToUser(String roleCode, List<String> userCodeList) {
		if (userCodeList.size() > 0) {
			try {
				//选中的用户一次性添加进去
				LambdaQueryWrapper<RoleRelateDO> queryWrapper = new QueryWrapper<RoleRelateDO>().lambda()
						.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
						.eq(RoleRelateDO::getRoleCode, roleCode)
						.in(RoleRelateDO::getRCode1, userCodeList);
				List<RoleRelateDO> relateDOList = roleRelateMapper.selectList(queryWrapper);
				List<String> dbUserCodeList = relateDOList.stream().map(i -> i.getRCode1()).collect(Collectors.toList());
				userCodeList.stream().distinct().filter(userCode -> !dbUserCodeList.contains(userCode)).forEach(wfCode -> {
					RoleRelateDO roleRelateDO = RoleRelateDO.builder()
							.rType(RoleRelateType.USER.getValue())
							.roleCode(roleCode)
							.rCode1(wfCode)
							.build();
					roleRelateMapper.insert(roleRelateDO);
				});
			} catch (Exception e) {
				e.printStackTrace();
				log.error(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_USER_FAIL.format(e).getErrorMsg(), e);
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_USER_FAIL.format(e.getMessage()), e);
			}
		}
		return MethodResponse.success();
	}


	@Override
	public MethodResponse batchDeleteRoleRelate4RoleToUser(String roleCode, List<String> userCodeList) {
		try {
			//删除角色中的用户
			LambdaQueryWrapper<RoleRelateDO> queryWrapper = new QueryWrapper<RoleRelateDO>().lambda()
					.eq(RoleRelateDO::getRType, RoleRelateType.USER.getValue())
					.eq(RoleRelateDO::getRoleCode, roleCode)
					.in(RoleRelateDO::getRCode1, userCodeList);
			roleRelateMapper.delete(queryWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_USER_FAIL.format(e).getErrorMsg(), e);
			ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_USER_FAIL.format(e.getMessage()), e);
		}
		return MethodResponse.success();
	}
}
