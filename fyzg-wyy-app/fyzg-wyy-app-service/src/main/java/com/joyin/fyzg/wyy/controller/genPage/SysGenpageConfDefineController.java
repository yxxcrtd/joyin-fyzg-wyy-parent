package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageConfDefine;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageConfDefineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("SysGenpageConfDefine")
@Slf4j
public class SysGenpageConfDefineController {

    @Autowired
    private SysGenpageConfDefineService sysGenpageConfDefineService;

    //查询内页配置
    @GetMapping("list")
    public RestResponse<List<SysGenpageConfDefine>> getAllGenpageConfDefine(@RequestParam(value = "pageId", required = false) String pageId) {
        return RestResponse.transMethodResponse(sysGenpageConfDefineService.queryGenpageConfDefine(pageId));
    }

    //分页查询内页配置
    @GetMapping("listPage")
    public RestResponse<PageResponse<SysGenpageConfDefine>> getAllGenpageConfDefineByPage(@RequestParam(value = "pageId", required = false) String pageId, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(sysGenpageConfDefineService.queryGenpageConfDefineByPage(pageId, pageWrapper));
    }

    //新增内页配置
    @PostMapping("add")
    public RestResponse<SysGenpageConfDefine> addGenpageConfDefine(@RequestBody List<SysGenpageConfDefine> sysGenpageConfDefineList) {
        return RestResponse.transMethodResponse(sysGenpageConfDefineService.batchInsertGenpageConfDefine(sysGenpageConfDefineList));
    }

    //修改内页配置
    @PostMapping("update")
    public RestResponse<SysGenpageConfDefine> updateGenpageConfDefine(@RequestBody SysGenpageConfDefine sysGenpageConfDefine) {
        return RestResponse.transMethodResponse(sysGenpageConfDefineService.updateGenpageConfDefine(sysGenpageConfDefine));
    }

    //删除内页配置
    @PostMapping("delete")
    public RestResponse<SysGenpageConfDefine> deleteGenpageConfDefine(@RequestBody List<String> idList) {
        return RestResponse.transMethodResponse(sysGenpageConfDefineService.batchDeleteGenpageConfDefine(idList));
    }
}
