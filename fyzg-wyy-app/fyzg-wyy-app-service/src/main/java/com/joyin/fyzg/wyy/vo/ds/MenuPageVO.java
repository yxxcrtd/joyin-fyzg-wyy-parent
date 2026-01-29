package com.joyin.fyzg.wyy.vo.ds;

import com.google.common.collect.Lists;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageActionVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageComponentVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageParameterVO;
import com.joyin.fyzg.wyy.vo.rbac.MenuPageRequestVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 表RBAC_MENU_PAGE的VO类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@Builder
public class MenuPageVO {

    /** R_ID : 数据编号，自增长主键 */
    private String rId;


    /** PAGE_NAME : 页面名称 */
    private String pageName;

    /** MDL_ID : 模块ID */
    private String mdlId;


    /** BUS_TYPE : 业务线类型(business) */
    private String busType;


    /** ENABLED : 菜单状态：0禁用；1:启用 */
    private String enabled;


    /** RT_URL : 路由地址(router) */
    private String rtUrl;


    /** COMT_URL : 组件地址(component) */
    private String comtUrl;


    /** SORT : 排序 */
    private Long sort;


    /** REMARK : 备注 */
    private String remark;


    /** ICON : 图标 */
    private String icon;


    private List<MenuPageComponentVO> componentList = Lists.newArrayList();

    private List<MenuPageParameterVO> parameterList = Lists.newArrayList();

    private List<MenuPageActionVO> actionList = Lists.newArrayList();

    private List<MenuPageRequestVO> requestList = Lists.newArrayList();


    /** isShow 是否显示  0不显示 1显示  */
    private String isShow;


    /** EXT_CFG : 扩展配置，json格式  */
    private String extCfg;


    /** MICRO_FLAG : 是否为微应用页面，1：是；0：否 */
    private String microFlag;

}
