package highlandscoffee.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=YourDB;encrypt=false";
    private final String USER = "coffee";
    private final String PASSWORD = "admin";

    public Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}