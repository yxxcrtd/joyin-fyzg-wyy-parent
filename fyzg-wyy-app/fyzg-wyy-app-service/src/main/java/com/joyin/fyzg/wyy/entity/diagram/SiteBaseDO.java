package com.joyin.fyzg.wyy.entity.diagram;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_DIAGRAM_SITE_BASE的实体类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_DIAGRAM_SITE_BASE")
public class SiteBaseDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.UUID)
    private String rId;


    /** SITE_ID : 站点id */
    private String siteId;


    /** SITE_NAME : 站点名称 */
    private String siteName;


    /** F1_NECESSARY : 限制发起流程:是("1")、否("0")。默认为“否” */
    private String f1Necessary;


    /** F1_GROUP_TAG : 分组标签 */
    private Long f1GroupTag;


    /** F1_TMPL_ID : 流程模板ID */
    private String f1TmplId;


    /** DIAGRAM_ID : 运行图ID */
    private String diagramId;


    /** R_TYPE : 类型参数 */
    private String rType;


    /** F1_CROSS_FLAG : 是否跨对象 */
    private String f1CrossFlag;


    /** F1_CROSS_MP_SQL : 跨对象映射SQL */
    private String f1CrossMpSql;


    /** TEMPLATE_ID : 运行图模板ID */
    private String templateId;

}
