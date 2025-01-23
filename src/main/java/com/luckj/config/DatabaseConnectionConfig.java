package com.luckj.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.luckj.constants.BotConfigConstants;

public class DatabaseConnectionConfig {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(BotConfigConstants.DB_URL);
    }
}