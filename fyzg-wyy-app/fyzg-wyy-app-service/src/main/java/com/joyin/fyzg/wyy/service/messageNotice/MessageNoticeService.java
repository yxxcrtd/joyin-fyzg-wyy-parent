package com.joyin.fyzg.wyy.service.messageNotice;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

public interface MessageNoticeService {


    MethodResponse insertMessageNotice(@RequestBody MessageNoticeDO messageNoticeDo);


    MethodResponse updateMessageNoticeById(@RequestBody MessageNoticeDO messageNoticeDo);


    MethodResponse deleteMessageNoticeById(@RequestParam("rId") String rId) ;


    MessageNoticeDO getMessageNoticeById(@RequestParam("rId") String rId) ;


    List<MessageNoticeDO> listMessageNoticeByName(@RequestParam("name") String name) ;

    Map<String, Object> listMessageNoticeByName(String title, String sender, String type,PageWrapper pageWrapper);

    Map<String, Object> listMessageNoticeByType(String type,String sender, String title ,String readFlag,String noticeWay,String beginDate,String endDate,String sortFlag,PageWrapper pageWrapper);

    List<MessageNoticeDO> listAll() ;
}

