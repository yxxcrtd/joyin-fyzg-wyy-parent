package com.joyin.fyzg.wyy.vo.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表RBAC_MENU_PAGE_REQUEST的VO类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPageActionVO {
    
    /** R_ID : 数据编号，自增长主键 */
    private String rId;


    /** PAGE_ID : 菜单页面ID */
    private String pageId;


    /** ACT_NAME : 操作代码 */
    private String actCode;


    /** ACT_NAME : 操作名称 */
    private String actName;


    /** DEF_PERMISSION : 默认权限 */
    private String defPermission;

    
	
}
