package id.sti.potek.ui;

import id.sti.potek.dao.UserDAO;
import id.sti.potek.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GoogleView {

    public void start(Stage stage) {
        // Title
        Label title = new Label("Log In dengan Google");
        title.getStyleClass().add("google-title");

        Label pilihAkun = new Label("Pilih Akun");
        pilihAkun.getStyleClass().add("google-subtitle");

        VBox akunList = new VBox(15);
        akunList.setPadding(new Insets(10, 0, 0, 0));
        akunList.setPrefHeight(600); // Akan menyesuaikan daftar akun

        
        // Tambahkan daftar akun
        akunList.getChildren().addAll(
            buatAkun("Aulia Azka Azzahra", "aulia@gmail.com"),
            buatAkun("Stefany Josefina Santono", "fina@gmail.com"),
            buatAkun("Matilda Angelina Sumaryo", "matilda@gmail.com"),
            buatAkun("Theresia Ivana M S", "ivana@gmail.com"),
            buatAkun("Sonya Putri Fadilah", "sonya@gmail.com")
        );

        for (User user : UserDAO.getAllUsers()) {
            akunList.getChildren().add(buatAkun(user.getName(), user.getContact()));
        }
        // Tambahkan daftar akun
        VBox content = new VBox(20, title, pilihAkun, akunList);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(30));
        content.getStyleClass().add("google-card");

        StackPane root = new StackPane(content);
        root.getStyleClass().add("google-root");

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/google.css").toExternalForm());

        stage.setTitle("Login dengan Google");
        stage.setScene(scene);
        stage.show();
    }

    private HBox buatAkun(String nama, String email) {
        Circle avatar = new Circle(20);
        avatar.setFill(Color.LIGHTGRAY);

        Label namaLabel = new Label(nama);
        namaLabel.getStyleClass().add("google-nama");

        Label emailLabel = new Label(email);
        emailLabel.getStyleClass().add("google-email");

        VBox info = new VBox(2, namaLabel, emailLabel);

        HBox akun = new HBox(15, avatar, info);
        akun.setAlignment(Pos.CENTER_LEFT);
        akun.getStyleClass().add("google-akun");

        // Add mouse click event to open LoginView with pre-filled email
        akun.setOnMouseClicked(e -> {
            LoginView loginView = new LoginView(email);
            Stage stage = (Stage) akun.getScene().getWindow();
            loginView.start(stage);
        });

        return akun;
    }
}
