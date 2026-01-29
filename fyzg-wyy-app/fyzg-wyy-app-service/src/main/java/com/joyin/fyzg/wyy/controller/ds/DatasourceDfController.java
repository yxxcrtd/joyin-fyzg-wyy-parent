package com.joyin.fyzg.wyy.controller.ds;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.client.DatasourceDfClientService;
import com.joyin.fyzg.wyy.entity.ds.DatasourceDO;
import com.joyin.fyzg.wyy.service.ds.DatasourceService;
import com.joyin.fyzg.wyy.vo.ds.Datasource4PublishVO;
import com.joyin.fyzg.wyy.vo.kanban.DaiBanContentVO;
import com.joyin.fyzg.wyy.vo.kanban.KanbanContentVO4Base;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dfcomposeapi/datasource")
@Slf4j
public class DatasourceDfController extends BaseController {
    @Autowired
    DatasourceDfClientService datasourceDfClientService;

    @Autowired
    private DatasourceService datasourceService;

    @PostMapping("insertDatasource")
    public RestResponse insertDatasource(@RequestBody DatasourceDO datasourceDO) {
        return RestResponse.transFeignResponse(datasourceDfClientService.insertDatasource(datasourceDO));
    }

    @PostMapping("updateDatasourceById")
    public RestResponse updateDatasourceById(@RequestBody DatasourceDO datasourceDO) {
        return RestResponse.transFeignResponse(datasourceDfClientService.updateDatasourceById(datasourceDO));
    }

    @DeleteMapping("deleteDatasourceById")
    public RestResponse deleteDatasourceById(@RequestParam("rId") String rId) {
        return RestResponse.transFeignResponse(datasourceDfClientService.deleteDatasourceById(rId));
    }

    @GetMapping("getDatasourceById")
    public RestResponse getDatasourceById(@RequestParam("rId") String rId) {
        return RestResponse.transFeignResponse(datasourceDfClientService.getDatasourceById(rId));
    }

    @GetMapping("listDatasourceByName")
    public RestResponse<List<DatasourceDO>> listDatasourceByName(@RequestParam("name") String name) {
        return RestResponse.transFeignResponse(datasourceDfClientService.listDatasourceByName(name));
    }

    @GetMapping("listAll")
    public RestResponse<List<DatasourceDO>> listAll() {
        return RestResponse.transFeignResponse(datasourceDfClientService.listAll());
    }

    @PostMapping("saveDatasource4Publish")
    public RestResponse saveDatasource4Publish(@RequestBody Datasource4PublishVO datasource4PublishVO) {
        return RestResponse.transFeignResponse(datasourceDfClientService.saveDatasource4Publish(datasource4PublishVO));
    }

    @PostMapping("saveDatasource4UnPublish")
    public RestResponse saveDatasource4UnPublish(@RequestParam("rId") String rId, @RequestParam("menuCode") String menuCode) {
        return RestResponse.transFeignResponse(datasourceDfClientService.saveDatasource4UnPublish(rId, menuCode));
    }

    @GetMapping("getDatasourceTableData4App12")
    public RestResponse<List<Map>> getDatasourceTableData4App(@RequestParam("rId") String rId, @RequestParam("param") String param) {
        return RestResponse.transFeignResponse(datasourceDfClientService.getDatasourceTableData4App(rId, param));
    }

    @GetMapping("listAllDatasourceTableData4HtzqApp")
    public RestResponse<List<Map>> listAllDatasourceTableData4HtzqApp(@RequestParam("param") String param) {
        return RestResponse.transFeignResponse(datasourceDfClientService.listAllDatasourceTableData4HtzqApp(param));
    }

    //@GetMapping("getDatasourceTableData4KanBanApp")
    @GetMapping("getDatasourceTableData4App")
    public <K> RestResponse<List<K>> getDatasourceTableData4KanBanApp(@RequestParam("rId") String rId, @RequestParam("rId") String userCode ,@RequestParam("param") String param) {
        List<K> objects = datasourceService.handleDatasourceTableData4App(rId, param, userCode);
        return RestResponse.success(objects);
    }

    @GetMapping("getDatasourceTableData4DaiBan")
    public RestResponse<List<DaiBanContentVO>> getDatasourceTableData4DaiBan(@RequestParam("rId") String rId, @RequestParam("param") String param, @RequestParam("status") String status) {
        List<DaiBanContentVO> objects = datasourceService.handleDatasourceTableData4DaiBan(rId, param, status, null);
        return RestResponse.success(objects);
    }

    @GetMapping("getDatasourceTableData4Msg")
    public RestResponse<List<MsgContentVO>> getDatasourceTableData4Msg(@RequestParam("rId") String rId, @RequestParam("param") String param, @RequestParam("msgType") String msgType) {
        List<MsgContentVO> objects = datasourceService.handleDatasourceTableData4Msg(rId, param, msgType, null);
        return RestResponse.success(objects);
    }

}
