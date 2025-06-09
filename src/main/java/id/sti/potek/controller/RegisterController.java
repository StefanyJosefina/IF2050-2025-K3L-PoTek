package id.sti.potek.controller;

import id.sti.potek.model.User;
import id.sti.potek.service.AuthService;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.UUID;

public class RegisterController {
    private TextField nameField;
    private TextField emailField;
    private TextField passwordField;
    private TextField phoneField;
    private DatePicker birthDatePicker;
    private final AuthService authService;

    public RegisterController(TextField nameField, TextField emailField, TextField passwordField,
                              TextField phoneField, DatePicker birthDatePicker) {
        this.nameField = nameField;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.phoneField = phoneField;
        this.birthDatePicker = birthDatePicker;
        this.authService = new AuthService();
    }

    public void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        LocalDate birthDate = birthDatePicker.getValue();

        if (birthDate == null || name.isBlank() || email.isBlank() || password.isBlank() || phone.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Form belum lengkap", "Semua field harus diisi.");
            return;
        }

        String idUser = "U" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        User newUser = new User(idUser, name, birthDate.toString(), phone, email, password);

        boolean success = authService.registerUser(newUser);
        if (success) {
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
