package com.joyin.fyzg.wyy.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的 Oracle 数据库查询工具类
 */
public class SimpleOracleUtil {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    /**
     * 初始化数据库连接信息
     */
    public  void init(String url, String username, String password) {
        URL = url;
        USERNAME = username;
        PASSWORD = password;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载Oracle驱动失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * 执行查询，返回结果列表
     */
    public  List<Map<String, Object>> query(String sql, Object... params) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }
                    resultList.add(row);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("查询失败: " + e.getMessage(), e);
        }

        return resultList;
    }

    /**
     * 执行更新操作（INSERT/UPDATE/DELETE）
     */
    public static int update(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("更新失败: " + e.getMessage(), e);
        }
    }

    /**
     * 查询单条记录
     */
    public  Map<String, Object> queryOne(String sql, Object... params) {
        List<Map<String, Object>> results = query(sql, params);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 查询单个值
     */
    public  Object querySingle(String sql, Object... params) {
        Map<String, Object> result = queryOne(sql, params);
        return result == null ? null : result.values().iterator().next();
    }
}
