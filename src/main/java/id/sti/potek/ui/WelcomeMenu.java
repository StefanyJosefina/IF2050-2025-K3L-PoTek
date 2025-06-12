package id.sti.potek.ui;

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
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class WelcomeMenu {

    public void start(Stage stage) {
        // Top Bar: Login and Register Buttons
        Button btnLogin = new Button("Log In");
        btnLogin.getStyleClass().add("login-button");

        Button btnRegister = new Button("Register");
        btnRegister.getStyleClass().add("register-button");

        btnLogin.setOnAction(e -> {
            new LoginView().start(stage);
        });

        btnRegister.setOnAction(e -> {
            new RegisterView().start(stage);
        });

        HBox headerButtons = new HBox(15, btnLogin, btnRegister);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.setPadding(new Insets(20, 30, 0, 0));

        // ==== Logo & Bintang Dekoratif ====
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPadding(new Insets(40, 0, 40, 0));

        ImageView logo = new ImageView(new Image("/icons/heart.png"));
        logo.setFitWidth(120);
        logo.setFitHeight(120);
        logo.setPreserveRatio(true);

        Text star1 = new Text("★");
        star1.getStyleClass().add("decorative-star");
        Text star2 = new Text("★");
        star2.getStyleClass().add("decorative-star");
        Text star3 = new Text("★");
        star3.getStyleClass().add("decorative-star");

        StackPane logoWithStars = new StackPane();
        logoWithStars.getChildren().addAll(logo, star1, star2, star3);
        StackPane.setAlignment(star1, Pos.TOP_LEFT);
        StackPane.setAlignment(star2, Pos.TOP_RIGHT);
        StackPane.setAlignment(star3, Pos.BOTTOM_LEFT);
        StackPane.setMargin(star1, new Insets(-20, 0, 0, -30));
        StackPane.setMargin(star2, new Insets(-15, -20, 0, 0));
        StackPane.setMargin(star3, new Insets(0, 0, -10, -40));

        logoContainer.getChildren().add(logoWithStars);

        // ==== Welcome Text ====
        Text welcomeTitle = new Text("Welcome to PoTek !");
        welcomeTitle.getStyleClass().add("welcome-title");
        welcomeTitle.setStyle("-fx-fill: #EC4C69;");

        Text tagline = new Text("PoTek – Teman setia untuk setiap langkah perjalananmu.");
        tagline.getStyleClass().add("welcome-subtitle");
        tagline.setStyle("-fx-fill: #EC4C69;");

        VBox welcomeText = new VBox(15, welcomeTitle, tagline);
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.setPadding(new Insets(0, 50, 50, 50));

        // ==== Menu Buttons ====
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

        HBox bottomButtons = new HBox(30, btnHotel, btnTransportasi);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(0, 50, 40, 50));

        // ==== Main Content ====
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.getChildren().addAll(logoContainer, welcomeText, bottomButtons);

        VBox root = new VBox();
        root.getChildren().addAll(headerButtons, mainContent);
        root.getStyleClass().add("main-background");

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/potek_landing.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("PoTek - Selamat Datang");
        stage.centerOnScreen();
        stage.show();
    }
}
