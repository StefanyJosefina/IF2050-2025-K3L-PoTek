package id.sti.potek.dao;

import id.sti.potek.model.Ulasan;
import id.sti.potek.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UlasanDAO {

    public void insertUlasan(Ulasan ulasan) {
        String sql = "INSERT INTO ulasan (idUser, idKamar, rating, komentar, tanggal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ulasan.getIdUser());
            stmt.setString(2, ulasan.getIdKamar());
            stmt.setDouble(3, ulasan.getRating());
            stmt.setString(4, ulasan.getKomentar());
            stmt.setString(5, ulasan.getTanggal());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ulasan> getAllByKamar(String idKamar) {
        List<Ulasan> list = new ArrayList<>();
        String sql = "SELECT * FROM ulasan WHERE idKamar = ? ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idKamar);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Ulasan(
                        rs.getInt("idUser"),
                        rs.getString("idKamar"),
                        rs.getDouble("rating"),
                        rs.getString("komentar"),
                        rs.getString("tanggal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public double getAverageRating(String idKamar) {
        String sql = "SELECT AVG(rating) AS avgRating FROM ulasan WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idKamar);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avgRating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getJumlahReview(String idKamar) {
        String sql = "SELECT COUNT(*) AS total FROM ulasan WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idKamar);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}