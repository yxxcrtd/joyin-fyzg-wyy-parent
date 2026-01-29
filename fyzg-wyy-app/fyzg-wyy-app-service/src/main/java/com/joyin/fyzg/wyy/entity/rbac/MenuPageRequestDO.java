package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表RBAC_MENU_PAGE_REQUEST的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_RBAC_MENU_PAGE_REQUEST")
public class MenuPageRequestDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** PAGE_ID : 菜单页面ID */
    private String pageId;
    
    
    /** REQ_NAME : 请求名称 */
    private String reqName;
    
    
    /** REQ_URL : 请求地址 */
    private String reqUrl;


    /** DEF_PERMISSION : 默认权限 */
    private String defPermission;

	
}
