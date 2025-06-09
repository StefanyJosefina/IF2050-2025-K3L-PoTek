package id.sti.potek.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RegisterView {
    public void start(Stage stage) {
        Label title = new Label("Register");
        title.getStyleClass().add("auth-header");

        TextField nama = new TextField();
        nama.setPromptText("Nama Lengkap");
        nama.getStyleClass().add("auth-field");

        TextField tgl = new TextField();
        tgl.setPromptText("Tanggal Lahir");
        tgl.getStyleClass().add("auth-field");

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
        
        btnRegister.setOnAction(e -> {
            String namaText = nama.getText();
            String tglText = tgl.getText();
            String emailText = email.getText();
            String passText = pass.getText();

            if (namaText == null || namaText.trim().isEmpty()) {
                new LoginPesanView("Required field missing: Nama Lengkap").show();
                return;
            }
            if (tglText == null || tglText.trim().isEmpty()) {
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
            // Validate email or phone format
            if (!emailText.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$") && !emailText.matches("^\\+?\\d{10,15}$")) {
                new LoginPesanView("Invalid input: Email atau No HP format is incorrect").show();
                return;
            }
            // Validate date format (simple check for YYYY-MM-DD)
            if (!tglText.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                new LoginPesanView("Invalid input: Tanggal Lahir format harus YYYY-MM-DD").show();
                return;
            }
            // Validate password length
            if (passText.length() < 6) {
                new LoginPesanView("Validation error: Kata Sandi harus minimal 6 karakter").show();
                return;
            }

            // Proceed with registration logic here
            System.out.println("Registration successful for: " + namaText);
            new LoginPesanView("Registration successful!").show();
            stage.close();
            new PoTekLandingView().start(stage);
        });

        VBox root = new VBox(15, title, nama, tgl, email, pass, btnRegister, linkToLogin);
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