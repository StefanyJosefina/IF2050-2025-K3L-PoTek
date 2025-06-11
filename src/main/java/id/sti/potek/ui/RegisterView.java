package id.sti.potek.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import id.sti.potek.controller.RegisterController;

public class RegisterView {
    public void start(Stage stage) {
        Label title = new Label("Register");
        title.getStyleClass().add("auth-header");

        TextField nama = new TextField();
        nama.setPromptText("Nama Lengkap");
        nama.getStyleClass().add("auth-field");

        // Ganti TextField dengan DatePicker untuk tanggal lahir
        DatePicker tglPicker = new DatePicker();
        tglPicker.setPromptText("Tanggal Lahir");
        tglPicker.getStyleClass().add("auth-field");

        TextField email = new TextField();
        email.setPromptText("Email atau No HP");
        email.getStyleClass().add("auth-field");

        PasswordField pass = new PasswordField();
        pass.setPromptText("Kata Sandi");
        pass.getStyleClass().add("auth-field");

        Button btnRegister = new Button("Register");
        btnRegister.getStyleClass().add("auth-button");

        Label linkToLogin = new Label("Sudah punya akun? Login di sini");
        linkToLogin.getStyleClass().add("auth-link");

        // Add mouse click event to navigate to LoginView
        linkToLogin.setOnMouseClicked(e -> {
            LoginView loginView = new LoginView();
            loginView.start(stage);
        });
        
        // Gunakan RegisterController untuk handle registrasi
        RegisterController registerController = new RegisterController(nama, email, pass, tglPicker);
        
        btnRegister.setOnAction(e -> {
            // Validasi basic di UI
            String namaText = nama.getText();
            String emailText = email.getText();
            String passText = pass.getText();

            if (namaText == null || namaText.trim().isEmpty()) {
                new LoginPesanView("Required field missing: Nama Lengkap").show();
                return;
            }
            if (tglPicker.getValue() == null) {
                new LoginPesanView("Required field missing: Tanggal Lahir").show();
                return;
            }
            if (emailText == null || emailText.trim().isEmpty()) {
                new LoginPesanView("Required field missing: Email atau No HP").show();
                return;
            }
            if (passText == null || passText.trim().isEmpty()) {
                new LoginPesanView("Required field missing: Kata Sandi").show();
                return;
            }
            
            // Validate email format
            if (!emailText.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
                new LoginPesanView("Invalid input: Email format is incorrect").show();
                return;
            }
            
            // Validate password length
            if (passText.length() < 6) {
                new LoginPesanView("Validation error: Kata Sandi harus minimal 6 karakter").show();
                return;
            }

            // Panggil controller untuk handle registrasi
            registerController.handleRegister();
            new LoginView().start(stage);
        });

        VBox root = new VBox(15, title, nama, tglPicker, email, pass, btnRegister, linkToLogin);
        root.getStyleClass().add("auth-root");
        root.setAlignment(Pos.CENTER);

        /*Label Hijau */
        root.setMaxWidth(700);
        root.setMaxHeight(200);

        StackPane container = new StackPane(root);
        container.getStyleClass().add("root");

        Scene scene = new Scene(container, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/register.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("register");
        stage.show();
    }
}