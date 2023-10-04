package com.easybank.shared.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/easybank";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "6EZHWJ5URS";

    // Private constructor to prevent direct instantiation
    private DatabaseConnection() {

    }

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            try{
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found");
            }
        }
        return connection;
    }

    public static void closeConnection() throws  SQLException {
        if(connection != null && !connection.isClosed()) connection.close();
    }
}
