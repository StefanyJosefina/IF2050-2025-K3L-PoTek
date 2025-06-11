// dao/UserDAO.java

package id.sti.potek.dao;

import java.sql.Connection;
// import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import id.sti.potek.model.User;
import id.sti.potek.util.DBConnection;

public class UserDAO {

    // LOGIN - berdasarkan email dan password
    public static User login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("idUser"),
                        rs.getString("nama"),
                        rs.getString("tgl_lahir"),
                        rs.getString("email"),
                        rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // REGISTER - simpan user baru
    public static boolean register(User user) {
        String query = "INSERT INTO user (idUser, nama, email, password, tgl_lahir) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getIdUser());
            stmt.setString(2, user.getNama());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            // stmt.setString(5, user.getNoHp());
            stmt.setString(5, user.getTanggalLahir());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Registrasi user berhasil: " + user.getEmail());
                return true;
            } else {
                System.out.println("⚠️ Registrasi gagal, tidak ada baris yang dimasukkan.");
                return false;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email sudah terdaftar!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("idUser"),
                    rs.getString("nama"),
                    rs.getString("tgl_lahir"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
