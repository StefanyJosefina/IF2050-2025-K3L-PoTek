// dao/UserDAO.java

package id.sti.potek.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import id.sti.potek.model.User;
import id.sti.potek.util.DBConnection;

public class UserDAO {

    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, birth_date, contact, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setDate(2, Date.valueOf(user.getBirthDate())); // format yyyy-MM-dd
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();
        }
    }

    public User login(String contact, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE contact = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getDate("birth_date").toString(),
                        rs.getString("contact"),
                        rs.getString("password")
                );
            }
        }

        return null;
    }

    public static java.util.List<User> getAllUsers() {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getDate("birth_date").toString(),
                        rs.getString("contact"),
                        rs.getString("password")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
