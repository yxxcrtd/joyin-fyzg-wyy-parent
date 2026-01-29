package com.joyin.fyzg.wyy.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 富文本解析测试
 */
public class SqlRichTextParserTest {

    private SqlRichTextParser sqlParser;

    public static void main(String[] args) {
        String sqlTemplate = "SELECT SENDER,NOTICE_WAY,TYPE FROM SYS_MESSAGENOTICE WHERE SENDER = #{SENDER} AND NOTICE_WAY = #{NOTICE_WAY}";

        String template = "#{SENDER}你好，您的保证金低于#{TOTAL}元,将于#{DAYS}天后自动平仓。";

        Map<String, Object> params = new HashMap<>();
        params.put("SENDER", "admin");
        params.put("NOTICE_WAY", "1,2");

        SqlRichTextParser sqlRichTextParser = new SqlRichTextParser();
        SqlParseResult result = sqlRichTextParser.parseAndExecute(sqlTemplate, params);
        System.out.println("原始 SQL: " + result.getOriginalSql());
        System.out.println("可执行 SQL: " + result.getExecutableSql());
        System.out.println("查询结果: " + result.getQueryResult());

        Map<String, String> data = new HashMap<>();
        data.put("SENDER", result.getQueryResult().get("SENDER").toString());
        data.put("TOTAL", result.getQueryResult().get("NOTICE_WAY").toString());
        data.put("DAYS", result.getQueryResult().get("TYPE").toString());
        System.out.println("原始的发送信息"+template);

        // 方式1：使用正则表达式（更灵活）
        String result1 = parseTemplate(template, data);
        System.out.println("富文本替换后的发送信息: " + result1);
    }

    /**
     * 使用正则表达式替换模板中的占位符
     */
    public static String parseTemplate(String template, Map<String, String> data) {
        Pattern pattern = Pattern.compile("#\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1); // 提取占位符中的key
            String replacement = data.getOrDefault(key, matcher.group()); // 如果找不到key，保留原占位符
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public void testOriginalSqlTemplate() {
        // 测试您提供的原始 SQL 模板
        String sqlTemplate = "SELECT #{SENDER},#{TOTAL},#{DAYS} FROM SYS_MESSAGENOTICE WHERE SENDER = #{SENDER} AND NOTICE_WAY = #{NOTICE_WAY}";

        Map<String, Object> params = new HashMap<>();
        params.put("SENDER", "admin");
        params.put("NOTICE_WAY", "1,2");


        SqlParseResult result = sqlParser.parseAndExecute(sqlTemplate, params);
        System.out.println("原始 SQL: " + result.getOriginalSql());
        System.out.println("可执行 SQL: " + result.getExecutableSql());
        System.out.println("查询结果: " + result.getQueryResult());
    }

    public void testComplexSqlTemplate() {
        // 测试更复杂的 SQL 模板
        String sqlTemplate = "SELECT #{USER_NAME}, #{ACCOUNT_BALANCE}, #{CREDIT_LIMIT} " +
                "FROM CUSTOMER_ACCOUNTS " +
                "WHERE USER_ID = #{USER_ID} " +
                "AND STATUS = #{STATUS} " +
                "AND CREATE_DATE >= #{START_DATE}";

        Map<String, Object> params = new HashMap<>();
        params.put("USER_ID", 12345);
        params.put("STATUS", "ACTIVE");
        params.put("START_DATE", "2024-01-01");

        SqlParseResult result = sqlParser.parseAndExecute(sqlTemplate, params);


        System.out.println("复杂 SQL 解析结果:");
        System.out.println("可执行 SQL: " + result.getExecutableSql());
    }
}
