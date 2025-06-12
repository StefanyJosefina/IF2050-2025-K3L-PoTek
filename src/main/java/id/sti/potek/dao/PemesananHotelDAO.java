package id.sti.potek.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.PemesananHotel;
import id.sti.potek.util.DBConnection;

public class PemesananHotelDAO {

    public boolean simpanPemesanan(PemesananHotel p) {
        String sql = "INSERT INTO pesanankamar (" +
                "idPesananKamar, idKamar, idUser, tanggalCheckIn, tanggalCheckOut, " +
                "namaPemesan, noHpPemesan, emailPemesan, jumlahKamar, jumlahTamu, totalHarga) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String lastIdQuery = "SELECT idPesananKamar FROM pesanankamar ORDER BY idPesananKamar DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(lastIdQuery);
            int nextId = 1;
            if (rs.next()) {
                String lastId = rs.getString(1); 
                nextId = Integer.parseInt(lastId.substring(2)) + 1;
            }
            String idPesanan = String.format("PK%03d", nextId);

            stmt.setString(1, idPesanan);
            stmt.setString(2, p.getIdKamar());
            stmt.setString(3, p.getIdUser()); // TODO: Nanti pake getUserID() nya ya inii masih default
            stmt.setString(4, p.getTanggalCheckIn().toString());
            stmt.setString(5, p.getTanggalCheckOut().toString());
            stmt.setString(6, p.getNamaPemesan());
            stmt.setString(7, p.getNoHpPemesan());
            stmt.setString(8, p.getEmailPemesan());
            stmt.setInt(9, 1); 
            stmt.setInt(10, 1); 
            stmt.setInt(11, p.getTotalHarga());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                kurangiKetersediaan(p.getIdKamar());
                return true;
            }
        } catch (Exception e) {
            System.out.println("SQL Error saat simpan pemesanan: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean isKamarAvailable(String idKamar, LocalDate checkin, LocalDate checkout) {
        String sql = "SELECT " +
                "  (SELECT tersedia FROM kamar WHERE idKamar = ?) AS sisa_kamar, " +
                "  (SELECT COUNT(*) FROM pesanankamar " +
                "     WHERE idKamar = ? AND" +
                "(tanggalCheckOut < ? OR tanggalCheckIn > ?)) AS total_booking";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idKamar);
            stmt.setString(2, idKamar);
            stmt.setString(3, checkout.toString());
            stmt.setString(4, checkin.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int sisaKamar = rs.getInt("sisa_kamar");
                int totalBooking = rs.getInt("total_booking");

                System.out.println("[DEBUG] Kamar tersedia: " + sisaKamar + ", Booking bentrok: " + totalBooking);
                return totalBooking < sisaKamar;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void kurangiKetersediaan(String idKamar) {
        String sql = "UPDATE kamar SET tersedia = tersedia - 1 WHERE idKamar = ? AND tersedia > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idKamar);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PemesananHotel> getPemesananByEmail(String email) {
        List<PemesananHotel> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanankamar WHERE email_pemesan = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PemesananHotel p = new PemesananHotel();
                p.setIdPesanan(rs.getString("idPesananKamar"));
                p.setIdKamar(rs.getString("idKamar"));
                p.setTanggalCheckIn(LocalDate.parse(rs.getString("tanggalCheckIn")));
                p.setTanggalCheckOut(LocalDate.parse(rs.getString("tanggalCheckOut")));
                p.setJumlahKamar(rs.getInt("jumlahKamar"));
                p.setJumlahTamu(rs.getInt("jumlahTamu"));
                p.setTotalHarga(rs.getInt("totalHarga"));
                p.setNamaPemesan(rs.getString("namaPemesan"));
                p.setNoHpPemesan(rs.getString("noHpPemesan"));
                p.setEmailPemesan(rs.getString("emailPemesan"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}