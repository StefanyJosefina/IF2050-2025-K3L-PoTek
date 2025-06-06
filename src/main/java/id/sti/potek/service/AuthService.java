// service/AuthService.java

package id.sti.potek.service;
import java.sql.SQLException;

import id.sti.potek.dao.UserDAO;
import id.sti.potek.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        try {
            userDAO.registerUser(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public User login(String contact, String password) {
        try {
            return userDAO.login(contact, password);
        } catch (SQLException e) {
            return null;
        }
    }
}
