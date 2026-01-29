package com.joyin.fyzg.wyy.controller.common;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.common.SysParamConfigDO;
import com.joyin.fyzg.wyy.service.common.SysParamConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysParamConfig")
@Slf4j
public class SysParamConfigController {
    @Autowired
    private SysParamConfigService configService;

    // 查询所有启用的配置
    @GetMapping("list")
    public RestResponse<List<SysParamConfigDO>> getAllEnabledConfigsByType(@RequestParam("type") String type) {
        return RestResponse.success(configService.getAllEnabledConfigsByType(type));
    }

    // 按 param_key 查询配置
    @GetMapping("getBy")
    public RestResponse<SysParamConfigDO> getConfig(@RequestParam("paramKey") String paramKey) {
        return RestResponse.success(configService.getConfigByKey(paramKey));
    }
}
