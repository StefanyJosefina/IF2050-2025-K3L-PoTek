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

        VBox root = new VBox(15, title, nama, tgl, email, pass, btnRegister, linkToLogin);
        root.getStyleClass().add("auth-root");
        root.setAlignment(Pos.CENTER);

        /*Label Hijau */
        root.setMaxWidth(700);
        root.setMaxHeight(200);

        StackPane container = new StackPane(root);
        container.getStyleClass().add("root");;

        Scene scene = new Scene(container, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/register.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("register");
        stage.show();
    }
}
