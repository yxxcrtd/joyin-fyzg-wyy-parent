package com.joyin.fyzg.wyy.service.financier;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpBO;
import com.joyin.fyzg.wyy.entity.financier.FinancierMpDO;

import java.util.List;

public interface FinancierMpService {

    MethodResponse insertFinancierMp(FinancierMpDO financierDO);

	MethodResponse batchInsertFinancierMp(List<FinancierMpDO> menuPageActionDOList);

	MethodResponse updateFinancierMpById(FinancierMpDO financierDO);

	MethodResponse deleteFinancierMpByCustOCode(String custOCode);

	MethodResponse deleteFinancierMpById(String rId);

	FinancierMpDO getFinancierMpById(String rId);

	List<FinancierMpDO> listAll();

	List<FinancierMpDO> listFinancierMpByCustOCode(String custOCode);

	List<FinancierMpBO> listFinancierMpBOByCustOCode(String custOCode);

	MethodResponse batchSaveFinancierMp(List<FinancierMpDO> financierMpDOList, String custOCode);
}
