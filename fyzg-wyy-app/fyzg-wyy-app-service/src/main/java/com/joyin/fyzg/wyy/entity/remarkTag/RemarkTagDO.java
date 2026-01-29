package com.joyin.fyzg.wyy.entity.remarkTag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_COMMON_REMARK的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@KeySequence(value = "S_SYS_REMARK_TAG",clazz = Long.class)
@TableName("SYS_REMARK_TAG")
public class RemarkTagDO {
    
    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private Long rId;
    
    
    /** NAME : 日历备注名称 */
    private String name;
    
    
    /** TYPE : 类型 */
    private String type;
    
    
    /** FONT_SIZE : 字体大小 */
    private String fontSize;
    
    
    /** FONT_WEIGHT : 字体加粗 */
    private String fontWeight;
    
    
    /** COLOR : 文字颜色 */
    private String color;
    
    
    /** BACKGROUND : 背景颜色 */
    private String background;
    
    
    /** CENTER : 显示位置 */
    private String center;
    
    
    /** FONT_FAMILY : 文字字体 */
    private String fontFamily;
    
    
    /** TEXT : 大文本 */
    private String text;

    /** BR_LENGTH : 换行 */
    private Integer brLength;
    
	
}
