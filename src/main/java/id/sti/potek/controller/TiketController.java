package id.sti.potek.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import id.sti.potek.model.Tiket;
import id.sti.potek.model.Pemesanan;
import id.sti.potek.dao.PemesananDAO;
import id.sti.potek.dao.TiketDAO;
import id.sti.potek.service.TiketService;
import id.sti.potek.util.DBConnection;

public class TiketController {
    private final TiketService tiketService = new TiketService();
    private final PemesananDAO pemesananDAO = new PemesananDAO();
    private final TiketDAO tiketDAO = new TiketDAO();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        return tiketService.cariTiket(asal, tujuan, tanggal);
    }
    public List<Tiket> getAllTiket() {
        return tiketService.getAllTiket();
    }
    public boolean simpanPemesanan(String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                   String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        return simpanPemesanan(null, idTiket, namaPemesan, noHpPemesan, emailPemesan, 
                              namaPenumpang, noHpPenumpang, emailPenumpang, noKursi);
    }

    // Method utama dengan idUser dan transaksi database
    public boolean simpanPemesanan(String idUser, String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                   String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        Connection conn = null;
        try {
            // Mulai transaksi
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Generate unique ID untuk pemesanan
            String idPesanan = "PT" + System.currentTimeMillis();
            
            // Buat object Pemesanan
            Pemesanan pemesanan = new Pemesanan();
            pemesanan.setIdPesanan(idPesanan);
            pemesanan.setIdUser(idUser);
            pemesanan.setIdTiket(idTiket);
            pemesanan.setNamaPemesan(namaPemesan);
            pemesanan.setNoHpPemesan(noHpPemesan);
            pemesanan.setEmailPemesan(emailPemesan);
            pemesanan.setNamaPenumpang(namaPenumpang);
            pemesanan.setNoHpPenumpang(noHpPenumpang);
            pemesanan.setEmailPenumpang(emailPenumpang);
            pemesanan.setNoKursi(noKursi);
            
            // 1. Simpan pemesanan menggunakan DAO dengan connection yang sama
            boolean pemesananBerhasil = pemesananDAO.simpanPemesananWithConnection(pemesanan, conn);
            if (!pemesananBerhasil) {
                conn.rollback();
                return false;
            }
            
            // 2. Kurangi kursi tersedia
            String updateKursiSql = "UPDATE tiket SET tersedia_kursi = tersedia_kursi - 1 WHERE idTiket = ? AND tersedia_kursi > 0";
            try (PreparedStatement stmt = conn.prepareStatement(updateKursiSql)) {
                stmt.setString(1, idTiket);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false; // Kursi sudah habis atau idTiket tidak valid
                }
            }
            
            // Commit transaksi jika semua berhasil
            conn.commit();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Integer> getKursiTerbooking(String idTiket) {
        return pemesananDAO.getKursiTerbooking(idTiket);
    }


}
