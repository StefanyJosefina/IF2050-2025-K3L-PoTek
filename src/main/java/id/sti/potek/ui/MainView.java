package id.sti.potek.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import id.sti.potek.model.User;
import id.sti.potek.util.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView {
    private String userName;
    private User loggedInUser;

    public MainView(User user) {
        this.loggedInUser = user;
        this.userName = user.getNama();
    }

    // public MainView(String userName) {
    //     this.userName = userName != null ? userName : "Guest";
    //     this.loggedInUser = null;    
    // }

    public void start(Stage stage) {
        // Top Bar: Username and Buttons
        Label usernameLabel = new Label(userName);
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        Button btnReview = new Button("Review");
        btnReview.getStyleClass().add("review-button");

        Button btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("login-button");

        btnLogout.setOnAction(e -> {
            new LogoutView(() -> {
                new WelcomeMenu().start(stage);
            }).start(stage);
        });

        btnReview.setOnAction(e -> {
            // Retrieve idPesananKamar for the logged-in user
            String idPesananKamar = getIdPesananKamar();
            if (idPesananKamar != null) {
                new UlasanFormView(idPesananKamar).start(stage);
            } else {
                showNoBookingPopup(stage);
            }
        });

        HBox leftRightTop = new HBox(10, usernameLabel);
        leftRightTop.setAlignment(Pos.CENTER_LEFT);

        HBox rightButtons = new HBox(10, btnReview, btnLogout);
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
        welcomeTitle.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #EC4C69;");

        Label tagline = new Label("PoTek — Teman setia untuk setiap langkah perjalananmu.");
        tagline.setStyle("-fx-font-size: 30px; -fx-text-fill: #EC4C69;");

        VBox centerContent = new VBox(15, heartIcon, welcomeTitle, tagline);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(30, 0, 30, 0));

        // ==== Bottom Buttons (Hotel, Transportasi) ====
        ImageView hotelIcon = new ImageView(new Image("/icons/kasur-icon.png"));
        hotelIcon.setFitWidth(24);
        hotelIcon.setFitHeight(24);

        Button btnHotel = new Button("Hotel");
        btnHotel.setGraphic(hotelIcon);
        btnHotel.getStyleClass().add("action-button");

        ImageView transportIcon = new ImageView(new Image("/icons/icon_transportasi.png"));
        transportIcon.setFitWidth(24);
        transportIcon.setFitHeight(24);

        Button btnTransportasi = new Button("Transportasi");
        btnTransportasi.setGraphic(transportIcon);
        btnTransportasi.getStyleClass().add("action-button");

        btnTransportasi.setOnAction(e -> {
            new TiketCariView(loggedInUser).start(stage);
        });

        btnHotel.setOnAction(e -> {
            new HotelCariView(loggedInUser).start(stage);
        });

        HBox bottomButtons = new HBox(30, btnHotel, btnTransportasi);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(0, 50, 40, 50));

        // ==== Final Layout ====
        VBox root = new VBox(30, topBar, centerContent, bottomButtons);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("main-background");

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/mainview.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Main View");
        stage.centerOnScreen();
        stage.show();
    }

    private String getIdPesananKamar() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idPesananKamar FROM pesanankamar WHERE idUser = ? LIMIT 1")) {
            stmt.setString(1, loggedInUser.getIdUser());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("idPesananKamar");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving idPesananKamar: " + e.getMessage());
        }
        return null;
    }

    private void showNoBookingPopup(Stage ownerStage) {
        Stage popupStage = new Stage();
        popupStage.initOwner(ownerStage);
        popupStage.setAlwaysOnTop(true);

        Rectangle overlay = new Rectangle(900, 645);
        overlay.setFill(Color.web("#F8C8DC", 0.6));

        Label msg = new Label("No booking found for this user!");
        msg.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d94a64;");

        VBox popupBox = new VBox(msg);
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(30));
        popupBox.setStyle("-fx-background-color: white; -fx-background-radius: 20px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);");

        StackPane root = new StackPane(overlay, popupBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 380, 130);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setScene(scene);
        popupStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(popupStage::close);
        }).start();
    }
}