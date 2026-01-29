package com.joyin.fyzg.wyy.controller.wf;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.entity.wf.WfPhraseDO;
import com.joyin.fyzg.wyy.service.wf.WfPhraseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dfcomposeapi/phrase")
@Slf4j
public class WfPhraseController extends BaseController {
	@Autowired
	WfPhraseService wfPhraseService;

	@PostMapping("insertPhrase")
	public RestResponse insertPhrase(@RequestBody WfPhraseDO wfPhraseDO) {
		wfPhraseDO.setOpUser(super.getLoginUserCode());
		wfPhraseDO.setOpTime(DateUtil8.getNowTime_EN());
		return RestResponse.transMethodResponse(wfPhraseService.insertPhrase(wfPhraseDO));
	}

	@PostMapping("updatePhraseById")
	public RestResponse updatePhraseById(@RequestBody WfPhraseDO wfPhraseDO) {
		return RestResponse.transMethodResponse(wfPhraseService.updatePhraseById(wfPhraseDO));
	}

	@DeleteMapping("deletePhraseById")
	public RestResponse deletePhraseById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(wfPhraseService.deletePhraseById(rId));
	}

	@GetMapping("getPhraseById")
	public RestResponse getPhraseById(@RequestParam("rId") String rId) {
		return RestResponse.success(wfPhraseService.getPhraseById(rId));
	}

	@GetMapping("listPhraseByUser")
	public RestResponse<List<WfPhraseDO>> listPhraseByUser() {
		return RestResponse.success(wfPhraseService.listPhraseByUser(super.getLoginUserCode()));
	}

	@GetMapping("listAll")
	public RestResponse<List<WfPhraseDO>> listAll() {
		return RestResponse.success(wfPhraseService.listAll());
	}
}
