package com.joyin.fyzg.wyy.entity.financier;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表DW_CUST_FINANCIER的实体类
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
@KeySequence(value = "S_DW_CUST_FINANCIER_MP",clazz = Long.class)
@TableName("DW_CUST_FINANCIER_MP")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancierMpDO {

    /** R_ID : 数据编号，自增长主键 */
    @TableId(value = "R_ID", type = IdType.INPUT)
    private Long rId;


    /** CUST_O_CODE : 管理人代码 */
    private String custOCode;


    /** RELATE_CODE : 关联管理人 */
    private String relateCode;


    /** JY_INSERT_TIME : 创建时间 */
    private String jyInsertTime;


    /** JY_UPDATE_TIME : 修改时间 */
    private String jyUpdateTime;
}
