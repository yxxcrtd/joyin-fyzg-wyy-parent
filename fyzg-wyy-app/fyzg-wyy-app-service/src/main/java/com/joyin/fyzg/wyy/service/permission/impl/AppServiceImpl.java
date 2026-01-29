package com.joyin.fyzg.wyy.service.permission.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.user.UserProperties;
import com.joyin.fyzg.wyy.common.enums.PageActionPermissionType;
import com.joyin.fyzg.wyy.common.enums.RoleRelateType;
import com.joyin.fyzg.wyy.common.utils.MenuUtils;
import com.joyin.fyzg.wyy.entity.rbac.*;
import com.joyin.fyzg.wyy.mapper.rbac.AppMapper;
import com.joyin.fyzg.wyy.service.financier.FinancierMpService;
import com.joyin.fyzg.wyy.service.financier.FinancierService;
import com.joyin.fyzg.wyy.service.permission.AppService;
import com.joyin.fyzg.wyy.service.permission.AuthService;
import com.joyin.fyzg.wyy.service.rbac.*;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.ExecutorServiceUtils;
import com.joyin.fyzg.vo.enums.FlagType;
import com.joyin.fyzg.wrapper.ScheduledThreadPoolExecutorMdcWrapper;
import com.joyin.fyzg.wyy.vo.rbac.UserFinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

;

@Service
@Slf4j
@Transactional
public class AppServiceImpl implements AppService {

	//默认密码
//	@Value("${user.adminAccount}")
//	private String adminAccount;


	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserProperties userProperties ;

	@Autowired
	AppMapper appMapper;

	@Autowired
	MenuUtils menuUtils;

	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;

	@Autowired
	FinancierService financierService;

	@Autowired
	FinancierMpService financierMpService;

	@Autowired
	UserExtService userExtService;

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
	RoleRelateService roleRelateService;

	@Override
	public MethodResponse mapRbac4App(String opUserCode, String busType) {
		Map map = Maps.newHashMap();
		List emptyList = Lists.newArrayList();
		ExecutorService executorService = new ScheduledThreadPoolExecutorMdcWrapper(8);
		List<MenuModuleDO> menuModuleDOList = Lists.newArrayList();
		List<MenuPageDO> menuPageDOList = Lists.newArrayList();
		List<MenuPageComponentDO> menuPageComponentDOList = Lists.newArrayList();
		List<MenuPageParameterDO> menuPageParameterDOList = Lists.newArrayList();
		List<MenuPageActionDO> menuPageActionDOList = Lists.newArrayList();
		List<RoleRelateDO> roleRelateDOList = Lists.newArrayList();
		AtomicReference<UserDO> userDO = new AtomicReference<UserDO>();
		try {
			CompletableFuture[] cfs = new CompletableFuture[7];

			cfs[0] = selectRelate4Action(opUserCode, executorService, roleRelateDOList);
			cfs[1] = getUserByUserCode(opUserCode, executorService, userDO);

			if (userProperties.getAdminAccount().equals(opUserCode)) {
				cfs[2] = listAllMenu4Module(busType, executorService, menuModuleDOList);
				cfs[3] = listAllMenu4Page(busType, executorService, menuPageDOList);
				cfs[4] = listAllPage4Component(executorService, menuPageComponentDOList);
				cfs[5] = listAllPage4Parameter(executorService, menuPageParameterDOList);
				cfs[6] = listAllPage4Action(executorService, menuPageActionDOList);
			}
			else {
				cfs[2] = selectMenu4Module(opUserCode, busType, executorService, menuModuleDOList);
				cfs[3] = selectMenu4Page(opUserCode, busType, executorService, menuPageDOList);
				cfs[4] = selectPage4Component(opUserCode, executorService, menuPageComponentDOList);
				cfs[5] = selectPage4Parameter(opUserCode, executorService, menuPageParameterDOList);
				cfs[6] = selectPage4Action(opUserCode, executorService, menuPageActionDOList);
			}
			CompletableFuture.allOf(cfs).join();
		} finally {
			ExecutorServiceUtils.shutdown(executorService);
		}
		String isInnerUser= "";
        try {
			UserDO userByUserCode = userService.getUserByUserCode(opUserCode);
			if (userByUserCode != null) {
				isInnerUser = userByUserCode.getIsInnerUser();
			}
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
		//log.info("menuModuleDOList:{},menuPageDOList:{},menuPageComponentDOList:{},menuPageParameterDOList:{}", menuModuleDOList, menuPageDOList,menuPageComponentDOList,menuPageParameterDOList);
        MethodResponse<List<UserFinancierVO>> listUserFinancier = authService.listUserFinancier(opUserCode);
		map.put("menuTreeList", menuUtils.buildVOTreeList(busType, menuModuleDOList, menuPageDOList, menuPageComponentDOList, menuPageParameterDOList, emptyList, emptyList));
		map.put("actionList", menuPageActionDOList.parallelStream()
				.peek(i -> roleRelateDOList.stream()
						.filter(j -> j.getRCode1().equals(i.getPageId())
								&& j.getRCode2().equals(i.getRId()))
						.max(Comparator.comparing(s -> PageActionPermissionType.getValue(s.getRCode3()).getLevel()))
						.ifPresent(j -> i.setDefPermission(j.getRCode3()))
				)
				.collect(Collectors.toList()));
		UserFinancierVO userFinancierVO = listUserFinancier.getData().stream().filter(i -> i.getSelected()).findFirst().get();
		map.put("userInfo",
				ImmutableMap.<String, Object>builder()
						.put("userCode", userDO.get()==null ? "":userDO.get().getUserOCode())
						.put("userName", userDO.get()==null ? "":userDO.get().getUserOName())
						.put("account", userDO.get()==null ? "":userDO.get().getAccount())
						.put("financierCode", userFinancierVO.getFinancierCode())
						.put("financierName", userFinancierVO.getFinancierName())
						.put("isInnerUser", isInnerUser)
						.build());
		map.put("userTheme", userDO.get()==null ? "":userDO.get().getThemeCfgJson());
		map.put("sysTime", DateUtil8.getNowTime_EN());
		return MethodResponse.success(map);
	}

	private CompletableFuture<UserDO> getUserByUserCode(String opUserCode, ExecutorService executorService, AtomicReference<UserDO> userDO) {
		return CompletableFuture.supplyAsync(() -> userService.getUserByUserCode(opUserCode), executorService)
				.whenComplete((userDOTemp, e) -> {
					userDO.set(userDOTemp);
				});
	}

	private CompletableFuture<List<RoleRelateDO>> selectRelate4Action(String opUserCode, ExecutorService executorService, List<RoleRelateDO> roleRelateDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectRelate4Action(opUserCode, RoleRelateType.USER.getValue(), RoleRelateType.MENU_PAGE$ACTION.getValue()), executorService)
				.whenComplete((roleRelateDOListTemp, e) -> {
					roleRelateDOList.addAll(roleRelateDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageActionDO>> selectPage4Action(String opUserCode, ExecutorService executorService, List<MenuPageActionDO> menuPageActionDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectPage4Action(opUserCode, RoleRelateType.USER.getValue(), RoleRelateType.MENU_PAGE.getValue()), executorService)
				.whenComplete((menuPageActionDOListTemp, e) -> {
					menuPageActionDOList.addAll(menuPageActionDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageParameterDO>> selectPage4Parameter(String opUserCode, ExecutorService executorService, List<MenuPageParameterDO> menuPageParameterDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectPage4Parameter(opUserCode, RoleRelateType.USER.getValue(), RoleRelateType.MENU_PAGE.getValue()), executorService)
				.whenComplete((menuPageParameterDOListTemp, e) -> {
					menuPageParameterDOList.addAll(menuPageParameterDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageComponentDO>> selectPage4Component(String opUserCode, ExecutorService executorService, List<MenuPageComponentDO> menuPageComponentDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectPage4Component(opUserCode, RoleRelateType.USER.getValue(), RoleRelateType.MENU_PAGE.getValue()), executorService)
				.whenComplete((menuPageComponentDOListTemp, e) -> {
					menuPageComponentDOList.addAll(menuPageComponentDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageDO>> selectMenu4Page(String opUserCode, String busType, ExecutorService executorService, List<MenuPageDO> menuPageDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectMenu4Page(opUserCode, busType, RoleRelateType.USER.getValue(), RoleRelateType.MENU_PAGE.getValue(), FlagType.YES.getValue()), executorService)
				.whenComplete((menuPageDOListTemp, e) -> {
					menuPageDOList.addAll(menuPageDOListTemp);
				});
	}

	private CompletableFuture<List<MenuModuleDO>> selectMenu4Module(String opUserCode, String busType, ExecutorService executorService, List<MenuModuleDO> menuModuleDOList) {
		return CompletableFuture.supplyAsync(() -> appMapper.selectMenu4Module(opUserCode, busType, RoleRelateType.USER.getValue(), RoleRelateType.MENU_MODULE.getValue(), FlagType.YES.getValue()), executorService)
				.whenComplete((menuModuleDOListTemp, e) -> {
					menuModuleDOList.addAll(menuModuleDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageActionDO>> listAllPage4Action(ExecutorService executorService, List<MenuPageActionDO> menuPageActionDOList) {
		return CompletableFuture.supplyAsync(() -> menuPageActionService.listAll(), executorService)
				.whenComplete((menuPageActionDOListTemp, e) -> {
					menuPageActionDOList.addAll(menuPageActionDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageParameterDO>> listAllPage4Parameter(ExecutorService executorService, List<MenuPageParameterDO> menuPageParameterDOList) {
		return CompletableFuture.supplyAsync(() -> menuPageParameterService.listAll(), executorService)
				.whenComplete((menuPageParameterDOListTemp, e) -> {
					menuPageParameterDOList.addAll(menuPageParameterDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageComponentDO>> listAllPage4Component(ExecutorService executorService, List<MenuPageComponentDO> menuPageComponentDOList) {
		return CompletableFuture.supplyAsync(() -> menuPageComponentService.listAll(), executorService)
				.whenComplete((menuPageComponentDOListTemp, e) -> {
					menuPageComponentDOList.addAll(menuPageComponentDOListTemp);
				});
	}

	private CompletableFuture<List<MenuPageDO>> listAllMenu4Page(String busType, ExecutorService executorService, List<MenuPageDO> menuPageDOList) {
		return CompletableFuture.supplyAsync(() -> menuPageService.listAllEnabled(), executorService)
				.whenComplete((menuPageDOListTemp, e) -> {
					menuPageDOList.addAll(menuPageDOListTemp);
				});
	}

	private CompletableFuture<List<MenuModuleDO>> listAllMenu4Module(String busType, ExecutorService executorService, List<MenuModuleDO> menuModuleDOList) {
		return CompletableFuture.supplyAsync(() -> menuModuleService.listAllEnabled(), executorService)
				.whenComplete((menuModuleDOListTemp, e) -> {
					menuModuleDOList.addAll(menuModuleDOListTemp);
				});
	}

}

