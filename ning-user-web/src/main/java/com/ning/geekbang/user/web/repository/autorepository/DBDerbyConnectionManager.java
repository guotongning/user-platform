package com.ning.geekbang.user.web.repository.autorepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: nicholas
 * @Date: 2021/3/2 20:53
 * @Descreption:
 */
public class DBDerbyConnectionManager {

    public static final String DATABASE_URL = "jdbc:derby:/db/user-platform;create=true";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }


    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
