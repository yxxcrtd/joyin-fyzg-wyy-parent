package com.joyin.fyzg.wyy.service.common;

import com.joyin.fyzg.wyy.entity.common.SysParamConfigDO;

import java.util.List;

public interface SysParamConfigService {
    public List<SysParamConfigDO> getAllEnabledConfigsByType(String type) ;


     SysParamConfigDO getConfigByKey(String paramKey) ;


}
