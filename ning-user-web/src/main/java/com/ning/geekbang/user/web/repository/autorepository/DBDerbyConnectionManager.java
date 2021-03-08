package com.ning.geekbang.user.web.repository.autorepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/3/2 20:53
 * @Descreption:
 */
public class DBDerbyConnectionManager {

    private static final Logger logger = Logger.getLogger(DBDerbyConnectionManager.class.getName());


    @Resource(name = "jdbc/UserPlatformDB")
    private DataSource dataSource;

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        if (connection != null) {
            logger.info("connection = " + connection.toString());
        }
        return connection;
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
