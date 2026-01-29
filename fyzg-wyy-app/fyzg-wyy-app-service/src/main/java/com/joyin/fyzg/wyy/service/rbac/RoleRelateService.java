package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.rbac.RoleRelateDO;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;

import java.util.List;
import java.util.Map;

public interface RoleRelateService {

    MethodResponse insertRoleRelate(RoleRelateDO roleRelateDO);

	List<RoleRelateDO> listRoleRelateByRoleCodeAndRType(String roleCode, List<String> rTypeList);

	PageResponse<UserVO> listRoleRelateByRoleCodeAndRTypeByPage(String roleCode, List<String> rTypeList, PageWrapper pageWrapper);

	List<RoleRelateDO> listRoleRelateByRoleCodeAndRType(List<String> roleCodeList, List<String> rTypeList);

	List<RoleRelateDO> listRoleRelateByRCode1AndRType(String rCode1, List<String> rTypeList);

	List<RoleRelateDO> listRoleRelateByRCode1ListAndRType(List<String> rCode1List, List<String> rTypeList);

	List<RoleRelateDO> listRoleRelateByRoleCodeAndRTypeAndPageCode(String roleCode, String pageCode);

	MethodResponse saveRoleRelateRoleToMenu4ModuleAndPage(String roleCode, String busCode, List<String> moduleCodeList, List<String> pageCodeList);

	String listRoleRelateDataAuToRole(String roleCode);

	MethodResponse saveRoleRelateMenu4PageToRole(String pageCode, String roleCode, Boolean checked);

	MethodResponse batchSaveRoleRelate4Page(List<RoleRelateDO> roleRelateDOList, String roleCode, String pageCode);

	MethodResponse batchSaveRoleRelate4RoleToUser(String roleCode, List<String> userCodeList);

	MethodResponse batchSaveRoleRelate4UserToRole(String userCode, List<String> roleCodeList);

	MethodResponse batchSaveRoleRelate4RoleToWf(String roleCode, List<String> wfCodeList);

	MethodResponse batchSaveRoleRelate4RoleToCa(String roleCode, List<Map> docList);

	MethodResponse batchSaveRoleRelate4RoleToDesktopExtend(String roleCode, String type, List<String> blockCodeList);

	MethodResponse batchSaveRoleRelate4WfToRole(String wfCode, List<String> roleCodeList);

    List<String> listComponentByUserCodeAndType(String userCode, String type);

    MethodResponse saveRoleRelateDataAuToRole(String roleCode, String dataAu);

	MethodResponse batchAddRoleRelate4RoleToUser(String roleCode, List<String> userCodeList);

	MethodResponse batchDeleteRoleRelate4RoleToUser(String roleCode, List<String> userCodeList);

}
