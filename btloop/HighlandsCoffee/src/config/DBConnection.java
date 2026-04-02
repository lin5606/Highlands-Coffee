package config;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        // 1. Khai báo thông số
        String server = "localhost"; // Dùng localhost để cả nhóm chạy máy nào cũng được
        String user = "coffee";
        String password = "admin";
        String db = "Highlands_Coffee";
        int port = 1433;

        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(user);
            ds.setPassword(password);
            ds.setDatabaseName(db);
            ds.setServerName(server);
            ds.setPortNumber(port);
            ds.setTrustServerCertificate(true);

            return ds.getConnection();
        } catch (SQLException ex) {
            System.err.println("Loi ket noi: " + ex.getMessage());
            return null;
        }
    }
}