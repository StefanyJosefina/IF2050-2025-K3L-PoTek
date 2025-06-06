package id.sti.potek.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeMenu {

    public void start(Stage stage) {
        // Top Bar: Login and Register Buttons
        Button btnLogin = new Button("Log In");
        btnLogin.setStyle("-fx-background-color: #86A788; -fx-text-fill: white; -fx-padding: 8 16;");

        Button btnRegister = new Button("Register");
        btnRegister.setStyle("-fx-background-color: #86A788; -fx-text-fill: white; -fx-padding: 8 16;");

        btnLogin.setOnAction(e -> {
            new LoginView().start(stage);
        });

        btnRegister.setOnAction(e -> {
            // Implement register view navigation if needed
        });

        HBox rightButtons = new HBox(10, btnLogin, btnRegister);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Center: Heart Icon and Welcome Message
        ImageView heartIcon = new ImageView(getClass().getResource("/icons/heart.png").toExternalForm());
        heartIcon.setFitHeight(120);
        heartIcon.setPreserveRatio(true);

        Label welcomeTitle = new Label("Welcome to PoTek !");
        welcomeTitle.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #d94a64;");

        Label tagline = new Label("PoTek — Teman setia untuk setiap langkah perjalananmu.");
        tagline.setStyle("-fx-font-size: 30px; -fx-text-fill: #e58d8d;");

        VBox centerContent = new VBox(10, heartIcon, welcomeTitle, tagline);
        centerContent.setAlignment(Pos.CENTER);

        // Bottom: Menu Buttons
        Button btnHotel = new Button("🏨 Hotel");
        Button btnTransportasi = new Button("🚆 Transportasi");

        for (Button btn : new Button[]{btnHotel, btnTransportasi}) {
            btn.setStyle("-fx-background-color: #86A788; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12 24; -fx-background-radius: 12;");
        }

        HBox bottomButtons = new HBox(30, btnHotel, btnTransportasi);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(20));

        VBox root = new VBox(30, rightButtons, centerContent, bottomButtons);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #ffe2e2;");

        Scene scene = new Scene(root, 900, 645);
        stage.setScene(scene);
        stage.setTitle("Welcome Menu");
        stage.show();
    }
}
