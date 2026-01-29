package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表RBAC_MENU_MODULE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_RBAC_MENU_MODULE")
public class MenuModuleDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** MDL_NAME : 模块名称 */
    private String mdlName;


    /** P_MDL_ID : 父模块ID */
    private String pMdlId;
    
    
    /** BUS_TYPE : 业务线类型(business) */
    private String busType;
    
    
    /** ICON : 图标 */
    private String icon;
    
    
    /** ENABLED : 菜单状态：0禁用；1:启用 */
    private String enabled;
    
    
    /** SORT : 排序 */
    private Long sort;
    
    
    /** REMARK : 备注 */
    private String remark;


    /** EXT_CFG : 扩展配置，json格式  */
    private String extCfg;


    /** IS_COMBINE : 是否合并菜单 */
    private String isCombine;
    
	
}
