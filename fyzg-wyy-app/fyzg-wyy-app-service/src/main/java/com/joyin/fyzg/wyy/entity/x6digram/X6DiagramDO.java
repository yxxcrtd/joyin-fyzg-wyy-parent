package com.joyin.fyzg.wyy.entity.x6digram;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_X6_DIAGRAM的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_X6_DIAGRAM")
public class X6DiagramDO {
    
    /** DIAGRAM_ID :  */
    @TableId(value = "DIAGRAM_ID", type = IdType.UUID)
    private String diagramId;
    
    
    /** DIAGRAM_NAME : 运行图名称 */
    private String diagramName;
    
    
    /** MAPPING_SQL : 对象与模板类型映射SQL */
    private String mappingSql;
    
    
    /** C_SQL : 节点阶段状态判断SQL */
    private String cSql;
    
    
    /** D_SQL : 用户数据权限判断SQL */
    private String dSql;
    
    
    /** REMARK : 备注 */
    private String remark;


    /** O_TYPE : 所属对象*/
    private String oType;
    
	
}
