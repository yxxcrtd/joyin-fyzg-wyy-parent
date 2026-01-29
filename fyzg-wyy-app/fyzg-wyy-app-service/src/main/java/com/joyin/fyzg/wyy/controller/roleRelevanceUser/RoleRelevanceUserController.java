package com.joyin.fyzg.wyy.controller.roleRelevanceUser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.roleRelevanceUser.RoleRelevanceUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roleRelevanceUser")
@Slf4j
public class RoleRelevanceUserController {
	@Autowired
	RoleRelevanceUserService roleRelevanceUserService;

	@GetMapping("listUserByRoleCodeAndUserName")
	public RestResponse<IPage<UserDO>> listUserByRoleCodeAndUserName(@RequestParam(value = "roleCode", required = true) String roleCode, @RequestParam(value = "userName", required = false) String userName, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
		return RestResponse.success(roleRelevanceUserService.listUserByRoleCodeAndUserName(roleCode, userName, page, pageSize));
	}

	@PostMapping("insertRoleRelevanceUser")
	public RestResponse insertRoleRelevanceUser(@RequestBody RequestParamMap params) {
		return RestResponse.transMethodResponse(roleRelevanceUserService.insertRoleRelevanceUser(params.getStringValueOf("roleCode"), params.getStringValueOf("userCode")));
	}

	@PostMapping("deleteRoleRelevanceUser")
	public RestResponse deleteRoleRelevanceUser(@RequestBody RequestParamMap params) {
		return RestResponse.success(roleRelevanceUserService.deleteRoleRelevanceUser(params.getStringValueOf("roleCode"), params.getStringValueOf("userCode")));
	}

}
