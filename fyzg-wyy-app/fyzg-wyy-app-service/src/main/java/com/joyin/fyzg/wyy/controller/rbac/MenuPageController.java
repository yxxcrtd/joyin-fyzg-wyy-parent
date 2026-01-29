package com.joyin.fyzg.wyy.controller.rbac;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.service.rbac.MenuPageService;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menuPage")
@Slf4j
public class MenuPageController {
	@Autowired
	MenuPageService menuPageService;

	@PostMapping("insertMenuPage")
	public RestResponse insertMenuPage(@RequestBody MenuPageVO menuPageVO) {
		return RestResponse.transMethodResponse(menuPageService.insertMenuPage(menuPageVO));
	}

	@PostMapping("updateMenuPageById")
	public RestResponse updateMenuPageById(@RequestBody MenuPageVO menuPageVO) {
		return RestResponse.transMethodResponse(menuPageService.updateMenuPageById(menuPageVO));
	}

	@DeleteMapping("deleteMenuPageById")
	public RestResponse deleteMenuPageById(@RequestParam("rId") String rId) {
		return RestResponse.transMethodResponse(menuPageService.deleteMenuPageById(rId));
	}

	@GetMapping("getMenuPageById")
	public RestResponse<MenuPageDO> getMenuPageById(@RequestParam("rId") String rId) {
		return RestResponse.success(menuPageService.getMenuPageById(rId));
	}

	@GetMapping("listAll")
	public RestResponse<List<MenuPageDO>> listAll() {
		return RestResponse.success(menuPageService.listAll());
	}

	@PostMapping("updateMenuMovePageById")
	public RestResponse updateMenuMovePageById(@RequestBody MenuPageDO menuPageDO) {
		return RestResponse.transMethodResponse(menuPageService.updateMenuMovePageById(menuPageDO));
	}

	@PostMapping("copyMenuPageById")
	public RestResponse copyMenuPageById(@RequestBody MenuPageDO menuPageDO) {
		return RestResponse.transMethodResponse(menuPageService.copyMenuPageById(menuPageDO));
	}
}
