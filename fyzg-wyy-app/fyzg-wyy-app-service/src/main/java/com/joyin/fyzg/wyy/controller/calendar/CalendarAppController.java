package com.joyin.fyzg.wyy.controller.calendar;

import com.google.common.collect.Maps;
import com.joyin.fyzg.common.*;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.client.CalendarAppClientService;
import com.joyin.fyzg.wyy.entity.calendar.CalendarFinishedDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 日历
 *
 * @author cqh
 * @date 2022/4/18 13:47
 */
@RestController
@RequestMapping("dfcomposeapi/calendarApp")
public class CalendarAppController extends BaseController {

    @Autowired
    CalendarAppClientService calendarAppClientService;


    @GetMapping("getCountData4App")
    public RestResponse<List<Map>> getCountData4App(@RequestParam("param") String param) {
        return RestResponse.transFeignResponse(calendarAppClientService.getCountData4App(param)) ;

        //return RestResponse.transFeignResponse((MethodResponse)calendarAppClientService.getCountData4App(param).getResult());
    }

    @GetMapping("getTableData4App")
    public RestResponse<List<Map>> getTableData4App(@RequestParam("cfgId") Long cfgId, @RequestParam("selectedDate") String selectedDate, @RequestParam("param") String param) {
        return RestResponse.transFeignResponse(calendarAppClientService.getTableData4App(cfgId,selectedDate,param));
    }

    /**
     * @Description 华泰日历查明细接口
     * @Param
     * @Return
     * @Author zihongshuai
     * @Date 2021/11/25 11:44
     */
    @PostMapping("getTableData4HtqzApp")
    public RestResponse<List<Map>> getTableData4HtqzApp(@RequestBody RequestParamMap params) {
        return RestResponse.transFeignResponse(calendarAppClientService.getTableData4HtqzApp(params));
    }

    @PostMapping("confirmRemind")
    public RestResponse confirmRemind(@RequestBody CalendarFinishedDO calendarFinishedDO) {
        return RestResponse.transFeignResponse(calendarAppClientService.confirmRemind(calendarFinishedDO));
    }

    @PostMapping("confirmSql")
    public RestResponse confirmSql(@RequestBody RequestParamMap params) {
        return RestResponse.transFeignResponse(calendarAppClientService.confirmSql(params));
    }

}
