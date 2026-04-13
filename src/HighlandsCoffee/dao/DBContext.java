package HighlandsCoffee.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private final static String URL = "jdbc:sqlserver://localhost:1433;databaseName=YourDB;encrypt=false";
    private final static String USER = "coffee";
    private final static String PASSWORD = "admin";

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}