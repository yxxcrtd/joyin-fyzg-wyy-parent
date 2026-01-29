package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.common.RequestParamMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")
public interface OperationDfClientService {

     String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/rptApp/";


     /*@GetMapping( ROOT_NAME + "getDatasourceTableData4App")
     public FeignResponse<List<Map>> getOperationTableData4App(@RequestParam("rId") String rId, @RequestParam("param") String param) ;*/

     @GetMapping( ROOT_NAME + "mapRptDefine4App")
     public FeignResponse<Map> mapRptDefine4App(@RequestBody RequestParamMap params);

     @GetMapping( ROOT_NAME + "getRptTableData4App")
     public FeignResponse<Map> getRptTableData4App(@RequestParam("rId") String rId, @RequestParam("param") String param);

}
