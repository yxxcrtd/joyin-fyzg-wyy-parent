package com.joyin.fyzg.wyy.service.ds;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.vo.kanban.DaiBanContentVO;
import com.joyin.fyzg.wyy.vo.kanban.KanbanContentVO4Base;
import com.joyin.fyzg.wyy.vo.kanban.MsgContentVO;

import java.util.List;
import java.util.Map;

public interface DatasourceService {

    <K> List<K> handleDatasourceTableData4App(String rId, String param, String userCode);


    List<DaiBanContentVO> handleDatasourceTableData4DaiBan(String rId, String param, String status , String userCode);

    List<MsgContentVO> handleDatasourceTableData4Msg(String rId, String param, String msgType , String userCode);

}

