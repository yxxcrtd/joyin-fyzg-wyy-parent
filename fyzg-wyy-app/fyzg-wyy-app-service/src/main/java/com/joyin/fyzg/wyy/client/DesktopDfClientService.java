package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.entity.desktop.IframeDO;
import com.joyin.fyzg.wyy.entity.diagram.DiagramDO;
import com.joyin.fyzg.wyy.entity.ds.DatasourceDO;
import com.joyin.fyzg.wyy.entity.remarkTag.RemarkTagDO;
import com.joyin.fyzg.wyy.entity.x6digram.X6DiagramDO;
import com.joyin.fyzg.wyy.vo.rbac.ContainerVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")

public interface DesktopDfClientService {
    String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/desktopDf/";


    @GetMapping(ROOT_NAME + "listIframeByUserCodeAndType")
    public FeignResponse<List<IframeDO>> listIframeByUserCodeAndType(@RequestParam("type") String type) ;

    @GetMapping(ROOT_NAME + "listReportByUserCodeAndType")
    public FeignResponse<List<ContainerVO>> listReportByUserCodeAndType(@RequestParam("type") String type) ;

    @GetMapping(ROOT_NAME + "listKanBanByUserCodeAndType")
    public FeignResponse<List<DatasourceDO>> listKanBanByUserCodeAndType(@RequestParam("type") String type) ;
    @GetMapping(ROOT_NAME + "listDiagramByUserCodeAndType")
    public FeignResponse<List<DiagramDO>> listDiagramByUserCodeAndType(@RequestParam("type") String type) ;

    @GetMapping(ROOT_NAME + "listX6DiagramByUserCodeAndType")
    public FeignResponse<List<X6DiagramDO>> listX6DiagramByUserCodeAndType(@RequestParam("type") String type) ;

    @GetMapping(ROOT_NAME + "listRemarkByUserCodeAndType")
    public FeignResponse<List<RemarkTagDO>> listRemarkByUserCodeAndType(@RequestParam("type") String type) ;

}
