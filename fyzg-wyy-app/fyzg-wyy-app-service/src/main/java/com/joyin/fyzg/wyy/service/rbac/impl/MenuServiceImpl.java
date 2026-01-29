package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.common.enums.RoleRelateType;
import com.joyin.fyzg.wyy.common.utils.MenuUtils;
import com.joyin.fyzg.wyy.entity.rbac.ProductDO;
import com.joyin.fyzg.wyy.entity.rbac.*;
import com.joyin.fyzg.wyy.mapper.rbac.RoleRelateMapper;
import com.joyin.fyzg.wyy.service.rbac.ProductService;
import com.joyin.fyzg.wyy.service.rbac.*;
import com.joyin.fyzg.wyy.vo.rbac.BusinessVO;
import com.joyin.fyzg.wyy.vo.rbac.ContainerVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuVO;
import com.joyin.fyzg.utils.ExecutorServiceUtils;
import com.joyin.fyzg.wrapper.ScheduledThreadPoolExecutorMdcWrapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static com.joyin.fyzg.wyy.common.exception.RbacExceptionEnum.ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_WF_FAIL;

/**
 * MenuServiceImpl
 * <br/>
 *
 * @author pengzhen
 * @date 2019/9/25 0025 下午 5:22
 */
@Service
@Slf4j
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MenuUtils menuUtils;
	@Autowired
	MenuModuleService menuModuleService;
	@Autowired
	MenuPageService menuPageService;
	@Autowired
	MenuPageComponentService menuPageComponentService;
	@Autowired
	MenuPageParameterService menuPageParameterService;
	@Autowired
	MenuPageActionService menuPageActionService;
	@Autowired
	MenuPageRequestService menuPageRequestService;
	@Autowired
	RoleRelateService roleRelateService;
	@Autowired
	ProductService productService;
	@Autowired
	RoleRelateMapper roleRelateMapper;

	@Override
	public List<Map<String, Object>> getTypeList() {
		List<ProductDO> productDOS = productService.listAllEnabled();

		List<Map<String, Object>> maps = productDOS.stream().map(item -> {
			Map<String, Object> temp = Maps.newHashMap();
			temp.put("code", item.getCode());
			temp.put("name", item.getName());
			temp.put("isDefault", item.getIsDefault());
			return temp;
		}).collect(Collectors.toList());
		return maps;
	}

	@Override
	public List<ContainerVO> listAllTree() {
		List<ContainerVO> list = new ArrayList<ContainerVO>();
		List<Map<String, Object>> mapList = this.getTypeList();
		List<MenuModuleDO> menuModuleDOList = menuModuleService.listAll();
		List<MenuPageDO> menuPageDOList = menuPageService.listAll();
		List<MenuPageComponentDO> menuPageComponentDOList = menuPageComponentService.listAll();
		List<MenuPageParameterDO> menuPageParameterDOList = menuPageParameterService.listAll();
		List<MenuPageActionDO> menuPageActionDOList = menuPageActionService.listAll();
		List<MenuPageRequestDO> menuPageRequestDOList = menuPageRequestService.listAll();
		mapList.forEach(item -> {
			BusinessVO businessVO = modelMapper.map(item, BusinessVO.class);
			List<MenuVO> voList = menuUtils.buildVOTreeList(item.get("code").toString(), menuModuleDOList, menuPageDOList, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList);
			list.add(ContainerVO.builder().menuVOList(voList).businessVO(businessVO).build());
		});
		return list;
	}

	@Override
	public List<ContainerVO> listAllTree4Publish() {
		List<ContainerVO> list = Lists.newArrayList();
		List<Map<String, Object>> mapList = this.getTypeList();
		List<MenuModuleDO> menuModuleDOList = menuModuleService.listAll();
		List emptyList = Lists.newArrayList();
		mapList.forEach(item -> {
			BusinessVO businessVO = modelMapper.map(item, BusinessVO.class);
			List<MenuVO> voList = menuUtils.buildVOTreeList(item.get("code").toString(), menuModuleDOList, emptyList, emptyList, emptyList, emptyList, emptyList);
			list.add(ContainerVO.builder().menuVOList(voList).businessVO(businessVO).build());
		});
		return list;
	}

	@Override
	public List<MenuVO> listTree(String busType, String menuName, String enabled) {
		List<MenuModuleDO> menuModuleDOList = menuModuleService.listMenuModuleByBusType(busType);
		List<MenuPageDO> menuPageDOList = menuPageService.listMenuPageByBusType(busType);
		List<MenuPageComponentDO> menuPageComponentDOList = menuPageComponentService.listAll();
		List<MenuPageParameterDO> menuPageParameterDOList = menuPageParameterService.listAll();
		List<MenuPageActionDO> menuPageActionDOList = menuPageActionService.listAll();
		List<MenuPageRequestDO> menuPageRequestDOList = menuPageRequestService.listAll();
		if (StringUtils.isEmpty(menuName) && StringUtils.isEmpty(enabled)) {
			return menuUtils.buildVOTreeList(busType, menuModuleDOList, menuPageDOList, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList);
		}
		else {
			List<MenuModuleDO> menuModuleDOListTemp = menuModuleDOList.stream().filter(item -> {
				boolean menuNameFlag = false;
				boolean enabledFlag = false;
				if (StringUtils.isNotEmpty(menuName)) {
					menuNameFlag = item.getMdlName().contains(menuName);
				}
				else {
					menuNameFlag = true;
				}
				if (StringUtils.isNotEmpty(enabled)) {
					enabledFlag = item.getEnabled().equals(enabled);
				}
				else {
					enabledFlag = true;
				}
				return menuNameFlag && enabledFlag;
			}).collect(Collectors.toList());
			List<MenuPageDO> menuPageDOListTemp = menuPageDOList.stream().filter(item -> {
				boolean menuNameFlag = false;
				boolean enabledFlag = false;
				if (StringUtils.isNotEmpty(menuName)) {
					menuNameFlag = item.getPageName().contains(menuName);
				}
				else {
					menuNameFlag = true;
				}
				if (StringUtils.isNotEmpty(enabled)) {
					enabledFlag = item.getEnabled().equals(enabled);
				}
				else {
					enabledFlag = true;
				}
				return menuNameFlag && enabledFlag;
			}).collect(Collectors.toList());
			return this.transVOTreeList(busType, menuModuleDOListTemp, menuPageDOListTemp, menuModuleDOList, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList);
		}
	}

	@Override
	public List<MenuVO> listAllTree4RoleRelate() {
		List<MenuVO> list = Lists.newArrayList();
		List<Map<String, Object>> mapList = this.getTypeList();
		List<MenuModuleDO> menuModuleDOList = menuModuleService.listAll();
		List<MenuPageDO> menuPageDOList = menuPageService.listAll();
		List emptyList = Lists.newArrayList();
		mapList.forEach(item -> {
			BusinessVO businessVO = modelMapper.map(item, BusinessVO.class);
			MenuVO rootMenuVO = MenuVO.builder().key("BUSINESS" + item.get("code").toString())
					.menuName(item.get("name").toString())
					.menuType("BUSINESS")
					.children(menuUtils.buildVOTreeList(item.get("code").toString(), menuModuleDOList, menuPageDOList, emptyList, emptyList, emptyList, emptyList))
					.build();
			list.add(rootMenuVO);
		});
		return list;
	}

	@Override
	public Map<String, List> mapActionAndRequestAndRoleRelate(String roleCode, String pageCode) {
		HashMap<String, List> map = Maps.newHashMap();
		List<MenuPageActionDO> menuPageActionDOList = Lists.newArrayList();
		List<MenuPageRequestDO> menuPageRequestDOList = Lists.newArrayList();
		List<RoleRelateDO> roleRelateDOList = Lists.newArrayList();
		ExecutorService executorService = new ScheduledThreadPoolExecutorMdcWrapper(3);
		try {
			CompletableFuture[] cfs = new CompletableFuture[] {
					CompletableFuture.supplyAsync(() -> menuPageActionService.listMenuPageActionByPageId(pageCode), executorService)
							.whenComplete((menuPageActionDOListTemp, e) -> {
						menuPageActionDOList.addAll(menuPageActionDOListTemp);
					}),
					CompletableFuture.supplyAsync(() -> menuPageRequestService.listMenuPageRequestByPageId(pageCode), executorService)
							.whenComplete((menuPageRequestDOListTemp, e) -> {
						menuPageRequestDOList.addAll(menuPageRequestDOListTemp);
					}),
					CompletableFuture.supplyAsync(() -> roleRelateService.listRoleRelateByRoleCodeAndRTypeAndPageCode(roleCode, pageCode), executorService)
							.whenComplete((roleRelateDOListTemp, e) -> {
						roleRelateDOList.addAll(roleRelateDOListTemp);
					}),
			};
			CompletableFuture.allOf(cfs).join();
		}
		finally {
			ExecutorServiceUtils.shutdown(executorService);
		}
		map.put("menuPageActionDOList", menuPageActionDOList);
		map.put("menuPageRequestDOList", menuPageRequestDOList);
		map.put("roleRelateDOList", roleRelateDOList);
		return map;
	}

	@Override
	public MethodResponse saveRoleRelatePage4ActionAndRequest(String roleCode, String pageCode, List<MenuPageActionDO> menuPageActionDOList, List<MenuPageRequestDO> menuPageRequestDOList) {
		List<RoleRelateDO> roleRelateDOList = Lists.newArrayList();
		menuPageActionDOList.forEach(item -> {
			if (StringUtils.isNotEmpty(item.getDefPermission())) {
				roleRelateDOList.add(RoleRelateDO.builder()
						.rType(RoleRelateType.MENU_PAGE$ACTION.getValue())
						.roleCode(roleCode)
						.rCode1(pageCode)
						.rCode2(item.getRId().toString())
						.rCode3(item.getDefPermission())
						.build()
				);
			}

		});
		menuPageRequestDOList.forEach(item -> {
			if (StringUtils.isNotEmpty(item.getDefPermission())) {
				roleRelateDOList.add(RoleRelateDO.builder()
						.rType(RoleRelateType.MENU_PAGE$REQUEST.getValue())
						.roleCode(roleCode)
						.rCode1(pageCode)
						.rCode2(item.getRId().toString())
						.rCode3(item.getDefPermission())
						.build()
				);
			}

		});
		return roleRelateService.batchSaveRoleRelate4Page(roleRelateDOList, roleCode, pageCode);
	}


	private MethodResponse batchSaveRoleRelate4RoleToWf(String roleCode, List<String> wfCodeList) {
		if (wfCodeList.size() > 0) {
			try {
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
				ChainExceptionUtils.throwExceptionWithReturn(ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_WF_FAIL.format(e.getMessage()), e);
			}
		}
		return MethodResponse.success();
	}

	private List<MenuVO> transVOTreeList(String busType, List<MenuModuleDO> menuModuleDOListTemp, List<MenuPageDO> menuPageDOListTemp, List<MenuModuleDO> menuModuleDOList, List<MenuPageComponentDO> menuPageComponentDOList, List<MenuPageParameterDO> menuPageParameterDOList, List<MenuPageActionDO> menuPageActionDOList, List<MenuPageRequestDO> menuPageRequestDOList) {
		List<MenuModuleDO> resultList = Lists.newArrayList();
		menuModuleDOListTemp.forEach(item -> {
			this.recursionMenuModule(item.getPMdlId(), resultList, menuModuleDOList);
		});
		menuPageDOListTemp.forEach(item -> {
			this.recursionMenuModule(item.getMdlId(), resultList, menuModuleDOList);
		});
		resultList.addAll(menuModuleDOListTemp);
		return menuUtils.buildVOTreeList(busType, resultList.stream().distinct().collect(Collectors.toList()), menuPageDOListTemp, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList);
	}

	private void recursionMenuModule(String mdlId, List<MenuModuleDO> resultList, List<MenuModuleDO> menuModuleDOList) {
		if (StringUtils.isNotEmpty(mdlId) && !"0".equals(mdlId)) {
			//不为空且不为字符'0'的情况下
			menuModuleDOList.forEach(item -> {
				if (item.getRId().equals(mdlId)) {
					resultList.add(item);
					this.recursionMenuModule(item.getPMdlId(), resultList, menuModuleDOList);
				}
			});
		}
	}
}
