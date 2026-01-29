package com.joyin.fyzg.wyy.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本表格解析与填充工具类
 */
public class RichTextTableFillerNew {

    /**
     * 解析富文本并填充数据
     */
    public static String fillTableData(String richText, Map<String, String> data) {
        if (richText == null || richText.isEmpty()) {
            return richText;
        }

        // 复制原始文本，避免修改原字符串
        String result = richText;

        // 替换所有占位符
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String placeholder = "#{" + entry.getKey() + "}";
            String value = entry.getValue() != null ? entry.getValue() : "";
            result = result.replace(placeholder, value);
        }

        return result;
    }

    /**
     * 提取表格中的占位符字段
     */
    public static java.util.Set<String> extractPlaceholders(String richText) {
        java.util.Set<String> placeholders = new java.util.HashSet<>();

        Pattern pattern = Pattern.compile("#\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(richText);

        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }

        return placeholders;
    }

    /**
     * 完整的表格处理流程
     */
    public static String processTable(String richText, Map<String, String> data) {
        System.out.println("=== 开始处理表格 ===");

        // 1. 提取占位符
        java.util.Set<String> placeholders = extractPlaceholders(richText);
        System.out.println("提取到的占位符: " + placeholders);

        // 2. 检查数据完整性
        for (String placeholder : placeholders) {
            if (!data.containsKey(placeholder)) {
                System.out.println("警告: 缺少占位符 " + placeholder + " 的数据");
            }
        }

        // 3. 填充数据
        String result = fillTableData(richText, data);

        System.out.println("=== 表格处理完成 ===");
        return result;
    }
}
