package com.joyin.fyzg.wyy.controller.rbac;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.rbac.RoleDO;
import com.joyin.fyzg.wyy.service.rbac.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
@Slf4j
public class RoleController {
	@Autowired
	RoleService roleService;

	@PostMapping("insertRole")
	public RestResponse insertRole(@RequestBody RoleDO roleDO) {
		return RestResponse.transMethodResponse(roleService.insertRole(roleDO));
	}

	@PostMapping("updateRoleById")
	public RestResponse updateRoleById(@RequestBody RoleDO roleDO) {
		return RestResponse.transMethodResponse(roleService.updateRoleById(roleDO));
	}

	@DeleteMapping("deleteRoleById")
	public RestResponse deleteRoleById(@RequestParam("rId") Long rId) {
		return RestResponse.transMethodResponse(roleService.deleteRoleById(rId));
	}

	@GetMapping("getRoleById")
	public RestResponse getRoleById(@RequestParam("rId") Long rId) {
		return RestResponse.success(roleService.getRoleById(rId));
	}

	@GetMapping("listRoleByCodeList")
	public RestResponse<List<RoleDO>> listRoleByCodeList(@RequestParam(value = "codeList[]", required = false) List<String> codeList) {
		return RestResponse.success(roleService.listRoleByCodeList(codeList));
	}

	@GetMapping("listAll")
	public RestResponse<List<RoleDO>> listAll() {
		return RestResponse.success(roleService.listAll());
	}

	//页面查询
	@GetMapping("listRolesByCondition")
	public RestResponse<Map<String, Object>> listRolesByCondition(@RequestParam(value = "roleOCode",required = false) String roleOCode, @RequestParam(value = "roleOName",required = false) String roleOName, HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(roleService.listRolesByCondition(roleOCode,roleOName,pageWrapper));
	}

	//产品岗位权限对象型格式刷
	@GetMapping("getJobPermissions")
	public RestResponse<List<Map>> getJobPermissions() {
		return RestResponse.success(roleService.getJobPermissions());
	}

	//数据权限枚举
	@GetMapping("getDataPermissions")
	public RestResponse<List<Map>> getDataPermissions() {
		return RestResponse.success(roleService.getDataPermissions());
	}
}
