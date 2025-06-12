package id.sti.potek.dao;

import id.sti.potek.model.Ulasan;
import id.sti.potek.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UlasanDAO {

    public void insertUlasan(Ulasan ulasan) {
        String sql = "INSERT INTO ulasan (idUlasan, idUser, idKamar, rating, komentar) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Generate ID unik yang lebih pendek untuk field varchar(10)
            String idUlasan = generateUlasanId();

            stmt.setString(1, idUlasan);
            stmt.setString(2, "U" + String.format("%03d", ulasan.getIdUser())); // Format U001, U002, dst
            stmt.setString(3, ulasan.getIdKamar());
            stmt.setInt(4, (int) ulasan.getRating()); // Database menggunakan INT, bukan DOUBLE
            stmt.setString(5, ulasan.getKomentar());
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Ulasan berhasil disimpan. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Error saat menyimpan ulasan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Ulasan> getAllByKamar(String idKamar) {
        List<Ulasan> list = new ArrayList<>();
        String sql = "SELECT u.rating, u.komentar, us.nama FROM ulasan u JOIN user us ON u.idUser = us.idUser WHERE u.idKamar = ? ORDER BY u.rating DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idKamar);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Ulasan(
                        rs.getString("nama"),
                        idKamar,
                        rs.getInt("rating"), // Database menggunakan INT
                        rs.getString("komentar")
                ));
            }
            
            System.out.println("Total ulasan ditemukan untuk kamar " + idKamar + ": " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil ulasan: " + e.getMessage());
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
    
    private String generateUlasanId() {
        // Generate ID dengan format ULxxx untuk memenuhi constraint varchar(10)
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM ulasan")) {
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return "UL" + String.format("%03d", count);
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
        }
        
        // Fallback jika query gagal
        return "UL" + String.format("%03d", (int)(Math.random() * 999) + 1);
    }
}