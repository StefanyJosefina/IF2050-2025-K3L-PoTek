package id.sti.potek.dao;


import id.sti.potek.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.Tiket;

public class TiketDAO {
    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        List<Tiket> daftar = new ArrayList<>();
        String sql = "SELECT * FROM tiket WHERE keberangkatan=? AND tujuan=? AND tanggal=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, asal);
            stmt.setString(2, tujuan);
            stmt.setString(3, tanggal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tiket t = new Tiket(
                    rs.getString("idTiket"),
                    rs.getString("keberangkatan"),
                    rs.getString("tujuan"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getInt("harga"),
                    rs.getInt("tersedia_kursi")
                );
                daftar.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }
    public List<Tiket> getAllTiket() {
        List<Tiket> daftar = new ArrayList<>();
        String sql = "SELECT * FROM tiket";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tiket t = new Tiket(
                    rs.getString("idTiket"),
                    rs.getString("keberangkatan"),
                    rs.getString("tujuan"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getInt("harga"),
                    rs.getInt("tersedia_kursi")
                );
                daftar.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }
    public boolean simpanPemesanan(String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                    String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        String sql = "INSERT INTO pesanantiket (idTiket, namaPemesan, noHpPemesan, emailPemesan, " +
                     "namaPenumpang, noHpPenumpang, emailPenumpang, noKursi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idTiket);
            stmt.setString(2, namaPemesan);
            stmt.setString(3, noHpPemesan);
            stmt.setString(4, emailPemesan);
            stmt.setString(5, namaPenumpang);
            stmt.setString(6, noHpPenumpang);
            stmt.setString(7, emailPenumpang);
            stmt.setInt(8, noKursi);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method untuk mengurangi kursi tersedia (BARU)
    public boolean kurangiKursiTersedia(String idTiket) {
        String sql = "UPDATE tiket SET tersedia_kursi = tersedia_kursi - 1 WHERE idTiket = ? AND tersedia_kursi > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, idTiket);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    
    }
}
