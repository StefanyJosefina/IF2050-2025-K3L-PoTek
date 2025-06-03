package id.sti.potek.dao;

import id.sti.potek.model.Pemesanan;
import id.sti.potek.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PemesananDAO {
    public boolean simpanPemesanan(Pemesanan p) {
        String sql = "INSERT INTO pemesanan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getIdPesanan());
            stmt.setString(2, p.getIdTiket());
            stmt.setString(3, p.getNamaPemesan());
            stmt.setString(4, p.getNoHpPemesan());
            stmt.setString(5, p.getEmailPemesan());
            stmt.setString(6, p.getNamaPenumpang());
            stmt.setString(7, p.getNoHpPenumpang());
            stmt.setString(8, p.getEmailPenumpang());
            stmt.setInt(9, p.getNoKursi());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Integer> getKursiTerbooking(String idTiket) {
        List<Integer> bookedSeats = new ArrayList<>();
        String sql = "SELECT no_kursi FROM pemesanan WHERE id_tiket = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idTiket);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getInt("no_kursi"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedSeats;
    }

}

