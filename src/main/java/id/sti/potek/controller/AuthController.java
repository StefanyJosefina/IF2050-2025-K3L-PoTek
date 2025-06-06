// controller/AuthController.java

package id.sti.potek.controller;
import id.sti.potek.model.User;
import id.sti.potek.service.AuthService;

public class AuthController {
    private final AuthService authService = new AuthService();

    public boolean register(String name, String birthDate, String contact, String password) {
        User user = new User(name, birthDate, contact, password);
        return authService.register(user);
    }

    public User login(String contact, String password) {
        return authService.login(contact, password);
    }
}
