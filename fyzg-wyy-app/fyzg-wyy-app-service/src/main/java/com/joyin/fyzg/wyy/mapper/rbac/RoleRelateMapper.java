package com.joyin.fyzg.wyy.mapper.rbac;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.RoleRelateDO;
import com.joyin.fyzg.wyy.vo.rbac.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * RBAC_ROLE_RELATE表的DAO层类
 *
 * @author 工具生成
 * @version 1.0
 * @since
 */
public interface RoleRelateMapper extends SuperMapper<RoleRelateDO> {
	/*********************菜单相关的*********************/

	//不更换
	int deleteRoleRelateRoleToMenu4ModuleAndPage(@Param("roleCode") String roleCode, @Param("busCode") String busCode, @Param("moduleRType") String moduleRType, @Param("moduleCodeList") List<String> moduleCodeList, @Param("pageRType") String pageRType, @Param("pageCodeList") List<String> pageCodeList);
    //更换
	int insertRoleRelateRoleToMenu4ModuleAndPage(@Param("roleCode") String roleCode, @Param("rCode1MapList") List<Map> rCode1MapList);
    //不更换
	int deleteRoleRelateRoleToMenu4PageActionAndRequest(@Param("roleCode") String roleCode, @Param("pageRType") String pageRType, @Param("pageActionRType") String pageActionRType, @Param("pageRequestRType") String pageRequestRType);

	int insertRoleRelateMenu4PageToRole(@Param("roleCode") String roleCode, @Param("pageCode") String pageCode, @Param("moduleRType") String moduleRType, @Param("pageRType") String pageRType);

	/*********************用户相关的*********************/
//不更换
	int deleteRoleRelate4RoleToUser(@Param("roleCode") String roleCode, @Param("userRType") String userRType, @Param("userCodeList") List<String> userCodeList);
   //更换
	int insertRoleRelate4RoleToUser(@Param("roleCode") String roleCode, @Param("userRType") String userRType, @Param("userCodeList") List<String> userCodeList);

	int deleteRoleRelate4UserToRole(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("roleCodeList") List<String> roleCodeList);
//更换
	int insertRoleRelate4UserToRole(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("roleCodeList") List<String> roleCodeList);

	/*********************流程相关的*********************/

	int deleteRoleRelate4RoleToWf(@Param("roleCode") String roleCode, @Param("wfRType") String wfRType, @Param("wfCodeList") List<String> wfCodeList);
    //更换
	int insertRoleRelate4RoleToWf(@Param("roleCode") String roleCode, @Param("wfRType") String wfRType, @Param("wfCodeList") List<String> wfCodeList);

	int deleteRoleRelate4WfToRole(@Param("wfCode") String wfCode, @Param("wfRType") String wfRType, @Param("roleCodeList") List<String> roleCodeList);

	int insertRoleRelate4WfToRole(@Param("wfCode") String wfCode, @Param("wfRType") String wfRType, @Param("roleCodeList") List<String> roleCodeList);

	/*********************文档相关的*********************/

	int deleteRoleRelate4RoleToCa(@Param("roleCode") String roleCode, @Param("wfRType") String wfRType, @Param("docList") List<Map> docList);

	int insertRoleRelate4RoleToCa(@Param("roleCode") String roleCode, @Param("wfRType") String wfRType, @Param("docList") List<Map> docList);

	/*********************桌面相关的*********************/

	int deleteRoleRelate4RoleToDesktop(@Param("roleCode") String roleCode, @Param("desktopRType") String desktopRType, @Param("blockCodeList") List<String> blockCodeList);

	int insertRoleRelate4RoleToDesktop(@Param("roleCode") String roleCode, @Param("desktopRType") String desktopRType, @Param("blockCodeList") List<String> blockCodeList);

	List<String> listComponentByUserCodeAndType(@Param("userCode") String userCode, @Param("type") String type);

    /*********************数据权限相关的*********************/

    String listRoleRelateDataAuToRole(@Param("roleCode") String roleCode);

    void updateRoleRelateDataAuToRole(@Param("roleCode") String roleCode, @Param("dataAu") String dataAu);
	//不更换
	List<String> listTasksByTemplateId(@Param("templateIds") List<String> templateIds);
	//不更换
	int deleteRoleRelate4RoleToWfByMenu(@Param("roleCode") String roleCode, @Param("wfRType") String wfRType, @Param("wfCodeList") List<String> wfCodeList);

	IPage<UserVO> listRoleRelateByRoleCodeAndRTypeByPage(Page<UserVO> page,  @Param("roleCode") String roleCode);

}


