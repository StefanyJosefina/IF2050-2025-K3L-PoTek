package id.sti.potek.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.Tiket;
import id.sti.potek.service.TiketService;
import id.sti.potek.util.DBConnection;

public class TiketController {
    private final TiketService tiketService = new TiketService();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        return tiketService.cariTiket(asal, tujuan, tanggal);
    }
    public List<Tiket> getAllTiket() {
        return tiketService.getAllTiket();
    }
    public boolean simpanPemesanan(String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                   String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        return tiketService.simpanPemesanan(idTiket, namaPemesan, noHpPemesan, emailPemesan,
                namaPenumpang, noHpPenumpang, emailPenumpang, noKursi);
    }
    public List<Integer> getKursiTerbooking(String idTiket) {
        List<Integer> booked = new ArrayList<>();
        String sql = "SELECT noKursi FROM pemesanan WHERE idTiket = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idTiket);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                booked.add(rs.getInt("noKursi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booked;
    }


}
