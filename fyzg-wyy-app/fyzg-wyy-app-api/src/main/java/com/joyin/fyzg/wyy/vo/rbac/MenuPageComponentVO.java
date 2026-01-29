package com.joyin.fyzg.wyy.vo.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表RBAC_MENU_PAGE_COMPONENT的VO类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPageComponentVO {
    
    /** R_ID : 数据编号，自增长主键 */
    private String rId;
    
    
    /** PAGE_ID : 菜单页面ID */
    private String pageId;
    
    
    /** RT_NAME : 路由名称(router) */
    private String rtName;
    
    
    /** RT_URL : 路由地址(router) */
    private String rtUrl;
    
    
    /** COMT_URL : 组件地址(component) */
    private String comtUrl;
    
	
}
