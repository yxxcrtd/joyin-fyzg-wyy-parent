package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${fyzg.modules.rbac.applicationName}")
public interface MenuPageDfClientService {

     final String ROOT_NAME="${fyzg.modules.rbac.contextPath}/menuPage/";



     @GetMapping( ROOT_NAME + "getMenuPageById")
     FeignResponse<MenuPageDO> getMenuPageById(@RequestParam("rId") String rId);


}
