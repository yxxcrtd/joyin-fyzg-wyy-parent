package com.joyin.fyzg.wyy.mapper.financier;

import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DW_CUST_FINANCIER表的DAO层类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
public interface FinancierDfMapper {

	List<FinancierDO> listFinancierByCustOCode(
			@Param("custOCode") String custOCode);

	List<FinancierMpBO> listFinancierMpBOByCustOCode(
			@Param("custOCode") String custOCode);
}

