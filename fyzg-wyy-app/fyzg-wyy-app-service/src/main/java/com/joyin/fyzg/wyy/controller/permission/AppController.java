package com.joyin.fyzg.wyy.controller.permission;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.service.permission.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("rbacApp")
@Slf4j
public class AppController extends BaseController {
	@Autowired
	AppService appService;

	@GetMapping("mapRbac4App")
	public RestResponse<Map> mapRbac4App(@RequestParam("busType") String busType) {
		return RestResponse.transMethodResponse(appService.mapRbac4App(super.getLoginUserCode(), busType));
	}
}