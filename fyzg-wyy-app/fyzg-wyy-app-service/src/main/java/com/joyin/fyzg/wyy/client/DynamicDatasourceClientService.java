package com.joyin.fyzg.wyy.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.entity.ds.DynamicDatasourceDO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")
public interface DynamicDatasourceClientService {

    String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/dynamicDatasource/";

    @PostMapping(ROOT_NAME + "insertDynamicDatasource")
    public FeignResponse insertDynamicDatasource(@RequestBody DynamicDatasourceDO dynamicDatasourceDO);

    @PostMapping(ROOT_NAME + "updateDynamicDatasourceById")
    public FeignResponse updateDynamicDatasourceById(@RequestBody DynamicDatasourceDO dynamicDatasourceDO);

    @DeleteMapping(ROOT_NAME + "deleteDynamicDatasourceById")
    public FeignResponse deleteDynamicDatasourceById(@RequestParam("rId") String rId);

    @GetMapping(ROOT_NAME + "getDynamicDatasourceById")
    public FeignResponse getDynamicDatasourceById(@RequestParam("rId") String rId);

    @GetMapping(ROOT_NAME + "listAll")
    public FeignResponse<List<DynamicDatasourceDO>> listAll();

    @GetMapping(ROOT_NAME + "listDynamicDatasourceByType")
    public FeignResponse<IPage<DynamicDatasourceDO>> listDynamicDatasourceByType(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") String type);
}
