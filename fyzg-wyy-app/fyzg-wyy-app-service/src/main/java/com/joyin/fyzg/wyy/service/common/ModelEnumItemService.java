package com.joyin.fyzg.wyy.service.common;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemBO;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemDO;
import com.joyin.fyzg.wyy.vo.common.ModelEnumGroupVO;

import java.util.List;

public interface ModelEnumItemService {

    List<ModelEnumItemDO> listEnumItemByCode(String enumCode);
    List<ModelEnumItemBO> listAllEnumItem();

	ModelEnumItemDO getEnumItemById(String rId);

	MethodResponse insertEnumItem(ModelEnumItemDO modelEnumItemDO);

    MethodResponse updateEnumItemById(ModelEnumItemDO modelEnumItemDO);

    MethodResponse deleteEnumItemById(String rId);

    MethodResponse deleteEnumItemByCode(String enumCode);

    MethodResponse batchSaveGroupList(ModelEnumGroupVO modelEnumGroupVO);
}
