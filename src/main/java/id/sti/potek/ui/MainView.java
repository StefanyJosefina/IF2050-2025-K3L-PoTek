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

public class MainView {

    public void start(Stage stage) {
        // Top Bar: Username and Buttons
        Label usernameLabel = new Label("Name");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        Button btnReport = new Button("Report");
        btnReport.setStyle("-fx-background-color: #fff8f0; -fx-text-fill: black; -fx-padding: 8 16;");

        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: #86A788; -fx-text-fill: white; -fx-padding: 8 16;");

        btnLogout.setOnAction(e -> {
            new LogoutView().start(stage);
        });

        HBox leftRightTop = new HBox(10, usernameLabel);
        leftRightTop.setAlignment(Pos.CENTER_LEFT);

        HBox rightButtons = new HBox(10, btnReport, btnLogout);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        HBox topBar = new HBox(20, leftRightTop, rightButtons);
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER);
        HBox.setHgrow(leftRightTop, javafx.scene.layout.Priority.ALWAYS);

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

        // Final Layout
        VBox root = new VBox(30, topBar, centerContent, bottomButtons);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #ffe2e2;");

        Scene scene = new Scene(root, 900, 645);
        stage.setScene(scene);
        stage.setTitle("Main View");
        stage.show();
    }
}
