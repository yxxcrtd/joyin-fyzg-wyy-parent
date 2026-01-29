package com.joyin.fyzg.wyy.controller.rbac;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.ComponentDO;
import com.joyin.fyzg.wyy.service.rbac.ComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("component")
@Slf4j
public class ComponentController {
	@Autowired
	ComponentService componentService;

	@PostMapping("saveComponent")
	public RestResponse saveComponent(@RequestBody ComponentDO componentDO) {
		return RestResponse.transMethodResponse(componentService.saveComponent(componentDO));
	}

	@PostMapping("insertComponent")
	public RestResponse insertComponent(@RequestBody ComponentDO componentDO) {
		return RestResponse.transMethodResponse(componentService.insertComponent(componentDO));
	}

	@PostMapping("updateComponentById")
	public RestResponse updateComponentById(@RequestBody ComponentDO componentDO) {
		return RestResponse.transMethodResponse(componentService.updateComponentById(componentDO));
	}

	@DeleteMapping("deleteComponentById")
	public RestResponse deleteComponentById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(componentService.deleteComponentById(rId));
	}

	@DeleteMapping("deleteComponentByCodeAndPattern")
	public RestResponse deleteComponentByCodeAndPattern(@RequestParam("code") String code, @RequestParam("pattern") String pattern) {
		return RestResponse.transMethodResponse(componentService.deleteComponentByCodeAndPattern(code, pattern));
	}

	@GetMapping("getComponentById")
	public RestResponse getComponentById(@RequestParam("rId") String rId) {
		return RestResponse.success(componentService.getComponentById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<ComponentDO>> listAll() {
		return RestResponse.success(componentService.listAll());
	}
}
