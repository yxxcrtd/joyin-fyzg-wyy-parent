package com.joyin.fyzg.wyy.vo.kanban;

import lombok.Data;

import java.util.List;

@Data
public class DaiBanContentVO {
    /* 菜单 */
    private String menu;
    /* 0-待办，1-已办 */
    private String status;
    private List<DaiBanItem> itemList;

    @Data
    public static class DaiBanItem {
        private String itemName;
        private String itemLink;
        private String itemNum;
        private String comtUrl;
    }
}
