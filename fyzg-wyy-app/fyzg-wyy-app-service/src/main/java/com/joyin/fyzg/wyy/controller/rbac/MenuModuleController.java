package com.joyin.fyzg.wyy.controller.rbac;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuModuleDO;
import com.joyin.fyzg.wyy.service.rbac.MenuModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menuModule")
@Slf4j
public class MenuModuleController {
	@Autowired
	MenuModuleService menuModuleService;

	@PostMapping("insertMenuModule")
	public RestResponse insertMenuModule(@RequestBody MenuModuleDO menuModuleDO) {
		return RestResponse.transMethodResponse(menuModuleService.insertMenuModule(menuModuleDO));
	}

	@PostMapping("updateMenuModuleById")
	public RestResponse updateMenuModuleById(@RequestBody MenuModuleDO menuModuleDO) {
		return RestResponse.transMethodResponse(menuModuleService.updateMenuModuleById(menuModuleDO));
	}

	@DeleteMapping("deleteMenuModuleById")
	public RestResponse deleteMenuModuleById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(menuModuleService.deleteMenuModuleById(rId));
	}

	@GetMapping("getMenuModuleById")
	public RestResponse getMenuModuleById(@RequestParam("rId") String rId) {
		return RestResponse.success(menuModuleService.getMenuModuleById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<MenuModuleDO>> listAll() {
		return RestResponse.success(menuModuleService.listAll());
	}
}
