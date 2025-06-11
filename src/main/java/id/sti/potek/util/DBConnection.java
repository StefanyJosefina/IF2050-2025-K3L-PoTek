package id.sti.potek.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\brams\\OneDrive\\Dokumen\\Nonik\\DRPL\\Tubes\\IF2050-2025-K3L-PoTek\\src\\main\\resources\\db\\potek_database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
