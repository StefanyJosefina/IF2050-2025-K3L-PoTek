package id.sti.potek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.Pemesanan;
import id.sti.potek.util.DBConnection;

public class PemesananDAO {
    public boolean simpanPemesanan(Pemesanan p) {
        String sql = "INSERT INTO pesanantiket (idPesananTiket, idUser, idTiket, namaPemesan, noHpPemesan, emailPemesan, namaPenumpang, noHpPenumpang, emailPenumpang, noKursi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Validasi input
            if (p == null || p.getIdPesanan() == null || p.getIdTiket() == null ||
                p.getNamaPemesan() == null || p.getNoHpPemesan() == null ||
                p.getEmailPemesan() == null || p.getNamaPenumpang() == null ||
                p.getNoHpPenumpang() == null || p.getEmailPenumpang() == null ||
                p.getNoKursi() <= 0) {
                throw new IllegalArgumentException("Semua field harus diisi.");
            }
            
            stmt.setString(1, p.getIdPesanan());
            stmt.setString(2, p.getIdUser());        // idUser bisa null untuk guest
            stmt.setString(3, p.getIdTiket());
            stmt.setString(4, p.getNamaPemesan());
            stmt.setString(5, p.getNoHpPemesan());
            stmt.setString(6, p.getEmailPemesan());
            stmt.setString(7, p.getNamaPenumpang());
            stmt.setString(8, p.getNoHpPenumpang());
            stmt.setString(9, p.getEmailPenumpang());
            stmt.setInt(10, p.getNoKursi());

            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method baru untuk mendukung transaksi
    public boolean simpanPemesananWithConnection(Pemesanan p, Connection conn) {
        String sql = "INSERT INTO pesanantiket (idPesananTiket, idUser, idTiket, namaPemesan, noHpPemesan, emailPemesan, namaPenumpang, noHpPenumpang, emailPenumpang, noKursi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Validasi input
            if (p == null || p.getIdPesanan() == null || p.getIdTiket() == null ||
                p.getNamaPemesan() == null || p.getNoHpPemesan() == null ||
                p.getEmailPemesan() == null || p.getNamaPenumpang() == null ||
                p.getNoHpPenumpang() == null || p.getEmailPenumpang() == null ||
                p.getNoKursi() <= 0) {
                throw new IllegalArgumentException("Semua field harus diisi.");
            }

            stmt.setString(1, p.getIdPesanan());
            stmt.setString(2, p.getIdUser());        // idUser bisa null untuk guest
            stmt.setString(3, p.getIdTiket());
            stmt.setString(4, p.getNamaPemesan());
            stmt.setString(5, p.getNoHpPemesan());
            stmt.setString(6, p.getEmailPemesan());
            stmt.setString(7, p.getNamaPenumpang());
            stmt.setString(8, p.getNoHpPenumpang());
            stmt.setString(9, p.getEmailPenumpang());
            stmt.setInt(10, p.getNoKursi());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Integer> getKursiTerbooking(String idTiket) {
        List<Integer> bookedSeats = new ArrayList<>();
        String sql = "SELECT noKursi FROM pesanantiket WHERE idTiket = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idTiket);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getInt("noKursi"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedSeats;
    }
}