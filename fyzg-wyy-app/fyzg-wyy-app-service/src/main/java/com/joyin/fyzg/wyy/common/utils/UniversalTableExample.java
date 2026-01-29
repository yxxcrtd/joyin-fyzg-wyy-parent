package com.joyin.fyzg.wyy.common.utils;

import java.util.*;

/**
 * 通用表格填充示例
 */
public class UniversalTableExample {

    public static void main(String[] args) {
        // 原始富文本表格
        String richText = "<p>这是一条表格的消息</p>\n" +
                "<table style=\"width: 100%;\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">发送人</th>\n" +
                "\t\t\t<th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">机构</th>\n" +
                "\t\t\t<th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">标题</th>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{SENDER}</td>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{ORG}</td>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{TITLE}</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{SENDER}</td>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{ORG}</td>\n" +
                "\t\t\t<td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">#{TITLE}</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>\n" +
                "<p><br></p>";

        // 准备多组数据
        List<Map<String, String>> dataList = createSampleData();

        // 方法1: 使用通用版本
        /*String result1 = UniversalTableFiller.fillTableWithData(richText, dataList);
        System.out.println("=== 通用版本结果 ===");
        System.out.println(result1);*/

        // 方法2: 使用简化通用版本
        String result2 = SimpleUniversalFiller.fillUniversalTable(richText, dataList);
        System.out.println("\n=== 简化通用版本结果 ===");
        System.out.println(result2);
    }

    /**
     * 创建示例数据
     */
    private static List<Map<String, String>> createSampleData() {
        List<Map<String, String>> dataList = new ArrayList<>();

        // 添加多组数据
        dataList.add(createData("张三", "技术部", "月度工作报告"));
        dataList.add(createData("李四", "市场部", "市场分析报告"));
        //dataList.add(createData("王五", "财务部", "财务预算报告"));
        //dataList.add(createData("赵六", "人事部", "招聘计划"));

        return dataList;
    }

    /**
     * 创建数据Map的辅助方法
     */
    private static Map<String, String> createData(String sender, String org, String title) {
        Map<String, String> data = new HashMap<>();
        data.put("SENDER", sender);
        data.put("ORG", org);
        data.put("TITLE", title);
        return data;
    }
}