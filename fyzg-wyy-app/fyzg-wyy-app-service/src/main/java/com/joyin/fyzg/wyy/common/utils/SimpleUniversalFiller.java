package com.joyin.fyzg.wyy.common.utils;

import java.util.*;

/**
 * 简化通用表格填充工具
 */
public class SimpleUniversalFiller {

    /**
     * 通用表格填充方法
     */
    public static String fillUniversalTable(String richText, List<Map<String, String>> dataList) {
        if (richText == null || dataList == null || dataList.isEmpty()) {
            return richText;
        }

        // 提取表头部分
        String header = extractTableHeader(richText);

        // 提取表格结尾
        String footer = extractTableFooter(richText);

        // 提取数据行模板
        String rowTemplate = extractDataRowTemplate(richText);

        // 生成数据行
        StringBuilder dataRows = new StringBuilder();
        for (Map<String, String> data : dataList) {
            String filledRow = fillTemplateRow(rowTemplate, data);
            dataRows.append(filledRow).append("\n");
        }

        // 组合结果
        return header + "\n" + dataRows.toString() + footer;
    }

    /**
     * 提取表头
     */
    private static String extractTableHeader(String richText) {
        int firstTrEnd = richText.indexOf("</tr>");
        if (firstTrEnd != -1) {
            return richText.substring(0, firstTrEnd + 5);
        }
        return "";
    }

    /**
     * 提取表格结尾
     */
    private static String extractTableFooter(String richText) {
        int tbodyEnd = richText.indexOf("</tbody>");
        if (tbodyEnd != -1) {
            return richText.substring(tbodyEnd);
        }
        return "";
    }

    /**
     * 提取数据行模板
     */
    private static String extractDataRowTemplate(String richText) {
        // 找到第一个数据行（跳过表头）
        int firstTrEnd = richText.indexOf("</tr>");
        if (firstTrEnd == -1) return "";

        String afterHeader = richText.substring(firstTrEnd + 5);
        int secondTrStart = afterHeader.indexOf("<tr>");
        if (secondTrStart == -1) return "";

        int secondTrEnd = afterHeader.indexOf("</tr>", secondTrStart);
        if (secondTrEnd == -1) return "";

        return afterHeader.substring(secondTrStart, secondTrEnd + 5);
    }

    /**
     * 填充模板行
     */
    private static String fillTemplateRow(String template, Map<String, String> data) {
        String result = template;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String placeholder = "#{" + entry.getKey() + "}";
            String value = entry.getValue() != null ? entry.getValue() : "";
            result = result.replace(placeholder, value);
        }
        return result;
    }
}
