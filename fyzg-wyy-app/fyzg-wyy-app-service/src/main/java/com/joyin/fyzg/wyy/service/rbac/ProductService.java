package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.ProductDO;

import java.util.List;
import java.util.Map;

public interface ProductService {

	ProductDO getProductById(Long rId);

	ProductDO getProductByCode(String code);

	MethodResponse getDesktopComponentDataById(Long rId);

	MethodResponse getAnnexSuffixByCode(String code);

	MethodResponse getMdAnnexSuffixByCode(String code);

	MethodResponse getDefaultDesktopCfgUserByCode(String code);

	MethodResponse listComponentWithFilter(String loginUserCode, String type);

	List<Map> listAllComponent();

	List<ProductDO> listAll();

	List<ProductDO> listAllEnabled();
}
