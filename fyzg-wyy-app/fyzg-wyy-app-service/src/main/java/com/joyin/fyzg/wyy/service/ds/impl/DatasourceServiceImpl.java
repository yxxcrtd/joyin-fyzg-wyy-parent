package com.joyin.fyzg.wyy.service.ds.impl;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.wyy.client.DatasourceDfClientService;
import com.joyin.fyzg.wyy.service.ds.DatasourceService;
import com.joyin.fyzg.wyy.service.rbac.ComponentService;
import com.joyin.fyzg.wyy.vo.kanban.DaiBanContentVO;
import com.joyin.fyzg.wyy.vo.kanban.KanbanContentVO4Base;
import com.joyin.fyzg.wyy.vo.kanban.KeFuContentVO;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    @Autowired
    DatasourceDfClientService datasourceDfClientService;
    @Autowired
    ComponentService componentService;

    private static final String GTITLE = "GTITLE";
    private static final String LINK = "LINK";
    private static final String RPT_ID = "RPT_ID";
    private static final String GSORT = "GSORT";
    private static final String TITLE = "TITLE";
    private static final String UNIT = "UNIT";
    private static final String GCOLOR = "GCOLOR";
    private static final String ICON = "ICON";
    private final List<String> kanBanBaseTypes = Arrays.asList("运营提示", "运作指引");
    private final List<String> kanBanKefuType = java.util.Collections.singletonList("客服");
    private final List<String> kanBanMsgType = java.util.Collections.singletonList("消息提醒");
    private final List<String> kanBanDaiBanType = java.util.Collections.singletonList("待办事项");


    @Override
    public <K> List<K> handleDatasourceTableData4App(String rId, String param, String userCode) {

        FeignResponse<List<Map>> datasourceTableData4App = datasourceDfClientService.getDatasourceTableData4App(rId, param);
        FeignResponse datasourceById = datasourceDfClientService.getDatasourceById(rId);

        Map<String, Object> result = (Map<String, Object>) datasourceById.getResult();
        String label = String.valueOf(result.get("name"));
        List<?> convertedList;
        if (kanBanBaseTypes.contains(label)) {
            convertedList = convertBase(datasourceTableData4App.getResult());
        } else if (kanBanKefuType.contains(label)) {
            convertedList = convertKeFu(datasourceTableData4App.getResult());
        } else if (datasourceTableData4App.getResult() instanceof List) {
            return (List) datasourceTableData4App.getResult();
        } else {
            return Collections.emptyList();
        }
        // 安全转换为 List<K>
        @SuppressWarnings("unchecked")
        List<K> resultK = (List<K>) convertedList;
        return resultK;
    }

    @Override
    public List<DaiBanContentVO> handleDatasourceTableData4DaiBan(String rId, String param, String status, String userCode) {
        FeignResponse<List<Map>> datasourceTableData4App = datasourceDfClientService.getDatasourceTableData4App(rId, param);
        return convertDaiBan(datasourceTableData4App.getResult(), status);
    }

    @Override
    public List<MsgContentVO> handleDatasourceTableData4Msg(String rId, String param, String msgType, String userCode) {
        FeignResponse<List<Map>> datasourceTableData4App = datasourceDfClientService.getDatasourceTableData4App(rId, param);
        return convertMsg(datasourceTableData4App.getResult(), msgType);
    }


    public List<KanbanContentVO4Base> convertBase(List<Map> dataList) {
        return dataList.stream()
                .collect(Collectors.groupingBy(map -> (String) map.get(GTITLE)))
                .entrySet().stream()
                .map(this::buildKanbanVO)
                .collect(Collectors.toList());
    }

    public List<KeFuContentVO> convertKeFu(List<Map> dataList) {
        return dataList.stream()
                .map(e -> {
                    KeFuContentVO vo = new KeFuContentVO();
                    vo.setLink(String.valueOf(e.get(LINK)));
                    vo.setGTitle(String.valueOf(e.get(GTITLE)));
                    vo.setTitle(String.valueOf(e.get(TITLE)));
                    vo.setGSort(String.valueOf(e.get(GSORT)));
                    return vo;
                })
                .collect(Collectors.toList());
    }

    public List<DaiBanContentVO> convertDaiBan(List<Map> dataList, String status) {
        return dataList.stream()
                .filter(data -> status.equals(data.get("STATUS")))
                .collect(Collectors.groupingBy(
                        data -> new AbstractMap.SimpleImmutableEntry<>(
                                (String) data.get("MENU"),
                                (String) data.get("STATUS")
                        ),
                        Collectors.mapping(
                                data -> {
                                    DaiBanContentVO.DaiBanItem item = new DaiBanContentVO.DaiBanItem();
                                    item.setItemName((String) data.get("ITEMNAME"));
                                    item.setItemLink((String) data.get("ITEMLINK"));
                                    item.setItemNum((String) data.get("ITEMNUM"));
                                    item.setComtUrl((String)data.get("COMTURL"));
                                    return item;
                                },
                                Collectors.toList()
                        )
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map.Entry<String, String> key = entry.getKey();
                    List<DaiBanContentVO.DaiBanItem> items = entry.getValue();

                    DaiBanContentVO vo = new DaiBanContentVO();
                    vo.setMenu(key.getKey());
                    vo.setStatus(key.getValue());
                    vo.setItemList(items);
                    return vo;
                })
                .collect(Collectors.toList());
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


    private KanbanContentVO4Base buildKanbanVO(Map.Entry<String, List<Map>> entry) {
        KanbanContentVO4Base vo = new KanbanContentVO4Base();
        vo.setGTitle(entry.getKey());

        // 取第一个元素作为默认值
        Map<String, Object> firstMap = entry.getValue().get(0);
        vo.setRptId((String) firstMap.get(RPT_ID));
        vo.setLink((String) firstMap.get(LINK));
        vo.setGSort((String) firstMap.get(GSORT));
        vo.setGColor((String) firstMap.get(GCOLOR));
        vo.setIcon((String) firstMap.get(ICON));

        // 按 TITLE 分组
        List<KanbanContentVO4Base.ChildContent> childContents = entry.getValue().stream()
                .collect(Collectors.groupingBy(map -> (String) map.get("TITLE")))
                .entrySet().stream()
                .map(e -> buildChildContent(e.getValue()))
                .collect(Collectors.toList());

        vo.setGContent(childContents);
        return vo;
    }

    private KanbanContentVO4Base.ChildContent buildChildContent(List<Map> group) {
        KanbanContentVO4Base.ChildContent childContent = new KanbanContentVO4Base.ChildContent();
        childContent.setTitle((String) group.get(0).get("TITLE"));

        List<KanbanContentVO4Base.ChildContent.ChildItem> items = group.stream().map(map -> {
            KanbanContentVO4Base.ChildContent.ChildItem item = new KanbanContentVO4Base.ChildContent.ChildItem();
            item.setCount((String) map.get("GCOUNT"));
            item.setUnit((String) map.get("UNIT"));
            return item;
        }).collect(Collectors.toList());

        childContent.setChildItems(items);
        return childContent;
    }

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key.toUpperCase());
        return val == null ? null : val.toString();
    }


}
