package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 表RBAC_ROLE_RELATE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@KeySequence(value = "S_SYS_RBAC_ROLE_RELATE",clazz = Long.class)
@TableName("SYS_RBAC_ROLE_RELATE")
@Builder
public class RoleRelateDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;
    
    
    /** R_TYPE : 关联类型 */
    private String rType;
    
    
    /** ROLE_CODE : 角色代码 */
    private String roleCode;
    
    
    /** R_CODE1 : 第1关联对象代码 */
    private String rCode1;
    
    
    /** R_CODE2 : 第2关联对象代码 */
    private String rCode2;
    
    
    /** R_CODE3 : 第3关联对象代码 */
    private String rCode3;
    
    
    /** R_CODE4 : 第4关联对象代码 */
    private String rCode4;
    
    
    /** R_CODE5 : 第5关联对象代码 */
    private String rCode5;
    
	
}
