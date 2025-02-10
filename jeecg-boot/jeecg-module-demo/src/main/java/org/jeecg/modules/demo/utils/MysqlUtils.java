package org.jeecg.modules.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.demo.test.entity.WhaleTranctionsHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtils {
    private static final String DB_URL = "jdbc:mysql://47.241.2.70:3306/jeecg-boot";
    private static final String USER = "root";
    private static final String PASS = "f6zx@4qc";

    // 静态方法，用于获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(ResultSet resultSet, Statement preparedStatement, Connection connection) {
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (null != preparedStatement) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static <T> List<T> queryList(String sql, Class<T> clazz) {
        Connection connection = MysqlUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // do something
                JSONObject jsonObject = new JSONObject();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    jsonObject.put(columnName, resultSet.getObject(i));
                }

                list.add(jsonObject.toJavaObject(clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlUtils.close(resultSet, preparedStatement, connection);
        }
        return list;
    }

    public static void batchUpdate(List<String> sqls) {
        Connection connection = MysqlUtils.getConnection();
        Statement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.createStatement();
            for (int i = 0; i < sqls.size(); i++) {
                preparedStatement.addBatch(sqls.get(i));
                if (i % 50 == 0) {
                    preparedStatement.executeBatch();
                    connection.commit();
                    System.out.println(i + " commit");
                }
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlUtils.close(null, preparedStatement, connection);
        }
    }
}
