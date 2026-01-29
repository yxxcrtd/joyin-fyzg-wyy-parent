package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.genPage.SysGenpageWindowQuestion;
import com.joyin.fyzg.wyy.service.genPage.SysGenpageWindowQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("SysGenpageWindowQuestion")
@Slf4j
public class SysGenpageWindowQuestionController {

    @Autowired
    private SysGenpageWindowQuestionService sysGenpageWindowQuestionService;

    //查询试卷问题列表
    @GetMapping("list")
    public RestResponse<List<SysGenpageWindowQuestion>> getAllGenpageWindowQuestion(@RequestParam("windowId") String windowId) {
        return RestResponse.transMethodResponse(sysGenpageWindowQuestionService.queryGenpageWindowQuestion(windowId));
    }


}
