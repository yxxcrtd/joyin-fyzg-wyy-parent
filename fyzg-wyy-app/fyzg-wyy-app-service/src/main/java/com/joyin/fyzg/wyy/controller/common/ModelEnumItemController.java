package com.joyin.fyzg.wyy.controller.common;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemBO;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemDO;
import com.joyin.fyzg.wyy.service.common.ModelEnumItemService;
import com.joyin.fyzg.wyy.vo.common.ModelEnumGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("modelEnumItem")
@Slf4j
public class ModelEnumItemController {
	@Autowired
	ModelEnumItemService modelEnumItemService;


	@GetMapping("getEnumItemById")
	public RestResponse<ModelEnumItemDO> getEnumItemById(@RequestParam("rId") String rId) {
		return RestResponse.success(modelEnumItemService.getEnumItemById(rId));
	}

	@GetMapping("listEnumItemByCode")
	public RestResponse<List<ModelEnumItemDO>> listEnumItemByCode(@RequestParam("enumCode") String enumCode) {
		List<ModelEnumItemDO> resultList = modelEnumItemService.listEnumItemByCode(enumCode);
		return RestResponse.success(resultList);
	}

	@GetMapping("listAllEnumItem")
	public RestResponse<List<ModelEnumItemBO>> listAllEnumItem(){
		return RestResponse.success(modelEnumItemService.listAllEnumItem());
	}
	@PostMapping("insertEnumItem")
	public RestResponse insertEnumItem(@RequestBody ModelEnumItemDO modelEnumItemDO) {
		return RestResponse.transMethodResponse(modelEnumItemService.insertEnumItem(modelEnumItemDO));
	}

	@PostMapping("updateEnumItemById")
	public RestResponse updateEnumItemById(@RequestBody ModelEnumItemDO modelEnumItemDO) {
		return RestResponse.transMethodResponse(modelEnumItemService.updateEnumItemById(modelEnumItemDO));
	}

	@DeleteMapping("deleteEnumItemById")
	public RestResponse<Object> deleteEnumItemById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(modelEnumItemService.deleteEnumItemById(rId));
	}

	@PostMapping("batchSaveGroupList")
	public RestResponse<Object> batchSaveGroupList(@RequestBody ModelEnumGroupVO modelEnumGroupVO) {
		return RestResponse.transMethodResponse(modelEnumItemService.batchSaveGroupList(modelEnumGroupVO));
	}

}
