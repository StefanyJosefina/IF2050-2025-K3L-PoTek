package id.sti.potek.controller;

import id.sti.potek.dao.UserDAO;
import id.sti.potek.model.User;
import id.sti.potek.service.AuthService;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RegisterController {
    private TextField namaField;
    private TextField emailField;
    private TextField passwordField;
    private TextField phoneField;
    private DatePicker tgl_lahirPicker;
    private final AuthService authService;

    public RegisterController(TextField namaField, TextField emailField, TextField passwordField, DatePicker tgl_lahirPicker) {
        this.namaField = namaField;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.tgl_lahirPicker = tgl_lahirPicker;
        this.authService = new AuthService();
    }

    public void handleRegister() {
        String nama = namaField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        // String phone = phoneField.getText();
        LocalDate tgl_lahir = tgl_lahirPicker.getValue();

        if (tgl_lahir == null || nama.isBlank() || email.isBlank() || password.isBlank() ) {
            showAlert(Alert.AlertType.ERROR, "Form belum lengkap", "Semua field harus diisi.");
            return;
        }

        String idUser = "U" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        User newUser = new User(idUser, nama, tgl_lahir.toString(), email, password);

        boolean success = authService.registerUser(newUser);
        if (success) {
            List<User> allUsers = UserDAO.getAllUsers();
            allUsers.forEach(user -> System.out.println("Registered: " + user.getIdUser() + " - " + user.getEmail()));
            showAlert(Alert.AlertType.INFORMATION, "Registrasi Berhasil", "Akun berhasil dibuat.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registrasi Gagal", "Email mungkin sudah digunakan.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
