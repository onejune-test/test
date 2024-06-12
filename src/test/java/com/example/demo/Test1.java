package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 描述
 *
 * @author 王俊
 */
public class Test1 {
    public static void main(String[] args) {
        // 数据库URL，格式通常为：jdbc:goldendb://hostname:port/databaseName
        String jdbcUrl = "jdbc:mysql://10.1.19.42:8880/analysisdb";
        // 数据库用户名和密码
        String username = "dhmp";
        String password = "sdh@246!";

        try {
            // 加载GoldenDB JDBC驱动
            Class<?> driverClass = Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            System.out.println("Connected to GoldenDB successfully!");

            // 在此处编写你的数据库操作代码
            ResultSet showDatabases = conn.createStatement().executeQuery("show tables ");
            while (showDatabases.next()){
                String string = showDatabases.getString(1);
                System.out.println(string);
            }

            // 关闭连接
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("GoldenDB JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to GoldenDB failed!");
            e.printStackTrace();
        }
    }
}
