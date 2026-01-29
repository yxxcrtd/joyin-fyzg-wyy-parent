package com.joyin.fyzg.wyy.controller.financier;

import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.service.financier.FinancierService;
import com.joyin.fyzg.wyy.vo.financier.FinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("financier")
@Slf4j
public class FinancierController {
	@Autowired
	FinancierService financierService;

	@PostMapping("insertFinancier")
	public RestResponse insertFinancier(@RequestBody FinancierVO financierVO) {
		return RestResponse.transMethodResponse(financierService.insertFinancier(financierVO));
	}

	@PostMapping("updateFinancierById")
	public RestResponse updateFinancierById(@RequestBody FinancierVO financierVO) {
		return RestResponse.transMethodResponse(financierService.updateFinancierById(financierVO));
	}

	@DeleteMapping("deleteFinancierById")
	public RestResponse deleteFinancierById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(financierService.deleteFinancierById(rId));
	}

	@DeleteMapping("deleteFinancierByCustOCode")
	public RestResponse deleteFinancierByCustOCode(@RequestParam("custOCode") String custOCode) {
		return RestResponse.transMethodResponse(financierService.deleteFinancierByCustOCode(custOCode));
	}

	@GetMapping("getFinancierById")
	public RestResponse getFinancierById(@RequestParam("rId") String rId) {
		return RestResponse.success(financierService.getFinancierById(rId));
	}

	@GetMapping("getFinancierByCustOCode")
	public RestResponse getFinancierByCustOCode(@RequestParam("custOCode") String custOCode) {
		return RestResponse.success(financierService.getFinancierByCustOCode(custOCode));
	}

	@PostMapping("listFinancierByCodeList")
	public RestResponse<List<FinancierDO>> listFinancierByCodeList(@RequestBody RequestParamMap params) {
		List<String> codeList = params.getObjectList("codeList", String.class);
		return RestResponse.success(financierService.listFinancierByCodeList(codeList));
	}

	@GetMapping("listAll")
	public RestResponse<List<FinancierDO>> listAll() {
		return RestResponse.success(financierService.listAll());
	}

	@GetMapping("listFinancierByCustOName")
	public RestResponse<List<FinancierDO>> listFinancierByCustOName(@RequestParam("custOName") String custOName) {
		return RestResponse.success(financierService.listFinancierByCustOName(custOName));
	}

	//页面查询
	@GetMapping("listFinanciersByCondition")
	public RestResponse<PageResponse<FinancierDO>> listFinanciersByCondition(@RequestParam(value = "custOName",required = false) String custOName, HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(financierService.listFinanciersByCondition(custOName,pageWrapper));
	}
}
