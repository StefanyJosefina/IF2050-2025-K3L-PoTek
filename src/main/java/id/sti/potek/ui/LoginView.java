package id.sti.potek.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    public void start(Stage stage) {
        Label title = new Label("Log In");
        title.getStyleClass().add("auth-header");

        TextField email = new TextField();
        email.setPromptText("Masukkan Nomor HP atau Email");
        email.getStyleClass().add("auth-field");

        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.getStyleClass().add("auth-field");

        Button btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("auth-button");

        Label orSeparator = new Label("Or");
        orSeparator.getStyleClass().add("auth-or");

        Image googleIcon = new Image(getClass().getResourceAsStream("/icons/google.png"));
        ImageView googleIconView = new ImageView(googleIcon);
        googleIconView.setFitHeight(20);
        googleIconView.setPreserveRatio(true);

        Button googleLogin = new Button("Login with Google", googleIconView);
        googleLogin.getStyleClass().add("auth-google");
        googleLogin.setGraphicTextGap(10); // jarak antara icon dan teks

        Label linkToRegister = new Label("Belum punya akun?");
        linkToRegister.getStyleClass().add("auth-link");

        VBox root = new VBox(15, title, email, pass, btnLogin, orSeparator, googleLogin, linkToRegister);
        root.getStyleClass().add("auth-root");
        root.setAlignment(Pos.CENTER);

        /*Label Hijau */
        root.setMaxWidth(900);
        root.setMaxHeight(645);

        StackPane container = new StackPane(root);
        container.getStyleClass().add("root");

        Scene scene = new Scene(container, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}
