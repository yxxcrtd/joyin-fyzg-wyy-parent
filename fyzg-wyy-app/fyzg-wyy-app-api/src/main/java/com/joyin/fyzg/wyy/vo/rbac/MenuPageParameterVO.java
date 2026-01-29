package com.joyin.fyzg.wyy.vo.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表RBAC_MENU_PAGE_PARAMETER的VO类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPageParameterVO {
    
    /** R_ID : 数据编号，自增长主键 */
    private String rId;
    
    
    /** PAGE_ID : 菜单页面ID */
    private String pageId;
    
    
    /** PARAM_NAME : 参数名称 */
    private String paramName;
    
    
    /** PARAM_CODE : 参数代码 */
    private String paramCode;
    
    
    /** PARAM_VALUE : 参数值 */
    private String paramValue;
    
	
}
