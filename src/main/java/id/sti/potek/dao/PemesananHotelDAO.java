package id.sti.potek.dao;

import java.sql.Connection;
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

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            int jumlahKamarDiminta = p.getJumlahKamar() != 0 ? p.getJumlahKamar() : 1;
            if (!isKamarAvailable(p.getIdKamar(), p.getTanggalCheckIn(), p.getTanggalCheckOut(), jumlahKamarDiminta)) {
                System.out.println("Kamar tidak tersedia untuk tanggal yang dipilih atau stok tidak mencukupi");
                return false;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

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
            stmt.setString(3, p.getIdUser());
            stmt.setString(4, p.getTanggalCheckIn().toString());
            stmt.setString(5, p.getTanggalCheckOut().toString());
            stmt.setString(6, p.getNamaPemesan());
            stmt.setString(7, p.getNoHpPemesan());
            stmt.setString(8, p.getEmailPemesan());
            stmt.setInt(9, p.getJumlahKamar() != 0 ? p.getJumlahKamar() : 1);
            stmt.setInt(10, p.getJumlahTamu() != 0 ? p.getJumlahTamu() : 1);
            stmt.setInt(11, p.getTotalHarga());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                int jumlahKamar = p.getJumlahKamar() != 0 ? p.getJumlahKamar() : 1;
                if (kurangiKetersediaan(p.getIdKamar(), jumlahKamar, conn)) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
            conn.rollback();
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("SQL Error saat simpan pemesanan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isKamarAvailable(String idKamar, LocalDate checkin, LocalDate checkout) {
        return isKamarAvailable(idKamar, checkin, checkout, 1);
    }
    
    public boolean isKamarAvailable(String idKamar, LocalDate checkin, LocalDate checkout, int jumlahKamarDiminta) {
        String sql = "SELECT tersedia FROM kamar WHERE idKamar = ?";
        String conflictSql = "SELECT COALESCE(SUM(jumlahKamar), 0) as total_booked FROM pesanankamar " +
                "WHERE idKamar = ? AND NOT (tanggalCheckOut <= ? OR tanggalCheckIn >= ?)";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            stmt1.setString(1, idKamar);
            ResultSet rs1 = stmt1.executeQuery();
            
            int totalTersedia = 0;
            if (rs1.next()) {
                totalTersedia = rs1.getInt("tersedia");
            }
            
            if (totalTersedia <= 0) {
                System.out.println("[DEBUG] Kamar tidak tersedia - stok habis: " + totalTersedia);
                return false;
            }
            
            PreparedStatement stmt2 = conn.prepareStatement(conflictSql);
            stmt2.setString(1, idKamar);
            stmt2.setString(2, checkin.toString());
            stmt2.setString(3, checkout.toString());
            ResultSet rs2 = stmt2.executeQuery();
            
            int totalBooked = 0;
            if (rs2.next()) {
                totalBooked = rs2.getInt("total_booked");
            }

            int sisaKamar = totalTersedia - totalBooked;
            System.out.println("[DEBUG] Total tersedia: " + totalTersedia + 
                             ", Total booked: " + totalBooked + 
                             ", Sisa kamar: " + sisaKamar + 
                             ", Diminta: " + jumlahKamarDiminta);
            
            return sisaKamar >= jumlahKamarDiminta && sisaKamar > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean kurangiKetersediaan(String idKamar, int jumlah, Connection conn) {
        String checkSql = "SELECT tersedia FROM kamar WHERE idKamar = ?";
        String updateSql = "UPDATE kamar SET tersedia = tersedia - ? WHERE idKamar = ? AND tersedia >= ?";
        
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, idKamar);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int currentStock = rs.getInt("tersedia");
                System.out.println("[DEBUG] Current stock: " + currentStock + ", Akan dikurangi: " + jumlah);
                
                if (currentStock < jumlah) {
                    System.out.println("[ERROR] Stok tidak mencukupi. Tersedia: " + currentStock + ", Diminta: " + jumlah);
                    return false;
                }
                
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, jumlah);
                    updateStmt.setString(2, idKamar);
                    updateStmt.setInt(3, jumlah);
                    int updatedRows = updateStmt.executeUpdate();
                    
                    if (updatedRows > 0) {
                        System.out.println("[SUCCESS] Ketersediaan berhasil dikurangi. Sisa: " + (currentStock - jumlah));
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void kurangiKetersediaan(String idKamar) {
        try (Connection conn = DBConnection.getConnection()) {
            kurangiKetersediaan(idKamar, 1, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getKetersediaanKamar(String idKamar) {
        String sql = "SELECT tersedia FROM kamar WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idKamar);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("tersedia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean tambahKetersediaan(String idKamar, int jumlah) {
        String sql = "UPDATE kamar SET tersedia = tersedia + ? WHERE idKamar = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jumlah);
            stmt.setString(2, idKamar);
            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("[SUCCESS] Ketersediaan berhasil ditambah: " + jumlah);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<PemesananHotel> getPemesananByEmail(String email) {
        List<PemesananHotel> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanankamar WHERE emailPemesan = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PemesananHotel p = new PemesananHotel();
                p.setIdPesanan(rs.getString("idPesananKamar"));
                p.setIdKamar(rs.getString("idKamar"));
                p.setIdUser(rs.getString("idUser"));
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