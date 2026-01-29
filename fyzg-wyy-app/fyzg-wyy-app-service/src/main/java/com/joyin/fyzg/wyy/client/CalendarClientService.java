package com.joyin.fyzg.wyy.client;

import com.alibaba.fastjson.JSONObject;
import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.entity.calendar.CalendarDO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")
public interface CalendarClientService {

    String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/calendar/";

    @GetMapping(ROOT_NAME + "getPageList")
    public FeignResponse<JSONObject> getPageList(@RequestParam("param") String param);

    @GetMapping(ROOT_NAME + "getCalendarById")
    public FeignResponse getCalendarById(@RequestParam("rId") Long rId);

    @PostMapping(ROOT_NAME + "insertCalendar")
    public FeignResponse<?> insertCalendar(@RequestBody CalendarDO calendarDO) ;

    @PostMapping(ROOT_NAME + "updateCalendarById")
    public FeignResponse<?> updateCalendarById(@RequestBody CalendarDO calendarDO) ;

    @DeleteMapping(ROOT_NAME + "deleteCalendarById")
    public FeignResponse<?> deleteCalendarById(@RequestParam("rId") Long rId) ;
    
}
