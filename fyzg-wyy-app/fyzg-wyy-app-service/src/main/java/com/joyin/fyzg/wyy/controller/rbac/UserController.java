package com.joyin.fyzg.wyy.controller.rbac;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {
	@Autowired
	UserService userService;

	@PostMapping("insertUser")
	public RestResponse insertUser(@RequestBody UserDO userDO) {
		return RestResponse.transMethodResponse(userService.insertUser(userDO));
	}
	@PostMapping("updateUserById")
	public RestResponse updateUserById(@RequestBody UserDO userDO) {
		return RestResponse.transMethodResponse(userService.updateUserById(userDO));
	}

	@DeleteMapping("deleteUserById")
	public RestResponse deleteUserById(@RequestParam("rId") Long rId) {
		return RestResponse.transMethodResponse(userService.deleteUserById(rId));
	}

	@PostMapping("setUserCollect")
	public RestResponse setUserCollect(@RequestBody UserDO userDO) {
		userDO.setUserOCode(super.getLoginUserCode());
		return RestResponse.transMethodResponse(userService.setUserCollect(userDO));
	}

	@PostMapping("setUserTheme")
	public RestResponse setUserTheme(@RequestBody UserDO userDO) {
		userDO.setUserOCode(super.getLoginUserCode());
		return RestResponse.transMethodResponse(userService.setUserTheme(userDO));
	}

	@PostMapping("setUserDesktop")
	public RestResponse setUserDesktop(@RequestBody RequestParamMap paramMap) {
		paramMap.put("userCode",super.getLoginUserCode());
		return RestResponse.transMethodResponse(userService.setUserDesktop(paramMap));
	}

	@GetMapping("getUserDesktop")
	public RestResponse getUserDesktop(@RequestParam("productLine") String productLine) {
		return RestResponse.success(userService.getUserDesktop(super.getLoginUserCode(),productLine));
	}
	@GetMapping("getUserDesktopByUserCode")
	public RestResponse getUserDesktopByUserCode(@RequestParam("productLine") String productLine,@RequestParam("userCode") String userCode) {
		return RestResponse.success(userService.getUserDesktop(userCode,productLine));
	}


	@GetMapping("updateUserLoginTime")
	public RestResponse updateUserLoginTime(@RequestParam("userCode") String userCode) {
		return RestResponse.transMethodResponse(userService.updateUserLoginTime(userCode));
	}

	@GetMapping("getUserById")
	public RestResponse getUserById(@RequestParam("rId") Long rId) {
		return RestResponse.success(userService.getUserById(rId));
	}

	@GetMapping("getUserByAccount")
	public RestResponse getUserByAccount(@RequestParam("account") String account) {
		return RestResponse.success(userService.getFirstUserByAccount(account));
	}

	@GetMapping("getUserByUserCode")
	public RestResponse getUserByUserCode(@RequestParam("userOCode") String userOCode) {
		return RestResponse.success(userService.getUserByUserCode(userOCode));
	}

	@GetMapping("getUserCollectCfgJsonByCode")
	public RestResponse getUserCollectMenuByCode() {
		return RestResponse.success(userService.getUserCollectMenuByCode(super.getLoginUserCode()));
	}

	@PostMapping("listUserByCodeList")
	public RestResponse<List<UserDO>> listUserByCodeList(@RequestBody RequestParamMap params) {
		List<String> codeList = params.getObjectList("codeList", String.class);
		return RestResponse.success(userService.listUserByCodeList(codeList));
	}

	@PostMapping("listUserCodeByRoleCodeList")
	public RestResponse<List<String>> listUserCodeByRoleCodeList(@RequestBody RequestParamMap params) {
		List<String> codeList = params.getObjectList("roleCodeList", String.class);
		return RestResponse.success(userService.listUserCodeByRoleCodeList(codeList));
	}

	@GetMapping("listAll")
	public RestResponse<List<UserDO>> listAll() {
		return RestResponse.success(userService.listAll());
	}

	@GetMapping("listAllByPage")
	public RestResponse<PageResponse<UserVO>> listAllByPage(@RequestParam(value = "userOName", required = false) String userOName,
															@RequestParam(value = "account", required = false) String account,
															@RequestParam(value = "roleCode") String roleCode,
															HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(userService.listAllByPage(userOName, account, roleCode, pageWrapper));
	}


	@GetMapping("listByConditions")
	public RestResponse<Map<String, Object>> listByConditions(@RequestParam(value = "userManager",required = false) String userManager, @RequestParam(value = "userOName",required = false) String userOName,@RequestParam(value = "roleName",required = false) String roleName,HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(userService.listByConditions(userManager,userOName,roleName,pageWrapper));
	}

	@GetMapping("listUserOrg")
	public RestResponse<List<String>> listUserOrg() {
		String loginUserCode = super.getLoginUserCode();
		return RestResponse.success(userService.listUserOrg(loginUserCode));
	}

//	@GetMapping("getPwdValidDate")
//	public RestResponse<String> getPwdValidDate() {
//		return RestResponse.success(userService.getPwdValidDate(null));
//	}

	/**
	 * 根据用户CODE查询部门信息
	 * @param userCode
	 * @return
	 */
	@GetMapping("listUserOrByUserCode")
	public RestResponse<List<String>> listUserOrByUserCode(@RequestParam("userCode") String userCode) {

		return RestResponse.success(userService.listUserOrg(userCode));
	}

	/**
	 * 获取用户的信息
	 * @return
	 */
	@GetMapping("getUserDepartInformation")
	public RestResponse<Map<String,Object>> getUserDepartInformation() {
		String loginUserCode = super.getLoginUserCode();
		return RestResponse.success(userService.getUserDepartInformation(loginUserCode));
	}

	@GetMapping("getCurrentUserSession")
	public RestResponse<Map> getCurrentUserSession(@RequestParam("loginUserCode") String loginUserCode) {
		return RestResponse.success(userService.getCurrentUserSession(loginUserCode));
	}

	@GetMapping("getCurrentUserSessionKeyList")
	public RestResponse<List<Map<String, Object>>> getCurrentUserSessionKeyList() {
		return RestResponse.success(userService.getCurrentUserSessionKeyList());
	}


    @GetMapping("getAllEffUser")
    public RestResponse<List<UserDO>> getAllEffUser() {
        return RestResponse.success(userService.getAllEffUser());
    }


    @GetMapping("userMap")
    public RestResponse<Map> getUserMapping() {
        return RestResponse.success(userService.getUserMapping());
    }

//	@GetMapping("updateUserErrorNum")
//	public RestResponse<String> updateUserErrorNum(@RequestParam("account") String account) {
//
//		return RestResponse.success(userService.updateUserErrorNum(account));
//	}

	//页面查询
	@GetMapping("listUsersByCondition")
	public RestResponse<PageResponse<UserVO>> listUsersByCondition(
			@RequestParam(value = "userOCode",required = false) String userOCode,
			@RequestParam(value = "account",required = false) String account,
			@RequestParam(value = "userOName",required = false) String userOName, HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(userService.listUsersByCondition(account, userOName, pageWrapper, userOCode));
	}


	@GetMapping("listUsersByFinancier")
	public RestResponse<List<UserDO>> listUsersByFinancier(
			@RequestParam(value = "financier",required = false) String financier) {
		return RestResponse.success(userService.listUsersByFinancier(financier));
	}

	//用户所属部门格式刷
	@GetMapping("getDepts")
	public RestResponse<List<Map>> getDepts() {
		return RestResponse.success(userService.getDepts());
	}

}
