package com.joyin.fyzg.wyy.controller.financier;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpBO;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpDO;
import com.joyin.fyzg.wyy.service.financier.FinancierMpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("financierMp")
@Slf4j
public class FinancierMpController {
	@Autowired
	FinancierMpService financierMpService;

	@PostMapping("insertFinancierMp")
	public RestResponse insertFinancierMp(@RequestBody FinancierMpDO financierMpDO) {
		return RestResponse.transMethodResponse(financierMpService.insertFinancierMp(financierMpDO));
	}

	@PostMapping("updateFinancierMpById")
	public RestResponse updateFinancierMpById(@RequestBody FinancierMpDO financierMpDO) {
		return RestResponse.transMethodResponse(financierMpService.updateFinancierMpById(financierMpDO));
	}

	@DeleteMapping("deleteFinancierMpById")
	public RestResponse deleteFinancierMpById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(financierMpService.deleteFinancierMpById(rId));
	}

	@GetMapping("getFinancierMpById")
	public RestResponse getFinancierMpById(@RequestParam("rId") String rId) {
		return RestResponse.success(financierMpService.getFinancierMpById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<FinancierMpDO>> listAll() {
		return RestResponse.success(financierMpService.listAll());
	}

	@GetMapping("listFinancierMpByCustOCode")
	public RestResponse listFinancierMpByCustOCode(@RequestParam("custOCode") String custOCode) {
		return RestResponse.success(financierMpService.listFinancierMpByCustOCode(custOCode));
	}

	@GetMapping("listFinancierMpBOByCustOCode")
	public RestResponse<List<FinancierMpBO>> listFinancierMpBOByCustOCode(@RequestParam("custOCode") String custOCode) {
		return RestResponse.success(financierMpService.listFinancierMpBOByCustOCode(custOCode));
	}

}