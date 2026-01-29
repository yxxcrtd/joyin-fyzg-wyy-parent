package com.joyin.fyzg.wyy.controller.messageNotice;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;

import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import com.joyin.fyzg.wyy.service.messageNotice.MessageNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dfcomposeapi/messageNotice")
@Slf4j
public class MessageNoticeController extends BaseController {
    @Autowired
    MessageNoticeService messageNoticeService;

    @PostMapping("insertMessageNotice")
    public RestResponse insertMessageNotice(@RequestBody MessageNoticeDO messageNoticeDO) {
        return RestResponse.success(messageNoticeService.insertMessageNotice(messageNoticeDO));
    }

    @PostMapping("updateMessageNoticeById")
    public RestResponse updateMessageNoticeById(@RequestBody MessageNoticeDO messageNoticeDO) {
        return RestResponse.success(messageNoticeService.updateMessageNoticeById(messageNoticeDO));
    }

    @DeleteMapping("deleteMessageNoticeById")
    public RestResponse deleteMessageNoticeById(@RequestParam("rId") String rId) {
        return RestResponse.success(messageNoticeService.deleteMessageNoticeById(rId));
    }

    @GetMapping("getMessageNoticeById")
    public RestResponse getMessageNoticeById(@RequestParam("rId") String rId) {
        return RestResponse.success(messageNoticeService.getMessageNoticeById(rId));
    }

    @GetMapping("listMessageNoticeByName")
    public RestResponse<Map<String, Object>> listMessageNoticeByName(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "sender",required = false) String sender, @RequestParam(value = "type", required = false) String type, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(messageNoticeService.listMessageNoticeByName(title,sender,type,pageWrapper));
    }


    @GetMapping("listMessageNoticeByType")
    public RestResponse<Map<String, Object>> listMessageNoticeByType(@RequestParam("type") String type,@RequestParam(value = "sender",required = false) String sender, @RequestParam(value = "title",required = false) String title, @RequestParam(value = "readFlag",required = false) String readFlag , @RequestParam(value = "noticeWay",required = false) String noticeWay, @RequestParam(value = "beginDate",required = false) String beginDate,@RequestParam(value = "endDate",required = false) String endDate,@RequestParam(value = "sortFlag",required = false) String sortFlag, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(messageNoticeService.listMessageNoticeByType(type,sender,title,readFlag,noticeWay,beginDate,endDate,sortFlag,pageWrapper));
    }

    @GetMapping("listAll")
    public RestResponse<List<MessageNoticeDO>> listAll() {
        return RestResponse.success(messageNoticeService.listAll());
    }

}
