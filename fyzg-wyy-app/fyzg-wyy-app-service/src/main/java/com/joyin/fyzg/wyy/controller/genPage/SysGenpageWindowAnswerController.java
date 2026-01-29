package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowAnswer;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowAnswerService;
import com.joyin.fyzg.wyy.vo.genPage.SysGenpageWindowAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("SysGenpageWindowAnswer")
@Slf4j
public class SysGenpageWindowAnswerController {

    @Autowired
    private SysGenpageWindowAnswerService sysGenpageWindowAnswerService;


    //新增答卷
    @PostMapping("add")
    public RestResponse<SysGenpageWindowAnswer> addGenpageWindowAnswer(@RequestBody SysGenpageWindowAnswerVO sysGenpageWindowAnswerVO) {
        return RestResponse.transMethodResponse(sysGenpageWindowAnswerService.batchInsertGenpageWindowAnswer(sysGenpageWindowAnswerVO));
    }

    //查询用户是否已经填写过答卷 true 已经填过，false 没有填过
    @GetMapping("queryUserIsAnswer")
    public RestResponse<Boolean> queryUserIsAnswer(@RequestParam("windowId") String windowId, @RequestParam("userName") String userName) {
        return RestResponse.transMethodResponse(sysGenpageWindowAnswerService.queryUserIsAnswer(windowId, userName));
    }


}
