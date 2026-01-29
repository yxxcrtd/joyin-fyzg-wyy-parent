package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 表SYS_RBAC_USER_EXT的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Builder
@Data
@TableName("SYS_RBAC_USER_EXT")
public class UserExtDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** USER_O_CODE : 用户code */
    private String userOCode;
    
    
    /** CFG_CODE : 键code */
    private String cfgCode;
    
    
    /** CFG_CONTENT : 配置信息 */
    private String cfgContent;
    
	
}
