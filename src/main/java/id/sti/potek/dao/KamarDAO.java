package id.sti.potek.dao;

import id.sti.potek.model.Kamar;
import id.sti.potek.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    public List<Kamar> filterKamar(String lokasi, String tanggal) {
        List<Kamar> daftar = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE lokasi = ? AND tanggal = ? AND ketersediaan = true";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lokasi);
            stmt.setString(2, tanggal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Kamar k = new Kamar(
                        rs.getString("idKamar"),
                        rs.getString("lokasi"),
                        rs.getInt("harga"),
                        rs.getBoolean("ketersediaan"),
                        rs.getString("tanggal")
                );
                daftar.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftar;
    }

    public boolean updateKetersediaan(String idKamar, boolean tersedia) {
        String sql = "UPDATE kamar SET ketersediaan = ? WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, tersedia);
            stmt.setString(2, idKamar);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHarga(String idKamar, int hargaBaru) {
        String sql = "UPDATE kamar SET harga = ? WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hargaBaru);
            stmt.setString(2, idKamar);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}