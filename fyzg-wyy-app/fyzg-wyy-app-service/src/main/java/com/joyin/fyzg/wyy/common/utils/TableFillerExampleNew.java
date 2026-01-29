package com.joyin.fyzg.wyy.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 富文本表格填充示例
 */
public class TableFillerExampleNew {

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

        // 准备填充数据
        Map<String, String> data = new HashMap<>();
        data.put("SENDER", "张三");
        data.put("ORG", "技术部");
        data.put("TITLE", "月度工作报告");

        // 处理表格
        String result = RichTextTableFillerNew.processTable(richText, data);

        // 输出结果
        System.out.println("\n=== 填充后的表格 ===");
        System.out.println(result);
    }
}
