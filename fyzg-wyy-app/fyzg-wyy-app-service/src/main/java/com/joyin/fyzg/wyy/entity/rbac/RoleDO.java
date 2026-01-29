package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表RBAC_ROLE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@KeySequence(value = "S_SYS_RBAC_ROLE",clazz = Long.class)
@TableName("SYS_RBAC_ROLE")
public class RoleDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;
    
    
    /** ROLE_O_CODE : 角色代码 */
    private String roleOCode;
    
    
    /** ROLE_O_NAME : 角色名称 */
    private String roleOName;


    /** JY_INSERT_TIME : 创建时间 */
    private String jyInsertTime;


    /** JY_UPDATE_TIME : 修改时间 */
    private String jyUpdateTime;


    /** SOURCE_TYPE : 子系统标识  */
    private String sourceType;


    /** SOURCE_KEY : 角色在子系统中的唯一标识  */
    private String sourceKey;

    
	
}
