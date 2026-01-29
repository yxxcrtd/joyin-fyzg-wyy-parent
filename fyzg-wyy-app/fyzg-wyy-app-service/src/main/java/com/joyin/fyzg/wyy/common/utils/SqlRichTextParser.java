package com.joyin.fyzg.wyy.common.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import javax.sql.DataSource;

/**
 * SQL 富文本动态解析器
 * 专门处理包含 #{...} 占位符的 SQL 模板
 */
@Service
public class SqlRichTextParser {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("#\\{([^}]+)\\}");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String URL = "jdbc:oracle:thin:@//192.168.88.129:1521/wfw";
    private static final String USERNAME = "dfzqwyykf";
    private static final String PASSWORD = "joyin123";



    /**
     * 解析 SQL 模板并执行查询
     */
    public SqlParseResult parseAndExecute(String sqlTemplate, Map<String, Object> parameters) {
        SqlParseResult result = new SqlParseResult();
        result.setOriginalSql(sqlTemplate);
        result.setInputParameters(parameters);

        try {
            // 1. 解析 SQL 模板中的占位符
            List<SqlPlaceholder> placeholders = parseSqlPlaceholders(sqlTemplate);
            result.setPlaceholders(placeholders);

            // 2. 构建可执行的 SQL
            String executableSql = buildExecutableSql(sqlTemplate, placeholders);
            result.setExecutableSql(executableSql);

            // 3. 执行 SQL 查询
            Map<String, Object> queryResult = executeQuery(executableSql, parameters, placeholders);
            result.setQueryResult(queryResult);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            throw new RuntimeException("SQL 解析执行失败: " + e.getMessage(), e);
        }

        return result;
    }

    /**
     * 解析 SQL 中的占位符
     */
    private List<SqlPlaceholder> parseSqlPlaceholders(String sqlTemplate) {
        List<SqlPlaceholder> placeholders = new ArrayList<>();
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(sqlTemplate);

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            int start = matcher.start();
            int end = matcher.end();

            SqlPlaceholder sqlPlaceholder = new SqlPlaceholder(placeholder, start, end);
            placeholders.add(sqlPlaceholder);
        }

        return placeholders;
    }

    /**
     * 构建可执行的 SQL
     */
    private String buildExecutableSql(String sqlTemplate, List<SqlPlaceholder> placeholders) {
        if (placeholders.isEmpty()) {
            return sqlTemplate;
        }

        // 从后往前替换，避免位置变化
        List<SqlPlaceholder> reversed = new ArrayList<>(placeholders);
        reversed.sort((a, b) -> Integer.compare(b.getStart(), a.getStart()));

        String result = sqlTemplate;
        for (SqlPlaceholder placeholder : reversed) {
            // 在 SELECT 部分的占位符替换为实际的字段名
            // 在 WHERE 条件的占位符替换为参数占位符 ?
            String replacement = determineReplacement(placeholder, result);
            result = result.substring(0, placeholder.getStart())
                    + replacement
                    + result.substring(placeholder.getEnd());
        }

        return result;
    }

    /**
     * 确定占位符的替换内容
     */
    private String determineReplacement(SqlPlaceholder placeholder, String sql) {
        // 检查占位符在 SQL 中的位置
        String sqlBefore = sql.substring(0, placeholder.getStart()).toUpperCase();

        if (sqlBefore.contains("SELECT") && !sqlBefore.contains("WHERE")) {
            // 在 SELECT 部分 - 替换为字段名
            return placeholder.getName();
        } else {
            // 在 WHERE 条件部分 - 替换为参数占位符
            return "?";
        }
    }

    /**
     * 执行查询
     */
    private Map<String, Object> executeQuery(String executableSql,
                                             Map<String, Object> parameters,
                                             List<SqlPlaceholder> placeholders) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(executableSql)) {

            // 设置查询参数（只设置 WHERE 条件中的参数）
            setQueryParameters(pstmt, parameters, placeholders, executableSql);

            try (ResultSet rs = pstmt.executeQuery()) {
                return extractResultData(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("SQL 执行失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置查询参数
     */
    private void setQueryParameters(PreparedStatement pstmt,
                                    Map<String, Object> parameters,
                                    List<SqlPlaceholder> placeholders,
                                    String executableSql) throws SQLException {

        // 提取 WHERE 条件中的参数占位符对应的参数名
        List<String> whereParamNames = extractWhereParameterNames(placeholders, executableSql);

        int paramIndex = 1;
        for (String paramName : whereParamNames) {
            Object value = parameters.get(paramName);
            if (value == null) {
                throw new RuntimeException("缺少必要的参数: " + paramName);
            }
            pstmt.setObject(paramIndex++, value);
        }
    }

    /**
     * 提取 WHERE 条件中的参数名
     */
    private List<String> extractWhereParameterNames(List<SqlPlaceholder> placeholders, String sql) {
        List<String> whereParams = new ArrayList<>();
        String sqlUpper = sql.toUpperCase();
        int whereIndex = sqlUpper.indexOf("WHERE");

        if (whereIndex == -1) {
            return whereParams;
        }

        for (SqlPlaceholder placeholder : placeholders) {
            // 检查占位符是否在 WHERE 之后
            if (placeholder.getStart() > whereIndex) {
                whereParams.add(placeholder.getName());
            }
        }

        return whereParams;
    }

    /**
     * 提取结果数据
     */
    private Map<String, Object> extractResultData(ResultSet rs) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        if (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                result.put(columnName, value);
            }
        } else {
            throw new RuntimeException("查询未返回任何结果");
        }

        return result;
    }

    /**
     * 专门处理您提供的 SQL 模板的方法
     */
    public Map<String, Object> parseMarginNoticeSql(String sender, String noticeWay) {
        String sqlTemplate = "SELECT #{SENDER},#{TOTAL},#{DAYS} FROM SYS_MESSAGENOTICE WHERE SENDER = #{SENDER} AND NOTICE_WAY = #{NOTICE_WAY}";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SENDER", sender);
        parameters.put("NOTICE_WAY", noticeWay);

        SqlParseResult result = parseAndExecute(sqlTemplate, parameters);

        if (result.isSuccess()) {
            return result.getQueryResult();
        } else {
            throw new RuntimeException("解析执行失败: " + result.getErrorMessage());
        }
    }
}

/**
 * SQL 占位符信息
 */
@Data
class SqlPlaceholder {
    private String name;     // 占位符名称（如 SENDER, TOTAL）
    private int start;       // 起始位置
    private int end;         // 结束位置

    public SqlPlaceholder(String name, int start, int end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }
}

/**
 * SQL 解析结果
 */
@Data
class SqlParseResult {
    private String originalSql;          // 原始 SQL 模板
    private String executableSql;        // 可执行的 SQL
    private List<SqlPlaceholder> placeholders; // 占位符列表
    private Map<String, Object> inputParameters; // 输入参数
    private Map<String, Object> queryResult;     // 查询结果
    private boolean success;             // 是否成功
    private String errorMessage;         // 错误信息

    public SqlParseResult() {
        this.placeholders = new ArrayList<>();
        this.inputParameters = new HashMap<>();
        this.queryResult = new HashMap<>();
    }
}
