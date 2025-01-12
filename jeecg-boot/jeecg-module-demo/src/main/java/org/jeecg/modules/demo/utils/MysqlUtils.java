package org.jeecg.modules.demo.utils;

import com.alibaba.druid.util.MySqlUtils;

import java.sql.*;

public class MysqlUtils {
    private static final String DB_URL = "jdbc:mysql://47.241.2.70:3306/jeecg-boot";
    private static final String USER = "root";
    private static final String PASS = "f6zx@4qc";

    // 静态方法，用于获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // 注册JDBC驱动（不再需要显式调用，但保留此行代码以供参考）
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用DriverManager类的getConnection方法建立连接
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
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
}
