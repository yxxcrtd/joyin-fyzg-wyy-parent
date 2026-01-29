package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
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
@KeySequence(value = "S_SYS_RBAC_USER_MICRO_MAPPING",clazz = Long.class)
@TableName("SYS_RBAC_USER_MICRO_MAPPING")
public class UserMicroMappingDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private String rId;
    
    
    /** USER_O_CODE : 用户code */
    private String userOCode;


    /** SOURCE_TYPE : 子系统标识  */
    private String sourceType;


    /** SOURCE_KEY : 用户在子系统中的唯一标识  */
    private String sourceKey;


    /** EXT_VALUE1 : 用户扩展属性1  */
    private String extValue1;


    /** EXT_VALUE2 : 用户扩展属性2  */
    private String extValue2;


    /** EXT_VALUE3 : 用户扩展属性3  */
    private String extValue3;


    /** EXT_VALUE4 : 用户扩展属性4  */
    private String extValue4;


    /** EXT_VALUE5 : 用户扩展属性5  */
    private String extValue5;

	
}
