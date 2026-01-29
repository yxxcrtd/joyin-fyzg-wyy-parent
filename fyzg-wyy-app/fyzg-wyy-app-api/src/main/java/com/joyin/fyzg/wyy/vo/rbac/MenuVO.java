package com.joyin.fyzg.wyy.vo.rbac;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuVO {

	private String key;

	/**
	 * 页面名称
	 */
	private String menuName;

	/**
	 * 菜单类型：MODULE，PAGE
	 */
	private String menuType;

	private List<MenuVO> children;

	/**
	 * =================共用属性 begin=================
	 */

	private String rId;

	/**
	 * ENABLED : 菜单状态：0禁用；1:启用
	 */
	private String enabled;

	/**
	 * BUS_TYPE : 业务线类型(business)
	 */
	private String busType;

	/**
	 * SORT : 排序
	 */
	private Long sort;

	/**
	 * REMARK : 备注
	 */
	private String remark;


	/** EXT_CFG : 扩展配置，json格式  */
	private String extCfg;

	/** =================共用属性 end================= */

	/** =================模块属性 begin================= */
	/**
	 * MDL_NAME : 模块名称
	 */
	private String mdlName;

	/**
	 * P_MDL_ID : 父模块ID
	 */
	private String pMdlId;

	/**
	 * ICON : 图标
	 */
	private String icon;

	/** =================模块属性 end================= */

	/** =================页面属性 begin================= */
	/**
	 * PAGE_NAME : 页面名称
	 */
	private String pageName;

	/**
	 * MDL_ID : 模块ID
	 */
	private String mdlId;

	/**
	 * RT_URL : 路由地址(router)
	 */
	private String rtUrl;

	/**
	 * COMT_URL : 组件地址(component)
	 */
	private String comtUrl;


	/** SOURCE_TYPE : 子系统标识  */
	private String sourceType;


	/** MICRO_FLAG : 是否为子应用页面，1：是；0：否  */
	private String microFlag;

	private List<MenuPageComponentVO> componentList = Lists.newArrayList();

	private List<MenuPageParameterVO> parameterList = Lists.newArrayList();

	private List<MenuPageActionVO> actionList = Lists.newArrayList();

	private List<MenuPageRequestVO> requestList = Lists.newArrayList();
	/** =================页面属性 end================= */

	/** isShow 是否显示  0不显示 1显示  */
	private String isShow;

	/** IS_COMBINE : 是否合并菜单 */
	private String isCombine;

}
