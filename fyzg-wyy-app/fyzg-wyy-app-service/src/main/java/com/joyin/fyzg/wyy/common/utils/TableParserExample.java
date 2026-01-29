package com.joyin.fyzg.wyy.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 表格解析使用示例
 */
public class TableParserExample {

    public static void main(String[] args) {
        // 您的富文本表格
        String richText = "<p>这是一条表格的消息</p>\n" +
                "<table style=\\\"width: 100%;\\\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">发送人</th>\n" +
                "\t\t\t<th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">机构</th>\n" +
                "\t\t\t<th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">标题</th>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{SENDER}</td>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{ORG}</td>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{TITLE}</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{SENDER}</td>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{ORG}</td>\n" +
                "\t\t\t<td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">#{TITLE}</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>\n" +
                "<p><br></p>";

        // 解析表格
        TableParseResult result = RichTextTableParser.parseTable(richText);

        System.out.println("=== 表格解析结果 ===");
        System.out.println("表头: " + result.getHeaders());
        System.out.println("数据行数: " + result.getData().size());
        System.out.println("占位符: " + result.getPlaceholders());

        // 打印表格结构
        System.out.println("\n=== 表格结构 ===");
        System.out.println("表头: " + String.join(" | ", result.getHeaders()));
        for (int i = 0; i < result.getData().size(); i++) {
            System.out.println("第 " + (i + 1) + " 行: " + String.join(" | ", result.getData().get(i)));
        }

        // 填充表格数据示例
        System.out.println("\n=== 填充表格数据 ===");
        Map<String, String> placeholderValues = new HashMap<>();
        placeholderValues.put("SENDER", "admin");
        placeholderValues.put("ORG", "joyin");
        placeholderValues.put("TITLE", "测试");

        String filledTable = RichTextTableParser.fillTableData(richText, placeholderValues);
        System.out.println("填充后的表格（部分）:");
        System.out.println(filledTable.substring(0, Math.min(500, filledTable.length())) + "...");
    }
}
