package com.joyin.fyzg.wyy.entity.ds;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_DATASOURCE的实体类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_DATASOURCE")
public class DatasourceDO {
    
    /** R_ID : 数据编号，自动生成 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;
    
    
    /** NAME : 名称 */
    private String name;
    
    
    /** PURPOSE : 用途 */
    private String purpose;
    
    
    /** SQL : sql内容 */
    @TableField("sql")
    private String sql;


    /** MENU_CODE : 发布之后的菜单代码 */
    private String menuCode;


    /** CFG_JSON : 用途的配置数据 */
    private String cfgJson;

}
