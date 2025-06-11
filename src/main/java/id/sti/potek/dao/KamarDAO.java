package id.sti.potek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.Kamar;
import id.sti.potek.util.DBConnection;

public class KamarDAO {

    public List<Kamar> filterKamar(String lokasi) {
        List<Kamar> daftar = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE lokasi = ? AND tersedia > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lokasi);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Kamar k = new Kamar(
                        rs.getString("idKamar"),
                        rs.getString("namaHotel"),
                        rs.getString("lokasi"),
                        rs.getInt("harga"),
                        rs.getInt("tersedia"),
                        rs.getInt("jumlah_kamar"),
                        rs.getString("tipeKamar")
                );
                daftar.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftar;
    }

    public boolean updateKetersediaan(String idKamar, int tersediaBaru) {
        String sql = "UPDATE kamar SET tersedia = ? WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tersediaBaru);
            stmt.setString(2, idKamar);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllLokasi() {
        List<String> kotaList = new ArrayList<>();
        String sql = "SELECT DISTINCT lokasi FROM kamar";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                kotaList.add(rs.getString("lokasi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kotaList;
    }

    public List<Kamar> cariKamarTersedia(String lokasi, String tipeKamar, int jumlahKamar) {
        List<Kamar> daftar = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE lokasi = ? AND tipeKamar = ? AND tersedia >= ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lokasi);
            stmt.setString(2, tipeKamar);
            stmt.setInt(3, jumlahKamar);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Kamar k = new Kamar(
                        rs.getString("idKamar"),
                        rs.getString("namaHotel"),
                        rs.getString("lokasi"),
                        rs.getInt("harga"),
                        rs.getInt("tersedia"),
                        rs.getInt("jumlah_kamar"),
                        rs.getString("tipeKamar")
                );
                daftar.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
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