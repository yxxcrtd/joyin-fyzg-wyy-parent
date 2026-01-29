package com.joyin.fyzg.wyy.controller.ds;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.client.DynamicDatasourceClientService;
import com.joyin.fyzg.wyy.entity.ds.DynamicDatasourceDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("dfcomposeapi/dynamicDatasource")
@Slf4j
public class DynamicDatasourceController extends BaseController {
	@Autowired
	DynamicDatasourceClientService dynamicDatasourceClientService;

	@PostMapping("insertDynamicDatasource")
	public RestResponse insertDynamicDatasource(@RequestBody DynamicDatasourceDO dynamicDatasourceDO) {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.insertDynamicDatasource(dynamicDatasourceDO));
	}

	@PostMapping("updateDynamicDatasourceById")
	public RestResponse updateDynamicDatasourceById(@RequestBody DynamicDatasourceDO dynamicDatasourceDO) {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.updateDynamicDatasourceById(dynamicDatasourceDO));
	}

	@DeleteMapping("deleteDynamicDatasourceById")
	public RestResponse deleteDynamicDatasourceById(@RequestParam("rId") String rId) {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.deleteDynamicDatasourceById(rId));
	}

	@GetMapping("getDynamicDatasourceById")
	public RestResponse getDynamicDatasourceById(@RequestParam("rId") String rId) {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.getDynamicDatasourceById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<DynamicDatasourceDO>> listAll() {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.listAll());
	}

	@GetMapping("listDynamicDatasourceByType")
	public RestResponse<IPage<DynamicDatasourceDO>> listDynamicDatasourceByType(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") String type) {
		return RestResponse.transFeignResponse(dynamicDatasourceClientService.listDynamicDatasourceByType(page,pageSize,type));
	}

}
