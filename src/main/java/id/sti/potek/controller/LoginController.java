package id.sti.potek.controller;

import id.sti.potek.model.User;
import id.sti.potek.service.AuthService;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {
    private TextField emailField;
    private PasswordField passwordField;
    private final AuthService authService;

    public LoginController(TextField emailField, PasswordField passwordField) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.authService = new AuthService(); // gunakan service
    }

    public void handleLogin(Stage stage) {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = authService.loginUser(email, password); // panggil service
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Berhasil");
            alert.setHeaderText("Selamat datang, " + user.getNama() + "!");
            alert.showAndWait();

            // TODO: Navigasi ke dashboard di sini
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Gagal");
            alert.setHeaderText("Email atau password salah!");
            alert.showAndWait();
        }
    }
}
