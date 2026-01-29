package com.joyin.fyzg.wyy.entity.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_CONST_COMPONENT的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@TableName("SYS_CONST_COMPONENT")
public class ComponentDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;


    /** CODE : 卡片代码；对应组件sql的code字段 */
    private String code;


    /** LABEL : 卡片名称；对应组件sql的label字段 */
    private String label;


    /** PATTERN : 卡片模式 */
    private String pattern;
    
    
    /** DEF_WIDTH : 默认宽度 */
    private Long defWidth;
    
    
    /** LOCK_WIDHT : 固定宽度 */
    private String lockWidht;
    
    
    /** MIN_WIDTH : 最小宽度 */
    private Long minWidth;
    
    
    /** MAX_WIDTH : 最大宽度 */
    private Long maxWidth;
    
    
    /** DEF_HEIGHT : 默认高度 */
    private Long defHeight;
    
    
    /** LOCK_HEIGHT : 固定高度 */
    private String lockHeight;
    
    
    /** MIN_HEIGHT : 最小高度 */
    private Long minHeight;
    
    
    /** MAX_HEIGHT : 最大高度 */
    private Long maxHeight;
    
	
}
