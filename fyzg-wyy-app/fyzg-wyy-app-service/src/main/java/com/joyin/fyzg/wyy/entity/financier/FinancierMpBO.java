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
public class FinancierMpBO extends FinancierMpDO{

    private String relateName;
}
