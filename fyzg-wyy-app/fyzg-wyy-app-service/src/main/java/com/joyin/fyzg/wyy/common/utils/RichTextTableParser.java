package com.joyin.fyzg.wyy.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

/**
 * 富文本表格解析工具类
 */
public class RichTextTableParser {

    /**
     * 解析富文本表格，返回表头和数据
     */
    public static TableParseResult parseTable(String richText) {
        TableParseResult result = new TableParseResult();

        try {
            Document doc = Jsoup.parse(richText);
            Element table = doc.select("table").first();

            if (table == null) {
                throw new IllegalArgumentException("未找到表格元素");
            }

            // 解析表头
            parseTableHeaders(table, result);

            // 解析表格数据
            parseTableData(table, result);

        } catch (Exception e) {
            throw new RuntimeException("解析表格失败: " + e.getMessage(), e);
        }

        return result;
    }

    /**
     * 解析表头
     */
    private static void parseTableHeaders(Element table, TableParseResult result) {
        Elements headerRows = table.select("tr:has(th)");
        if (headerRows.isEmpty()) {
            throw new IllegalArgumentException("表格没有表头行");
        }

        // 取第一行作为表头
        Element headerRow = headerRows.first();
        Elements headerCells = headerRow.select("th");

        List<String> headers = new ArrayList<>();
        for (Element headerCell : headerCells) {
            String headerText = headerCell.text().trim();
            headers.add(headerText);
        }

        result.setHeaders(headers);
    }

    /**
     * 解析表格数据
     */
    private static void parseTableData(Element table, TableParseResult result) {
        Elements dataRows = table.select("tr:has(td)");
        List<List<String>> data = new ArrayList<>();
        Set<String> placeholders = new HashSet<>();

        for (Element dataRow : dataRows) {
            Elements dataCells = dataRow.select("td");
            List<String> rowData = new ArrayList<>();

            for (Element dataCell : dataCells) {
                String cellContent = dataCell.html().trim();
                rowData.add(cellContent);

                // 提取占位符
                extractPlaceholders(cellContent, placeholders);
            }

            data.add(rowData);
        }

        result.setData(data);
        result.setPlaceholders(new ArrayList<>(placeholders));
    }

    /**
     * 提取占位符
     */
    private static void extractPlaceholders(String text, Set<String> placeholders) {
        // 匹配 #{...} 格式的占位符
        String pattern = "#\\{([^}]+)\\}";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(text);

        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }
    }

    /**
     * 填充表格数据
     */
    public static String fillTableData(String richText, Map<String, String> placeholderValues) {
        Document doc = Jsoup.parse(richText);
        Elements dataCells = doc.select("td");

        for (Element dataCell : dataCells) {
            String cellContent = dataCell.html();

            // 替换所有占位符
            for (Map.Entry<String, String> entry : placeholderValues.entrySet()) {
                String placeholder = "#{" + entry.getKey() + "}";
                String value = entry.getValue();
                cellContent = cellContent.replace(placeholder, value);
            }

            dataCell.html(cellContent);
        }

        return doc.html();
    }
}

/**
 * 表格解析结果
 */
class TableParseResult {
    private List<String> headers;           // 表头列表
    private List<List<String>> data;        // 数据行
    private List<String> placeholders;      // 占位符列表

    public TableParseResult() {
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
        this.placeholders = new ArrayList<>();
    }

    // Getters and Setters
    public List<String> getHeaders() { return headers; }
    public void setHeaders(List<String> headers) { this.headers = headers; }

    public List<List<String>> getData() { return data; }
    public void setData(List<List<String>> data) { this.data = data; }

    public List<String> getPlaceholders() { return placeholders; }
    public void setPlaceholders(List<String> placeholders) { this.placeholders = placeholders; }

    @Override
    public String toString() {
        return String.format("TableParseResult{headers=%s, data=%s, placeholders=%s}",
                headers, data, placeholders);
    }
}
