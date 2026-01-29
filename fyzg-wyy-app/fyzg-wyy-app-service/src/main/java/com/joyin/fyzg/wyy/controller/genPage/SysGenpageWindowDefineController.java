package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowDefine;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowDefineService;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowDefineVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("SysGenpageWindowDefine")
@Slf4j
public class SysGenpageWindowDefineController {

    @Autowired
    private SysGenpageWindowDefineService sysGenpageWindowDefineService;

    //查询试卷信息
    @GetMapping("list")
    public RestResponse<List<SysGenpageWindowDefine>> getAllGenpageWindowDefine(@RequestParam("title") String title) {
        return RestResponse.transMethodResponse(sysGenpageWindowDefineService.queryGenpageWindowDefine(title));
    }

    //分页查询试卷信息
    @GetMapping("listPage")
    public RestResponse<PageResponse<SysGenpageWindowDefine>> getAllGenpageWindowDefineByPage(@RequestParam("title") String title, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(sysGenpageWindowDefineService.queryGenpageWindowDefineByPage(title, pageWrapper));
    }

    //新增试卷
    @PostMapping("add")
    public RestResponse<SysGenpageWindowDefine> addGenpageWindowDefine(@RequestBody SysGenpageWindowDefineVO sysGenpageWindowConfDefineVO) {
        return RestResponse.transMethodResponse(sysGenpageWindowDefineService.insertGenpageWindowDefine(sysGenpageWindowConfDefineVO));
    }

    //修改试卷
    @PostMapping("update")
    public RestResponse<SysGenpageWindowDefine> updateGenpageWindowDefine(@RequestBody SysGenpageWindowDefineVO sysGenpageWindowConfDefineVO) {
        return RestResponse.transMethodResponse(sysGenpageWindowDefineService.updateGenpageWindowDefine(sysGenpageWindowConfDefineVO));
    }

    //删除试卷 id为题库定义表id
    @PostMapping("delete")
    public RestResponse<SysGenpageWindowDefine> deleteGenpageWindowDefine(@RequestBody List<Long> idList) {
        return RestResponse.transMethodResponse(sysGenpageWindowDefineService.batchDeleteGenpageWindowDefine(idList));
    }
}
