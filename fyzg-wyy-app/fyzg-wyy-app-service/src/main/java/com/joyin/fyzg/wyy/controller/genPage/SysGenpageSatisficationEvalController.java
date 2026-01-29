package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageSatisficationEval;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageSatisficationEvalService;
import com.joyin.fyzg.wyy.service.genPage.impl.SysGenpageSatisficationEvalAttachmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("SysGenpageSatisficationEval")
@Slf4j
public class SysGenpageSatisficationEvalController {

    @Autowired
    private SysGenpageSatisficationEvalService sysGenpageSatisficationEvalService;


    //查询满意度评价
    @GetMapping("list")
    public RestResponse<List<SysGenpageSatisficationEval>> getAllGenpageSatisficationEval(@RequestParam("pageName") String pageName) {
        return RestResponse.transMethodResponse(sysGenpageSatisficationEvalService.queryGenpageSatisficationEval(pageName));
    }

    //分页查询满意度评价
    @GetMapping("listPage")
    public RestResponse<PageResponse<SysGenpageSatisficationEval>> getAllGenpageSatisficationEvalByPage(@RequestParam("pageId") String pageId, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(sysGenpageSatisficationEvalService.queryGenpageSatisficationEvalByPage(pageId, pageWrapper));
    }

    //新增满意度评价
    @PostMapping("add")
    public RestResponse<SysGenpageSatisficationEval> addGenpageSatisficationEval(@RequestBody SysGenpageSatisficationEval sysGenpageSatisficationEval) {
        return RestResponse.transMethodResponse(sysGenpageSatisficationEvalService.insertGenpageSatisficationEval(sysGenpageSatisficationEval));
    }

}
