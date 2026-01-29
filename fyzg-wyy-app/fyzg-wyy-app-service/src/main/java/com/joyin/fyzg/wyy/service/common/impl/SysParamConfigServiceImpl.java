package com.joyin.fyzg.wyy.service.common.impl;

import com.joyin.fyzg.wyy.entity.common.SysParamConfigDO;
import com.joyin.fyzg.wyy.mapper.common.SysParamConfigMapper;
import com.joyin.fyzg.wyy.service.common.SysParamConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysParamConfigServiceImpl implements SysParamConfigService {


    @Autowired
    private SysParamConfigMapper mapper;

    @Override
    public List<SysParamConfigDO> getAllEnabledConfigsByType(String type) {
        return mapper.findAllEnabledByType(type);
    }


    public SysParamConfigDO getConfigByKey(String paramKey) {
        return mapper.findByKey(paramKey);
    }

}
