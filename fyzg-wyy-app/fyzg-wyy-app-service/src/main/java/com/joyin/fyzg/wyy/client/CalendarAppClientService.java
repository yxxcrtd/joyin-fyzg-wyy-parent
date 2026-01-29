package com.joyin.fyzg.wyy.client;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.wyy.entity.calendar.CalendarFinishedDO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "${fyzg.modules.dfcomposeapi.applicationName}")
public interface CalendarAppClientService {

    final String ROOT_NAME = "${fyzg.modules.dfcomposeapi.contextPath}/calendarApp/";

    @GetMapping(ROOT_NAME + "getCountData4App")
    FeignResponse getCountData4App(@RequestParam("param") String param);

    @GetMapping(ROOT_NAME + "getTableData4App")
    FeignResponse getTableData4App(@RequestParam("cfgId") Long cfgId, @RequestParam("selectedDate") String selectedDate, @RequestParam("param") String param);

    @GetMapping(ROOT_NAME + "getTableData4HtqzApp")
    FeignResponse getTableData4HtqzApp(@RequestBody RequestParamMap params);

    @GetMapping(ROOT_NAME + "confirmRemind")
    FeignResponse confirmRemind(@RequestBody CalendarFinishedDO calendarFinishedDO);

    @GetMapping(ROOT_NAME + "confirmSql")
    FeignResponse confirmSql(@RequestBody RequestParamMap params);
}
