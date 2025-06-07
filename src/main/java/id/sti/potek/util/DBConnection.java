package id.sti.potek.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/ulasan_dummy.db";

    static {
        try {
            Connection conn = getConnection();
            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS ulasan (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    idUser INTEGER,
                    idKamar TEXT,
                    rating REAL,
                    komentar TEXT,
                    tanggal TEXT
                )
            """);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}