package id.sti.potek.dao;

import id.sti.potek.model.PemesananHotel;
import id.sti.potek.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Date;

public class PemesananHotelDAO {

    public boolean simpanPemesanan(PemesananHotel p) {
        String sql = "INSERT INTO pemesanan (" +
                "id_kamar, tgl_checkin, tgl_checkout, jumlah_kamar, jumlah_tamu, total_harga, " +
                "nama_pemesan, no_hp_pemesan, email_pemesan) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getIdKamar());
            stmt.setDate(2, Date.valueOf(p.getTanggalCheckIn()));
            stmt.setDate(3, Date.valueOf(p.getTanggalCheckOut()));
            stmt.setInt(4, p.getJumlahKamar());
            stmt.setInt(5, p.getJumlahTamu());
            stmt.setInt(6, p.getTotalHarga());
            stmt.setString(7, p.getNamaPemesan());
            stmt.setString(8, p.getNoHpPemesan());
            stmt.setString(9, p.getEmailPemesan());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PemesananHotel> getPemesananByEmail(String email) {
        List<PemesananHotel> list = new ArrayList<>();
        String sql = "SELECT * FROM pemesanan WHERE email_pemesan = ? AND id_kamar IS NOT NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PemesananHotel p = new PemesananHotel();
                p.setIdPesanan(rs.getString("id_pesanan"));
                p.setIdKamar(rs.getString("id_kamar"));
                p.setTanggalCheckIn(rs.getDate("tgl_checkin").toLocalDate());
                p.setTanggalCheckOut(rs.getDate("tgl_checkout").toLocalDate());
                p.setJumlahKamar(rs.getInt("jumlah_kamar"));
                p.setJumlahTamu(rs.getInt("jumlah_tamu"));
                p.setTotalHarga(rs.getInt("total_harga"));
                p.setNamaPemesan(rs.getString("nama_pemesan"));
                p.setNoHpPemesan(rs.getString("no_hp_pemesan"));
                p.setEmailPemesan(rs.getString("email_pemesan"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}