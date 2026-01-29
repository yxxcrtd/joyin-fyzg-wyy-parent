package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.entity.rbac.ComponentDO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${fyzg.modules.rbac.applicationName}")
public interface ComponentClientService {
    String ROOT_NAME = "${fyzg.modules.rbac.contextPath}/component/";

    @GetMapping(ROOT_NAME + "getComponentById")
    public FeignResponse getComponentById(@RequestParam("rId") String rId);

    @GetMapping(ROOT_NAME + "listAll")
    public FeignResponse<List<ComponentDO>> listAll();
}
