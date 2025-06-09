package id.sti.potek.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/potek";
    private static final String DB_USER = "root"; // ganti jika perlu
    private static final String DB_PASS = "katharina.ms29";     // ganti jika ada password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
