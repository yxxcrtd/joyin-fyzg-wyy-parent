package com.joyin.fyzg.wyy.mapper.rbac;

import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用
 *
 * @author 工具生成
 * @version 1.0
 * @since
 */
public interface AppMapper extends SuperMapper {

	List<MenuModuleDO> selectMenu4Module(@Param("userCode") String userCode, @Param("busType") String busType, @Param("userRType") String userRType, @Param("moduleRType") String moduleRType, @Param("enabled") String enabled);

	List<MenuPageDO> selectMenu4Page(@Param("userCode") String userCode, @Param("busType") String busType, @Param("userRType") String userRType, @Param("pageRType") String pageRType, @Param("enabled") String enabled);

	List<MenuPageComponentDO> selectPage4Component(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("pageRType") String pageRType);

	List<MenuPageParameterDO> selectPage4Parameter(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("pageRType") String pageRType);

	List<MenuPageActionDO> selectPage4Action(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("pageRType") String pageRType);

	List<RoleRelateDO> selectRelate4Action(@Param("userCode") String userCode, @Param("userRType") String userRType, @Param("pageActionRType") String pageRType);

//	List<RoleRelateDO> selectRelate4Common(@Param("rType") String rType, @Param("rCode1") String rCode1, @Param("rCode2") String rCode2, @Param("rCode3") String rCode3, @Param("rCode4") String rCode4, @Param("rCode5") String rCode5);
//
//	List<RoleRelateDO> selectRelate4PageAction(@Param("rType") String rType, @Param("pageId") String pageId, @Param("actCode") String actCode);

}

