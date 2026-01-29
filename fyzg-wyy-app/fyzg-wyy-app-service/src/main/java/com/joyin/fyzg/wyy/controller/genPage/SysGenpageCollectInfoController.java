package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageCollectInfo;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageCollectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 内页配置汇总信息配置
 */
@RestController
@RequestMapping("SysGenpageCollectInfo")
@Slf4j
public class SysGenpageCollectInfoController {

    @Autowired
    private SysGenpageCollectInfoService sysGenpageCollectInfoService;

    //输入id,查询sql结果
    @GetMapping("querySqlResult")
    public RestResponse getCollectInfoResult(@RequestParam("ids") String ids) {
        return RestResponse.success(sysGenpageCollectInfoService.queryCollectInfoResult(ids));
    }

    //输入id和参数，查询sql结果
    @GetMapping("querySqlResultByParam")
    public RestResponse getCollectInfoResultByParam(@RequestParam("ids") String ids, @RequestParam("param") String param) {
        Map<String, Object> paramMap = JsonUtils.json2map(param);
        return RestResponse.success(sysGenpageCollectInfoService.queryCollectInfoResultByParam(ids, paramMap));
    }

    //查询内页配置
    @GetMapping("list")
    public RestResponse<List<SysGenpageCollectInfo>> getAllGenpageCollectInfo(@RequestParam("collectDesp") String collectDesp) {
        return RestResponse.transMethodResponse(sysGenpageCollectInfoService.queryGenpageCollectInfo(collectDesp));
    }

    //分页查询内页配置
    @GetMapping("listPage")
    public RestResponse<PageResponse<SysGenpageCollectInfo>> getAllGenpageCollectInfoByPage(@RequestParam("collectDesp") String collectDesp, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(sysGenpageCollectInfoService.queryGenpageCollectInfoByPage(collectDesp, pageWrapper));
    }

    //新增内页配置
    @PostMapping("add")
    public RestResponse<SysGenpageCollectInfo> addGenpageCollectInfo(@RequestBody List<SysGenpageCollectInfo> sysGenpageCollectInfoList) {
        return RestResponse.transMethodResponse(sysGenpageCollectInfoService.batchInsertGenpageCollectInfo(sysGenpageCollectInfoList));
    }

    //修改内页配置
    @PostMapping("update")
    public RestResponse<SysGenpageCollectInfo> updateGenpageCollectInfo(@RequestBody SysGenpageCollectInfo sysGenpageCollectInfo) {
        return RestResponse.transMethodResponse(sysGenpageCollectInfoService.updateGenpageCollectInfo(sysGenpageCollectInfo));
    }

    //删除内页配置
    @PostMapping("delete")
    public RestResponse<SysGenpageCollectInfo> deleteGenpageCollectInfo(@RequestBody List<String> idList) {
        return RestResponse.transMethodResponse(sysGenpageCollectInfoService.batchDeleteGenpageCollectInfo(idList));
    }
}
