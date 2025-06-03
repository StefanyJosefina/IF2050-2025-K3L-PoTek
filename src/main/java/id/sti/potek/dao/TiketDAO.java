package id.sti.potek.dao;


import id.sti.potek.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.Tiket;

public class TiketDAO {
    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        List<Tiket> daftar = new ArrayList<>();
        String sql = "SELECT * FROM tiket WHERE keberangkatan=? AND tujuan=? AND tanggal=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, asal);
            stmt.setString(2, tujuan);
            stmt.setString(3, tanggal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tiket t = new Tiket(
                    rs.getString("idTiket"),
                    rs.getString("keberangkatan"),
                    rs.getString("tujuan"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getInt("harga"),
                    rs.getInt("tersedia_kursi")
                );
                daftar.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }
}
