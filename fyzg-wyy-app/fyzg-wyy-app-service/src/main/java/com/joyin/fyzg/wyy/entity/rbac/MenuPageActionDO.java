package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表RBAC_MENU_PAGE_ACTION的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_RBAC_MENU_PAGE_ACTION")
public class MenuPageActionDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
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
