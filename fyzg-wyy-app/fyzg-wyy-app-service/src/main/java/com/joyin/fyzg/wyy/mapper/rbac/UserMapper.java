package com.joyin.fyzg.wyy.mapper.rbac;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * RBAC_USER表的DAO层类
 *
 * @author 工具生成
 * @version 1.0
 * @since
 */
public interface UserMapper extends SuperMapper<UserDO> {

    List<String> selectOrgsByCodes(List<String> listOrg);

    String getUserDesktop(@Param("filed") String filed, @Param("userCode") String userCode);

    List<String> listUserCodeByRoleCodeList(@Param("roleCodeList") List<String> roleCodeList);

    Map getUserDepartInformation(@Param("loginUserCode") String loginUserCode);

    IPage<UserVO> selectPageInner(Page<UserVO> page, @Param("account") String account, @Param("userOName") String userOName);

    IPage<UserVO> selectPageOut(Page<UserVO> page, @Param("account") String account, @Param("userOName") String userOName, @Param("userOCode") String userOCode);

    List<UserVO> selectUsersRolesByUserCodes(@Param("userCodes") List<String> userCodes);

    IPage<UserVO> selectAllByPage(Page<UserVO> page, @Param("account") String account, @Param("userOName") String userOName, @Param("roleCode") String roleCode);

    IPage<UserDO> selectAllByPageAndRoleName(Page<UserDO> page, @Param("userManager") String userManager, @Param("userOName") String userOName, @Param("roleName") String roleName);

}

