package com.joyin.fyzg.wyy.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表DEFINE_CONSTANT_ENUM的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_ML_MODEL_ENUM")
public class ModelEnumDO {
    
    /** ENUM_CODE : 枚举代码 */
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "ENUM_CODE", type = IdType.UUID)
    private String enumCode;
    
    
    /** ENUM_NAME : 枚举名称 */
    private String enumName;
    
    
    /** ENUM_OTYPE : 适用对象类型，0表示所有，其他表示具体对象类型 */
    private String enumOtype;
    
    
    /** ENUM_DESC : 描述 */
    private String enumDesc;


     /** ENUM_VISIBLE : 前端数据字典只显示为1的，为0的给开发在特定场景使用 */
    private String enumVisible;

	
}
