package id.sti.potek.test;

import id.sti.potek.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Koneksi ke database BERHASIL");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            System.out.println("📄 Data dari tabel `user`:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("idUser") + ": " + rs.getString("nama") + " | " + rs.getString("email"));
            }

        } catch (Exception e) {
            System.out.println("❌ Gagal konek database:");
            e.printStackTrace();
        }
    }
}
