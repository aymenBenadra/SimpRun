package com.simprun.dao.mysql;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    private static Connection connection;
    private static MySQLConnection instance;

    private MySQLConnection() {
        try {
            Dotenv dotenv = Dotenv.load();
            Class.forName(dotenv.get("DATABASE_DRIVER"));
            connection = DriverManager.getConnection(dotenv.get("DATABASE_URL"),
                    dotenv.get("DATABASE_USERNAME"), dotenv.get("DATABASE_PASSWORD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return connection;
    }
}
