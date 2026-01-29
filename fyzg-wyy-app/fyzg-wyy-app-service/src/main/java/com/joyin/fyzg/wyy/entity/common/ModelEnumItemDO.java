package com.joyin.fyzg.wyy.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表DEFINE_CONSTANT_ENUM_ITEM的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_ML_MODEL_ENUM_ITEM")
public class ModelEnumItemDO {
    
    /** R_ID : 自增主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;


    /** ENUM_CODE : 常量代码 */
    private String enumCode;


    /** ENUM_LABEL : 枚举显示值 */
    private String enumLabel;


    /** ENUM_VALUE : 枚举存储值 */
    private String enumValue;


    /** ENUM_SORT : 显示顺序 */
    private Long enumSort;


    /** PARAM1 : 扩展参数1 */
    private String param1;


    /** PARAM2 : 扩展参数2 */
    private String param2;


    /** PARAM3 : 扩展参数3 */
    private String param3;

}
