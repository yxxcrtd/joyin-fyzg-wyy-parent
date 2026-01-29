package com.joyin.fyzg.wyy.service.common;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumDO;

import java.util.List;

public interface ModelEnumService {
    MethodResponse insertEnum(ModelEnumDO modelEnumDO);

    MethodResponse updateEnumById(ModelEnumDO modelEnumDO);

    MethodResponse deleteEnumById(String enumCode);

    List<ModelEnumDO> listAll();

	List<ModelEnumDO> listEnumByEnumName(String enumName);

    /**
     * 根据对象type获取所有枚举值
     * @author xiongyang
     * @date 11:20 2023/4/20
     * @param oType 对象type
     * @return java.util.List<com.joyin.fyzg.mldf.entity.model.ModelEnumDO> 枚举集合
     **/
    List<ModelEnumDO> listAllByType(String oType);
}
