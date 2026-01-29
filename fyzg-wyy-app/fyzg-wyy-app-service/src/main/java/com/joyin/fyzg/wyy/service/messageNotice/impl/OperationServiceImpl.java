package com.joyin.fyzg.wyy.service.messageNotice.impl;


import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.client.MenuPageDfClientService;
import com.joyin.fyzg.wyy.client.OperationDfClientService;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import com.joyin.fyzg.wyy.entity.rbac.MenuPageDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMapper;
import com.joyin.fyzg.wyy.mapper.messageNotice.MessageNoticeMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.messageNotice.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class OperationServiceImpl implements OperationService {
    @Autowired
    MessageNoticeMapper messageNoticeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FinancierMapper financierMapper;

    @Autowired
    OperationDfClientService operationDfClientService;

    @Autowired
    MenuPageDfClientService menuPageDfClientService;

    @Override
    public RestResponse<Map> mapRptDefine4App(RequestParamMap params) {
        return RestResponse.transFeignResponse(operationDfClientService.mapRptDefine4App(params));
    }

    @Override
    public RestResponse<Map> getRptTableData4App(String rId, String param, HttpServletRequest httpServletRequest) {
        return RestResponse.transFeignResponse(operationDfClientService.getRptTableData4App(rId,param));
    }

    @Override
    public RestResponse<MenuPageDO> getMenuPageById(String rId) {
        return RestResponse.transFeignResponse(menuPageDfClientService.getMenuPageById(rId));
    }

    @Override
    public List<MessageNoticeDO> listAll() {
        return messageNoticeMapper.selectList();
    }

}
