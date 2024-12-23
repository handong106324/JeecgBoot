package org.jeecg.modules.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jeecg-boot";
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
}
