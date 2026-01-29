package com.joyin.fyzg.wyy.controller.rbac;

import com.google.common.collect.ImmutableList;
import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.common.enums.RoleRelateType;
import com.joyin.fyzg.wyy.entity.rbac.RoleRelateDO;
import com.joyin.fyzg.wyy.service.rbac.RoleRelateService;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("roleRelate")
@Slf4j
public class RoleRelateController extends BaseController {
	@Autowired
	RoleRelateService roleRelateService;

	@PostMapping("insertRoleRelate")
	public RestResponse insertRoleRelate(@RequestBody RoleRelateDO roleRelateDO) {
		return RestResponse.transMethodResponse(roleRelateService.insertRoleRelate(roleRelateDO));
	}

	@GetMapping("listRoleRelateAllMenuByRoleCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllMenuByRoleCode(@RequestParam("roleCode") String roleCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCode, ImmutableList.of(
				RoleRelateType.MENU_MODULE.getValue(),
				RoleRelateType.MENU_PAGE.getValue())));
	}

	@GetMapping("listRoleRelateByPageCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateByPageCode(@RequestParam("pageCode") String pageCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRCode1AndRType(pageCode, ImmutableList.of(
				RoleRelateType.MENU_PAGE.getValue())));
	}

	@GetMapping("listRoleRelateAllUserByRoleCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllUserByRoleCode(@RequestParam("roleCode") String roleCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCode, ImmutableList.of(
				RoleRelateType.USER.getValue())));
	}

	@GetMapping("listRoleRelateAllUserByRoleCodeByPage")
	public RestResponse<PageResponse<UserVO>> listRoleRelateAllUserByRoleCodeByPage(@RequestParam("roleCode") String roleCode, HttpServletRequest httpServletRequest) {
		PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRTypeByPage(roleCode, ImmutableList.of(
				RoleRelateType.USER.getValue()), pageWrapper));
	}

	@GetMapping("listRoleRelateAllWfByRoleCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllWfByRoleCode(@RequestParam("roleCode") String roleCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCode, ImmutableList.of(
				RoleRelateType.WF.getValue())));
	}

	@GetMapping("listRoleRelateAllCaByRoleCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllCaByRoleCode(@RequestParam("roleCode") String roleCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCode, ImmutableList.of(
				RoleRelateType.CA.getValue())));
	}

	@GetMapping("listRoleRelateAllDesktopByRoleCodeExtend")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllDesktopByRoleCodeExtend(@RequestParam("roleCode") String roleCode
			,@RequestParam("type") String type) {
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCode, ImmutableList.of(type)));
	}

	@GetMapping("listRoleRelateAllCaByUserCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllCaByUserCode(@RequestParam("userCode") String userCode) {
		List<String> roleCodeList=roleRelateService.listRoleRelateByRCode1AndRType(userCode, ImmutableList.of(
				RoleRelateType.USER.getValue())).stream().map(i->i.getRoleCode()).collect(Collectors.toList());
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCodeList, ImmutableList.of(
				RoleRelateType.CA.getValue())));
	}

	@GetMapping("listRoleRelateAllDesktopByUserCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateAllDesktopByUserCode(String typeCode) {
		String userCode = this.getLoginUserCode();
		List<String> roleCodeList=roleRelateService.listRoleRelateByRCode1AndRType(userCode, ImmutableList.of(
				RoleRelateType.USER.getValue())).stream().map(i->i.getRoleCode()).collect(Collectors.toList());
		return RestResponse.success(roleRelateService.listRoleRelateByRoleCodeAndRType(roleCodeList, ImmutableList.of(
				typeCode)));
	}

//	@GetMapping("listRoleRelateAllPortalCode")
//	public RestResponse<List<String>> listRoleRelateAllPortalCode() {
//		String userCode = this.getLoginUserCode();
//		return RestResponse.success(roleRelateService.listComponentByUserCodeAndType(userCode, "PORTAL"));
//	}

	@GetMapping("listRoleRelateByUserCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateByUserCode(@RequestParam("userCode") String userCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRCode1AndRType(userCode, ImmutableList.of(
				RoleRelateType.USER.getValue())));
	}

	@GetMapping("listRoleRelateByWfCode")
	public RestResponse<List<RoleRelateDO>> listRoleRelateByWfCode(@RequestParam("wfCode") String wfCode) {
		return RestResponse.success(roleRelateService.listRoleRelateByRCode1AndRType(wfCode, ImmutableList.of(
				RoleRelateType.WF.getValue())));
	}

	@GetMapping("listRoleRelateByWfCodeList")
	public RestResponse<List<RoleRelateDO>> listRoleRelateByWfCodeList(@RequestParam(value = "wfCodeList[]", required = false) List<String> wfCodeList){
		return RestResponse.success(roleRelateService.listRoleRelateByRCode1ListAndRType(wfCodeList, ImmutableList.of(
				RoleRelateType.WF.getValue())));
	}

    @GetMapping("listRoleRelateDataAuToRole")
    public RestResponse<String> listRoleRelateDataAuToRole(@RequestParam("roleCode") String roleCode) {
        return RestResponse.success(roleRelateService.listRoleRelateDataAuToRole(roleCode));
    }

	@PostMapping("saveRoleRelateRoleToMenu4ModuleAndPage")
	public RestResponse<Map<String, Object>> saveRoleRelateRoleToMenu4ModuleAndPage(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		String busCode = params.getStringValueOfNullable("busCode");
		List<String> moduleCodeList = JsonUtils.json2list(params.getStringValueOfNullable("moduleCodeList"), String.class);
		List<String> pageCodeList = JsonUtils.json2list(params.getStringValueOfNullable("pageCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.saveRoleRelateRoleToMenu4ModuleAndPage(roleCode, busCode, moduleCodeList, pageCodeList));
	}

	@PostMapping("saveRoleRelateMenu4PageToRole")
	public RestResponse<Map<String, Object>> saveRoleRelateMenu4PageToRole(@RequestBody RequestParamMap params) {
		String pageCode = params.getStringValueOfNullable("pageCode");
		String roleCode = params.getStringValueOfNullable("roleCode");
		Boolean checked = params.getBooleanValueOfNullable("checked");
		return RestResponse.transMethodResponse(roleRelateService.saveRoleRelateMenu4PageToRole(pageCode, roleCode, checked));
	}

	@PostMapping("saveRoleRelateRoleToUser")
	public RestResponse saveRoleRelateRoleToUser(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		List<String> userCodeList = JsonUtils.json2list(params.getStringValueOfNullable("userCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4RoleToUser(roleCode, userCodeList));
	}

	@PostMapping("saveRoleRelateUserToRole")
	public RestResponse saveRoleRelateUserToRole(@RequestBody RequestParamMap params) {
		String userCode = params.getStringValueOfNullable("userCode");
		List<String> roleCodeList = JsonUtils.json2list(params.getStringValueOfNullable("roleCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4UserToRole(userCode, roleCodeList));
	}

	@PostMapping("saveRoleRelateRoleToWf")
	public RestResponse saveRoleRelateRoleToWf(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		List<String> wfCodeList = JsonUtils.json2list(params.getStringValueOfNullable("wfCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4RoleToWf(roleCode, wfCodeList));
	}

	@PostMapping("saveRoleRelateWfToRole")
	public RestResponse saveRoleRelateWfToRole(@RequestBody RequestParamMap params) {
		String wfCode = params.getStringValueOfNullable("wfCode");
		List<String> roleCodeList = JsonUtils.json2list(params.getStringValueOfNullable("roleCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4WfToRole(wfCode, roleCodeList));
	}

	@PostMapping("saveRoleRelateRoleToCa")
	public RestResponse saveRoleRelateRoleToCa(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		List<Map> docList = JsonUtils.json2list(params.getStringValueOfNullable("docList"), Map.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4RoleToCa(roleCode, docList));
	}

	@PostMapping("saveRoleRelateDesktop4BlockExtend")
	public RestResponse saveRoleRelateDesktop4BlockExtend(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		String type = params.getStringValueOfNullable("type");
		List<String> blockCodeList = JsonUtils.json2list(params.getStringValueOfNullable("blockCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchSaveRoleRelate4RoleToDesktopExtend(roleCode,type, blockCodeList));
	}

	@GetMapping("listComponentByUserCodeAndType")
	public RestResponse<List<String>> listComponentByUserCodeAndType(@RequestParam("userCode") String userCode, @RequestParam("type") String type) {
		return RestResponse.success(roleRelateService.listComponentByUserCodeAndType(userCode,type));
	}

    @PostMapping("saveRoleRelateDataAuToRole")
    public RestResponse saveRoleRelateDataAuToRole(@RequestBody RequestParamMap params) {
        String roleCode = params.getStringValueOfNullable("roleCode");
        String dataAu = params.getStringValueOfNullable("dataAu");
        return RestResponse.transMethodResponse(roleRelateService.saveRoleRelateDataAuToRole(roleCode, dataAu));
    }

	//功能角色权限页面批量添加用户
	@PostMapping("addRoleRelateRoleToUser")
	public RestResponse addRoleRelateRoleToUser(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		List<String> userCodeList = JsonUtils.json2list(params.getStringValueOfNullable("userCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchAddRoleRelate4RoleToUser(roleCode, userCodeList));
	}

	//功能角色权限页面批量删除用户
	@PostMapping("deleteRoleRelateRoleToUser")
	public RestResponse deleteRoleRelateRoleToUser(@RequestBody RequestParamMap params) {
		String roleCode = params.getStringValueOfNullable("roleCode");
		List<String> userCodeList = JsonUtils.json2list(params.getStringValueOfNullable("userCodeList"), String.class);
		return RestResponse.transMethodResponse(roleRelateService.batchDeleteRoleRelate4RoleToUser(roleCode, userCodeList));
	}
}
