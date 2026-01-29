package com.joyin.fyzg.wyy.service.messageNotice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.BeanUtils;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.JsonUtils;
import com.joyin.fyzg.wyy.client.OperationDfClientService;
import com.joyin.fyzg.wyy.common.enums.RbacUserEnable;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMapper;
import com.joyin.fyzg.wyy.mapper.messageNotice.MessageNoticeMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.messageNotice.MessageNoticeService;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageNoticeVO;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
@Transactional
public class MessageNoticeServiceImpl implements MessageNoticeService {
    @Autowired
    MessageNoticeMapper messageNoticeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FinancierMapper financierMapper;

    @Autowired
    OperationDfClientService operationDfClientService;


    @Override
    public MethodResponse insertMessageNotice(MessageNoticeDO messageNoticeDo) {
        MessageNoticeDO noticeDo = new MessageNoticeDO();
        try {
            String senders = messageNoticeDo.getSender();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(senders)){
                if (org.apache.commons.lang3.StringUtils.equals(senders,"all")) {
                    QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
                    query.lambda().eq(UserDO::getUserType, "0");
                    query.lambda().in(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
                    List<UserDO> userDOList = userMapper.selectList(query);
                    for (int i = 0; i < userDOList.size(); i++){
                        BeanUtils.copyProperties(messageNoticeDo,noticeDo);
                        noticeDo.setSender(userDOList.get(i).getUserOName());
                        noticeDo.setOrg(userDOList.get(i).getOrg());
                        noticeDo.setCreateTime(DateUtil8.getNowTime_EN());
                        noticeDo.setUpdateTime(DateUtil8.getNowTime_EN());
                        noticeDo.setReadFlag("0");
                        messageNoticeMapper.insert(noticeDo);
                    }
                    return MethodResponse.success(messageNoticeDo.getSender());
                }else{
                    String senderArr[] = senders.split(",");
                    for (int i = 0; i < senderArr.length; i++){
                        BeanUtils.copyProperties(messageNoticeDo,noticeDo);
                        noticeDo.setSender(senderArr[i]);
                        noticeDo.setCreateTime(DateUtil8.getNowTime_EN());
                        noticeDo.setUpdateTime(DateUtil8.getNowTime_EN());
                        noticeDo.setReadFlag("0");
                        messageNoticeMapper.insert(noticeDo);
                    }
                }

            }
            return MethodResponse.success(messageNoticeDo.getSender());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(INSERT_FAIL.formatEntity(MessageNoticeDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MessageNoticeDO.class, e));
        }
    }

    @Override
    public MethodResponse updateMessageNoticeById(MessageNoticeDO messageNoticeDo) {
        try {
            messageNoticeDo.setUpdateTime(DateUtil8.getNowTime_EN());
            //一键已读
            if (org.apache.commons.lang.StringUtils.equals(messageNoticeDo.getRId(),"all")) {
                QueryWrapper<MessageNoticeDO> query = new QueryWrapper<>();
                messageNoticeDo.setReadFlag("1");
                if (org.apache.commons.lang.StringUtils.isNotBlank(messageNoticeDo.getType()) && org.apache.commons.lang.StringUtils.isNotBlank(messageNoticeDo.getSender())) {
                    query.lambda().eq(MessageNoticeDO::getType, messageNoticeDo.getType());
                    query.lambda().eq(MessageNoticeDO::getSender, messageNoticeDo.getSender());
                    messageNoticeMapper.update(messageNoticeDo,query);
                }
                return MethodResponse.success();
            }
            messageNoticeMapper.updateById(messageNoticeDo);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(UPDATE_FAIL.formatEntity(MessageNoticeDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MessageNoticeDO.class, e));
        }
    }

    @Override
    public MethodResponse deleteMessageNoticeById(String rId) {
        try {
            messageNoticeMapper.deleteById(rId);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(DELETE_FAIL.formatEntity(MessageNoticeDO.class, e).getErrorMsg(), e);
            return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MessageNoticeDO.class, e));
        }
    }

    @Override
    public MessageNoticeDO getMessageNoticeById(String rId) {
        return messageNoticeMapper.selectById(rId);
    }

    @Override
    public List<MessageNoticeDO> listMessageNoticeByName(String name) {
        QueryWrapper<MessageNoticeDO> query = new QueryWrapper<MessageNoticeDO>();
        if (StringUtils.isNotEmpty(name)) {
            query.lambda().eq(MessageNoticeDO::getTitle, name);
        }
        return messageNoticeMapper.selectList(query);
    }

    @Override
    public Map<String, Object> listMessageNoticeByName(String title, String sender,String type, PageWrapper pageWrapper) {
        Page<MessageNoticeVO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());
        List<Map<String, Object>> resultList = Lists.newArrayList();
        Map<String, Object> resultMap = new HashMap<>();
        IPage<MessageNoticeVO> resultPage = messageNoticeMapper.selectPageByParam(page, title, sender, type);
        if (resultPage == null) {
            return resultMap;
        }

        resultPage.getRecords().forEach(messageNotice -> {
            String messageNoticeJson = JsonUtils.obj2json(messageNotice);
            Map<String, Object> messageNoticeMap = JsonUtils.json2map(messageNoticeJson);
            resultList.add(messageNoticeMap);
        });
        resultMap.put("records",resultList);
        resultMap.put("current",resultPage.getCurrent());
        resultMap.put("size",resultPage.getSize());
        resultMap.put("totalPages",resultPage.getPages());
        resultMap.put("total",resultPage.getTotal());

        return resultMap;
    }

    @Override
    public Map<String, Object> listMessageNoticeByType(String type, String sender,String title ,String readFlag,String noticeWay,String beginDate,String endDate,String sortFlag, PageWrapper pageWrapper) {
        Page<MessageNoticeDO> page = new Page<>(pageWrapper.getCurrentPage(),pageWrapper.getPageSize());
        List<Map<String, Object>> resultList = Lists.newArrayList();
        Map<String, Object> resultMap = new HashMap<>();
        IPage<MessageNoticeDO> resultPage = null;
        if (org.apache.commons.lang.StringUtils.isEmpty(type)) {
            resultPage = messageNoticeMapper.selectPage(page,new QueryWrapper<>());
        }
        else {
            LambdaQueryWrapper<MessageNoticeDO> queryWrapper = new QueryWrapper<MessageNoticeDO>().lambda();
            if (org.apache.commons.lang.StringUtils.isNotEmpty(type)) {
                queryWrapper.eq(MessageNoticeDO::getType, type);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(sender)) {
                queryWrapper.eq(MessageNoticeDO::getSender, sender);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(title)) {
                queryWrapper.like(MessageNoticeDO::getTitle, title);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(readFlag)) {
                queryWrapper.eq(MessageNoticeDO::getReadFlag, readFlag);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(noticeWay)) {
                queryWrapper.eq(MessageNoticeDO::getNoticeWay, noticeWay);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(beginDate) && org.apache.commons.lang.StringUtils.isNotEmpty(endDate)) {
                queryWrapper.between(MessageNoticeDO::getCreateTime, beginDate, endDate);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(sortFlag)) {
                queryWrapper.orderByAsc(MessageNoticeDO::getReadFlag);
                queryWrapper.orderByDesc(MessageNoticeDO::getHomepageType);
                queryWrapper.orderByDesc(MessageNoticeDO::getCreateTime);
            }
            resultPage = messageNoticeMapper.selectPage(page,queryWrapper);
        }
        if (resultPage == null){
            return  resultMap;
        }
        resultPage.getRecords().forEach(messageNotice->{
            String messageNoticeJson = JsonUtils.obj2json(messageNotice);
            Map<String, Object> messageNoticeMap = JsonUtils.json2map(messageNoticeJson);
            resultList.add(messageNoticeMap);

        });
        resultMap.put("records",resultList);
        resultMap.put("current",resultPage.getCurrent());
        resultMap.put("size",resultPage.getSize());
        resultMap.put("totalPages",resultPage.getPages());
        resultMap.put("total",resultPage.getTotal());

        return resultMap;
    }

    @Override
    public List<MessageNoticeDO> listAll() {
        return messageNoticeMapper.selectList();
    }


    public List<MsgContentVO> convertMsg(List<Map> dataList, String msgType) {
        return dataList.stream()
                .filter(data -> msgType.equals(data.get("MSGTYPE")))
                .collect(Collectors.groupingBy(map -> (String) map.get("MSGTYPE")))
                .entrySet().stream()
                .map(entry -> {
                    List<MsgContentVO.MsgItem> items = entry.getValue().stream()
                            .map(map -> {
                                MsgContentVO.MsgItem item = new MsgContentVO.MsgItem();
                                item.setMsgName((String) map.get("MSGNAME"));
                                item.setMsgLink((String) map.get("MSGLINK"));
                                item.setMsgLink((String) map.get("ISREAD"));
                                item.setMsgLink((String) map.get("NOTIFYMETHOD"));
                                return item;
                            })
                            .collect(Collectors.toList());

                    MsgContentVO vo = new MsgContentVO();
                    vo.setMsgType(entry.getKey());
                    vo.setItemList(items);
                    return vo;
                })
                .collect(Collectors.toList());

    }
}
