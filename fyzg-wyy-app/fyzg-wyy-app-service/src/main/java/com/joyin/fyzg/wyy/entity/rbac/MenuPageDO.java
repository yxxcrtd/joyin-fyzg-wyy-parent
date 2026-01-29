package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表RBAC_MENU_PAGE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_RBAC_MENU_PAGE")
public class MenuPageDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
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


    /** IS_SHOW : 是否显示  0不显示 1显示  */
    private String isShow;


    /** EXT_CFG : 扩展配置，json格式  */
    private String extCfg;


    /** SOURCE_TYPE : 子系统标识  */
    private String sourceType;


    /** MICRO_FLAG : 是否为子应用页面，1：是；0：否  */
    private String microFlag;

}
