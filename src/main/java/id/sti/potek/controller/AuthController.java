// controller/AuthController.java

package id.sti.potek.controller;
import id.sti.potek.model.User;
import id.sti.potek.service.AuthService;

public class AuthController {
    private final AuthService authService = new AuthService();

    public boolean register(String idUser, String nama, String tgl_lahir, String email, String password) {
        User user = new User(idUser, nama, tgl_lahir, email, password);
        return authService.registerUser(user);
    }

    public User login(String email, String password) {
        return authService.loginUser(email, password);
    }
}
