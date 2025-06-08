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
        String sql = "INSERT INTO pemesanan (" +
                "id_tiket, no_kursi, " +
                "id_kamar, tgl_checkin, tgl_checkout, jumlah_kamar, jumlah_tamu, total_harga, " +
                "nama_pemesan, no_hp_pemesan, email_pemesan) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getIdTiket()); // null jika bukan transportasi

            if (p.getNoKursi() >= 0) {
                stmt.setInt(2, p.getNoKursi());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setString(3, p.getIdKamar()); // null jika bukan penginapan

            if (p.getTanggalCheckIn() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(p.getTanggalCheckIn()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            if (p.getTanggalCheckOut() != null) {
                stmt.setDate(5, java.sql.Date.valueOf(p.getTanggalCheckOut()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setInt(6, p.getJumlahKamar());
            stmt.setInt(7, p.getJumlahTamu());
            stmt.setInt(8, p.getTotalHarga());

            stmt.setString(9, p.getNamaPemesan());
            stmt.setString(10, p.getNoHpPemesan());
            stmt.setString(11, p.getEmailPemesan());

            return stmt.executeUpdate() > 0;
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

    public List<Pemesanan> getPemesananByEmail(String email) {
        List<Pemesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM pemesanan WHERE email_pemesan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pemesanan p = new Pemesanan();
                p.setIdPesanan(rs.getString("id_pesanan"));
                p.setIdTiket(rs.getString("id_tiket"));
                p.setNoKursi(rs.getInt("no_kursi"));
                p.setIdKamar(rs.getString("id_kamar"));

                java.sql.Date tglIn = rs.getDate("tgl_checkin");
                java.sql.Date tglOut = rs.getDate("tgl_checkout");
                if (tglIn != null) p.setTanggalCheckIn(tglIn.toLocalDate());
                if (tglOut != null) p.setTanggalCheckOut(tglOut.toLocalDate());

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