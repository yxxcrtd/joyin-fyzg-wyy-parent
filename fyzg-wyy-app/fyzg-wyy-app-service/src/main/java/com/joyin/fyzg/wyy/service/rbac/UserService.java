package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {
	MethodResponse insertUser(UserDO userDO);
	MethodResponse updateUserById(UserDO userDO);
	MethodResponse deleteUserById(Long rId);

	MethodResponse setUserCollect(UserDO userDO);

	MethodResponse setUserTheme(UserDO userDO);

	MethodResponse setUserDesktop(RequestParamMap paramMap);

	String getUserDesktop(String userCode, String productLine);

	UserDO getUserById(Long rId);

	UserDO getFirstUserByAccount(String account);

	UserDO getUserByUserCode(String userCode);

	String getUserCollectMenuByCode(String code);

	List<UserDO> listUserByCodeList(List<String> codeList);

	List<String> listUserCodeByRoleCodeList(List<String> codeList);

	List<UserDO> listAll();

	PageResponse<UserVO> listAllByPage(String userOName, String account, String roleCode, PageWrapper pageWrapper);

	Map<String, Object> listByConditions(String userManager,String userOName,String roleName, PageWrapper pageWrapper);

	List<UserDO> listUserByAccount(String account);

	List<String> listUserOrg(String loginUserCode);

    Map getCurrentUserSession(String loginUserCode);

	List<Map<String, Object>> getCurrentUserSessionKeyList();

	MethodResponse updateUserLoginTime(String userCode);

	List<UserDO> getAllEffUser();
	PageResponse<UserVO> listUsersByCondition(String account, String userOName, PageWrapper pageWrapper, String currentUserAcc);

	List<UserDO> listUsersByFinancier(String financier);

    Map<String,Object> getUserDepartInformation(String loginUserCode);

    Map getUserMapping();

	List<Map> getDepts();

}
