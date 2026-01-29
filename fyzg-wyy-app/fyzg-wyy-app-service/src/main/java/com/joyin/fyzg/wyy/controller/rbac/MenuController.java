package com.joyin.fyzg.wyy.controller.rbac;

import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageActionDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageRequestDO;
import com.joyin.fyzg.wyy.service.rbac.MenuService;
import com.joyin.fyzg.wyy.vo.rbac.ContainerVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuVO;
import com.joyin.fyzg.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
@Slf4j
public class MenuController {
	@Autowired
	MenuService menuService;

	@GetMapping("getTypeList")
	public RestResponse<List<Map<String, Object>>> getTypeList() {
		return RestResponse.success(menuService.getTypeList());
	}

	@GetMapping("listAllTree")
	public RestResponse<List<ContainerVO>> listAllTree() {
		return RestResponse.success(menuService.listAllTree());
	}

	@GetMapping("listAllTree4Publish")
	public RestResponse<List<ContainerVO>> listAllTree4Publish() {
		return RestResponse.success(menuService.listAllTree4Publish());
	}

	@GetMapping("listTree")
	public RestResponse<List<MenuVO>> listTree(@RequestParam("busType") String busType,@RequestParam("menuName") String menuName, @RequestParam("enabled") String enabled) {
		return RestResponse.success(menuService.listTree(busType, menuName, enabled));
	}

	@GetMapping("listAllTree4RoleRelate")
	public RestResponse<List<MenuVO>> listAllTree4RoleRelate() {
		return RestResponse.success(menuService.listAllTree4RoleRelate());
	}

	@GetMapping("mapActionAndRequestAndRoleRelate")
	public RestResponse mapActionAndRequestAndRoleRelate(@RequestParam("roleCode") String roleCode, @RequestParam("pageCode") String pageCode) {
		return RestResponse.success(menuService.mapActionAndRequestAndRoleRelate(roleCode, pageCode));
	}

	@PostMapping("saveRoleRelatePage4ActionAndRequest")
	public RestResponse<Map<String, Object>> saveRoleRelatePage4ActionAndRequest(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		String pageCode = params.getStringValueOfNullable("pageCode");
		List<MenuPageActionDO> menuPageActionDOList = JsonUtils.json2list(params.getStringValueOfNullable("actionTableData"), MenuPageActionDO.class);
		List<MenuPageRequestDO> menuPageRequestDOList = JsonUtils.json2list(params.getStringValueOfNullable("requestTableData"), MenuPageRequestDO.class);
		return RestResponse.transMethodResponse(menuService.saveRoleRelatePage4ActionAndRequest(roleCode, pageCode, menuPageActionDOList, menuPageRequestDOList));
	}
}