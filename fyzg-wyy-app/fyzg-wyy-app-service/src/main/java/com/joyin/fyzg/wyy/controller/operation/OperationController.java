package com.joyin.fyzg.wyy.controller.operation;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.service.messageNotice.MessageNoticeService;
import com.joyin.fyzg.wyy.service.messageNotice.OperationService;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/dfcomposeapi/operation")
@Slf4j
public class OperationController extends BaseController {
    @Autowired
    MessageNoticeService messageNoticeService;

    @Autowired
    OperationService operationService;


    @PostMapping("mapRptDefine4App")
    public RestResponse<Map> mapRptDefine4App(@RequestBody RequestParamMap params) {
        params.put("userCode", super.getLoginUserCode());
        return operationService.mapRptDefine4App(params);
    }

    @GetMapping("getRptTableData4App")
    public RestResponse<Map> getRptTableData4App(@RequestParam("rId") String rId, @RequestParam("param") String param,HttpServletRequest httpServletRequest) {
        /*Map<String, Object> paramMap = JsonUtils.json2map(param);
        paramMap.put("userCode", super.getLoginUserCode());
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(paramMap);
        PlaceHolderUtils.dealWithPage4Sql(pageWrapper,paramMap);*/
        return operationService.getRptTableData4App(rId, param,httpServletRequest);
    }


    @GetMapping("getMenuPageById")
    public RestResponse<MenuPageDO> getMenuPageById(@RequestParam("rId") String rId) {
        return operationService.getMenuPageById(rId);
    }

}
