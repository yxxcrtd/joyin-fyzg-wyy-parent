package com.joyin.fyzg.wyy.service.financier;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.vo.financier.FinancierVO;

import java.util.List;

public interface FinancierService {

    MethodResponse insertFinancier(FinancierVO financierVO);

    MethodResponse updateFinancierById(FinancierVO financierVO);

    MethodResponse deleteFinancierById(String rId);

	MethodResponse deleteFinancierByCustOCode(String custOCode);

	FinancierDO getFinancierById(String rId);

	FinancierDO getFinancierByCustOCode(String custOCode);

	List<FinancierDO> listAll();

	List<FinancierDO> listFinancierByCustOName(String custOName);

	List<FinancierDO> listFinancierByCodeList(List<String> codeList);

	List<FinancierDO> listFinancierByCustOCode(String custOCode);

	PageResponse<FinancierDO> listFinanciersByCondition(String custOName, PageWrapper pageWrapper);
}
