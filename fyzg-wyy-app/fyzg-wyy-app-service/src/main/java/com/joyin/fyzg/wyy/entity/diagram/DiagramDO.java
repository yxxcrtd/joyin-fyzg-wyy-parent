package com.joyin.fyzg.wyy.entity.diagram;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表SYS_DIAGRAM的实体类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@TableName("SYS_DIAGRAM")
public class DiagramDO {

    /** R_ID : 主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;


    /** NAME : 名称 */
    private String name;


    /** LIST_SQL : SQL列表 */
    private String listSql;


    /** DETAIL_SQL : 明细SQL */
    private String detailSql;


    /** TEMPLATE_CFG_JSON : 模板配置 */
    private String templateCfgJson;


    /** SHOW_TYPE : 对象显示方式：左侧：left；右上角：upperRight； */
    private String showType;


    /** REMARK : 备注 */
    private String remark;


    /** ALLOW_CREATED_SQL : 流程发起判断SQL */
    private String allowCreatedSql;


    /** CROSS_FILTER_FLAG : 跨对象节点筛选:自动（“0”），手动（“1”） */
    private String crossFilterFlag;

}
