package com.joyin.fyzg.wyy.controller.microApplication;

import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.microApplication.MicroApplicationDO;
import com.joyin.fyzg.wyy.service.microApplication.MicroApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("microApplication")
@Slf4j
public class MicroApplicationController {
	@Autowired
	MicroApplicationService microApplicationService;

	@PostMapping("insertMicroApplication")
	public RestResponse insertMicroApplication(@RequestBody MicroApplicationDO microApplicationDO) {
		return RestResponse.transMethodResponse(microApplicationService.insertMicroApplication(microApplicationDO));
	}

	@PostMapping("updateMicroApplicationById")
	public RestResponse updateMicroApplicationById(@RequestBody MicroApplicationDO microApplicationDO) {
		return RestResponse.transMethodResponse(microApplicationService.updateMicroApplicationById(microApplicationDO));
	}

	@DeleteMapping("deleteMicroApplicationById")
	public RestResponse deleteMicroApplicationById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(microApplicationService.deleteMicroApplicationById(rId));
	}

	@GetMapping("getMicroApplicationById")
	public RestResponse getMicroApplicationById(@RequestParam("rId") String rId) {
		return RestResponse.success(microApplicationService.getMicroApplicationById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<MicroApplicationDO>> listAll() {
		return RestResponse.success(microApplicationService.listAll());
	}

	/***
	 * 获取文件规则的数据
	 * <br/>
	 * @param params
	 * @return com.joyin.fyzg.common.RestResponse<java.util.List < java.util.Map < java.lang.String, java.lang.String>>>
	 * @author jinyuan.lin
	 * @date 2024/1/23 18:37
	 */
	@PostMapping("listMicroApplication")
	public RestResponse<List<MicroApplicationDO>> listMicroApplication(@RequestBody RequestParamMap params) {
		return RestResponse.success(microApplicationService.listMicroApplication(params));
	}
}
