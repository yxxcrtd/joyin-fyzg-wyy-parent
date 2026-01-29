package com.joyin.fyzg.wyy.vo.kanban;

import lombok.Data;

import java.util.List;

@Data
public class MsgContentVO {
    /* 消息类型  0-消息,  1-提醒 */
    private String msgType;
    private List<MsgItem> itemList;
    @Data
    public static class MsgItem {
        private String msgName;
        private String msgLink;
        private String isRead; //是否已读 0-未读, 1-已读
        private String notifyMethod; //提醒方式  NOTIFY 、MAIL 、MESSAGE
    }
}
