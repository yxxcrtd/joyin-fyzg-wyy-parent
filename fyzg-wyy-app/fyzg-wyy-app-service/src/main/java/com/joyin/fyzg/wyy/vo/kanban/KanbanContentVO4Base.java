package com.joyin.fyzg.wyy.vo.kanban;

import lombok.Data;

import java.util.List;

@Data
public class KanbanContentVO4Base {
    private String rptId;
    private String gTitle;
    private String link;
    private String gSort;
    private String gColor;
    private String icon;
    private List<ChildContent> gContent;

    private String gDesc;

    @Data
    public static class ChildContent {
        private String title;
        private List<ChildItem> childItems;
        @Data
        public static class ChildItem {
            private String count;
            private String unit;
        }
    }
}






