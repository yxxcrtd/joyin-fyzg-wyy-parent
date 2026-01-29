package com.joyin.fyzg.wyy.controller.common;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.QueryResultMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.service.common.AppCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("commonApp")
@Slf4j
public class CommonAppController extends BaseController {

	@Autowired
	AppCommonService appCommonService;

	@GetMapping("listAutocompleteUserData")
	public RestResponse<List<QueryResultMap>> listAutocompleteUserData(@RequestParam("queryString") String queryString) {
		return RestResponse.success(appCommonService.listAutocompleteUserData(queryString));
	}

}
