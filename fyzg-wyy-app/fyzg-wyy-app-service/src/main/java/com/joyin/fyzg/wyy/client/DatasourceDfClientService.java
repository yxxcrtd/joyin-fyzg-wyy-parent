package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.entity.ds.DatasourceDO;
import com.joyin.fyzg.wyy.vo.ds.Datasource4PublishVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")
public interface DatasourceDfClientService {

     String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/datasource/";


     @PostMapping( ROOT_NAME + "insertDatasource")
     public FeignResponse insertDatasource(@RequestBody DatasourceDO datasourceDO);

     @PostMapping( ROOT_NAME + "updateDatasourceById")
     public FeignResponse updateDatasourceById(@RequestBody DatasourceDO datasourceDO);

     @DeleteMapping( ROOT_NAME + "deleteDatasourceById")
     public FeignResponse deleteDatasourceById(@RequestParam("rId") String rId) ;

     @GetMapping( ROOT_NAME + "getDatasourceById")
     public FeignResponse getDatasourceById(@RequestParam("rId") String rId) ;

     @GetMapping( ROOT_NAME + "listDatasourceByName")
     public FeignResponse<List<DatasourceDO>> listDatasourceByName(@RequestParam("name") String name) ;

     @GetMapping( ROOT_NAME + "listAll")
     public FeignResponse<List<DatasourceDO>> listAll() ;

     @PostMapping( ROOT_NAME + "saveDatasource4Publish")
     public FeignResponse saveDatasource4Publish(@RequestBody Datasource4PublishVO datasource4PublishVO) ;

     @PostMapping( ROOT_NAME + "saveDatasource4UnPublish")
     public FeignResponse saveDatasource4UnPublish(@RequestParam("rId") String rId,@RequestParam("menuCode") String menuCode) ;

     @GetMapping( ROOT_NAME + "getDatasourceTableData4App")
     public FeignResponse<List<Map>> getDatasourceTableData4App(@RequestParam("rId") String rId, @RequestParam("param") String param) ;

     @GetMapping( ROOT_NAME + "listAllDatasourceTableData4HtzqApp")
     public FeignResponse<List<Map>> listAllDatasourceTableData4HtzqApp(@RequestParam("param") String param) ;
}
