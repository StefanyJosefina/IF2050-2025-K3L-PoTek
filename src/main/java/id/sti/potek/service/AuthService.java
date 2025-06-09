package id.sti.potek.service;

import id.sti.potek.dao.UserDAO;
import id.sti.potek.model.User;

public class AuthService {

    public boolean registerUser(User user) {
        return UserDAO.register(user);
    }

    public User loginUser(String email, String password) {
        return UserDAO.login(email, password);
    }
}
