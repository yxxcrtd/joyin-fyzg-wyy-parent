package com.joyin.fyzg.wyy.controller.calendar;

import com.alibaba.fastjson.JSONObject;
import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.client.CalendarClientService;
import com.joyin.fyzg.wyy.entity.calendar.CalendarDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("dfcomposeapi/calendar")
public class CalendarController extends BaseController {

    @Resource
    private CalendarClientService calendarClientService;

    @GetMapping("getPageList")
    public RestResponse<JSONObject> getPageList(@RequestParam String param) {
        return RestResponse.transFeignSuccess(calendarClientService.getPageList(param));
    }

    @GetMapping("getCalendarById")
    public RestResponse getCalendarById(@RequestParam Long rId) {
        return RestResponse.transFeignSuccess(calendarClientService.getCalendarById(rId));
    }

    @PostMapping("insertCalendar")
    public RestResponse<?> insertCalendar(@RequestBody CalendarDO calendarDO) {
        calendarClientService.insertCalendar(calendarDO);
        return RestResponse.success();
    }

    @PostMapping("updateCalendarById")
    public RestResponse<?> updateCalendarById(@RequestBody CalendarDO calendarDO) {
        return RestResponse.transFeignSuccess(calendarClientService.updateCalendarById(calendarDO));
    }

    @DeleteMapping("deleteCalendarById")
    public RestResponse<?> deleteCalendarById(@RequestParam Long rId) {
        return RestResponse.transFeignSuccess(calendarClientService.deleteCalendarById(rId));
    }
}
