package com.joyin.fyzg.wyy.controller.rbac;


import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.ProductDO;
import com.joyin.fyzg.wyy.service.rbac.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController extends BaseController {
	@Autowired
	ProductService productService;

	@GetMapping("getDesktopComponentDataById")
	public RestResponse getDesktopComponentDataById(@RequestParam("rId") Long rId) {
		return RestResponse.success(productService.getDesktopComponentDataById(rId));
	}

	@GetMapping("getAnnexSuffixByCode")
	public RestResponse getAnnexSuffixByCode(@RequestParam("code") String code) {
		return RestResponse.transMethodResponse(productService.getAnnexSuffixByCode(code));
	}

	@GetMapping("getMdAnnexSuffixByCode")
	public RestResponse getMdAnnexSuffixByCode(@RequestParam("code") String code) {
		return RestResponse.transMethodResponse(productService.getMdAnnexSuffixByCode(code));
	}

	@GetMapping("getDefaultDesktopCfgUserByCode")
	public RestResponse getDefaultDesktopCfgUserByCode(@RequestParam("code") String code) {
		return RestResponse.transMethodResponse(productService.getDefaultDesktopCfgUserByCode(code));
	}

	@GetMapping("listComponentWithFilter")
	public RestResponse listComponentWithFilter(@RequestParam("type") String type) {
		return RestResponse.success(productService.listComponentWithFilter(super.getLoginUserCode(),type));
	}

	@GetMapping("listAllComponent")
	public RestResponse<List<Map>> listAllComponent() {
		return RestResponse.success(productService.listAllComponent());
	}

	@GetMapping("listAll")
	public RestResponse<List<ProductDO>> listAll() {
		return RestResponse.success(productService.listAll());
	}

	@GetMapping("getProductByCode")
	public RestResponse<ProductDO> getProductByCode(@RequestParam("code") String code) {
		return RestResponse.success(productService.getProductByCode(code));
	}
}
