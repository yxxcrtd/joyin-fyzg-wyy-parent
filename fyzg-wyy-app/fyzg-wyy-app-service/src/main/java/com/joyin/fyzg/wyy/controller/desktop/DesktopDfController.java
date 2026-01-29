package com.joyin.fyzg.wyy.controller.desktop;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.client.DesktopDfClientService;
import com.joyin.fyzg.wyy.entity.desktop.IframeDO;
import com.joyin.fyzg.wyy.entity.diagram.DiagramDO;
import com.joyin.fyzg.wyy.entity.ds.DatasourceDO;
import com.joyin.fyzg.wyy.entity.remarkTag.RemarkTagDO;
import com.joyin.fyzg.wyy.entity.x6digram.X6DiagramDO;
import com.joyin.fyzg.wyy.vo.rbac.ContainerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("desktopDf")
@Slf4j
public class DesktopDfController extends BaseController {

    @Autowired
    DesktopDfClientService desktopDfClientService;

    @GetMapping("listIframeByUserCodeAndType")
    public RestResponse<List<IframeDO>> listIframeByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listIframeByUserCodeAndType(type));
    }

    @GetMapping("listReportByUserCodeAndType")
    public RestResponse<List<ContainerVO>> listReportByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listReportByUserCodeAndType(type));
    }

    @GetMapping("listKanBanByUserCodeAndType")
    public RestResponse<List<DatasourceDO>> listKanBanByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listKanBanByUserCodeAndType(type));
    }

    @GetMapping("listDiagramByUserCodeAndType")
    public RestResponse<List<DiagramDO>> listDiagramByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listDiagramByUserCodeAndType(type));
    }

    @GetMapping("listX6DiagramByUserCodeAndType")
    public RestResponse<List<X6DiagramDO>> listX6DiagramByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listX6DiagramByUserCodeAndType(type));
    }

    @GetMapping("listRemarkByUserCodeAndType")
    public RestResponse<List<RemarkTagDO>> listRemarkByUserCodeAndType(@RequestParam("type") String type) {
        return RestResponse.transFeignResponse(desktopDfClientService.listRemarkByUserCodeAndType(type));
    }
}