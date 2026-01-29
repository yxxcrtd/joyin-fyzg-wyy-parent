package com.joyin.fyzg.wyy.service.messageNotice;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OperationService {

    RestResponse<Map> mapRptDefine4App(RequestParamMap params);

    RestResponse<Map> getRptTableData4App(String rId, String param,HttpServletRequest httpServletRequest);

    RestResponse<MenuPageDO> getMenuPageById(String rId);

    List<MessageNoticeDO> listAll() ;
}

