package id.sti.potek.ui;

import id.sti.potek.App;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PoTekLandingView {
    private Scene scene;

    private Runnable onLoginClicked;
    public void setOnLoginClicked(Runnable onLoginClicked) {
        this.onLoginClicked = onLoginClicked;
    }

    public void start(Stage stage) {
        // Header dengan tombol Login dan Register
        Button loginBtn = new Button("Log In");
        loginBtn.getStyleClass().add("login-button");
        
        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().add("register-button");
        
        HBox headerButtons = new HBox(15, loginBtn, registerBtn);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.setPadding(new Insets(20, 30, 0, 0));

        // Logo container dengan bintang dekoratif
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPadding(new Insets(40, 0, 40, 0));
        
        // Logo - ganti dengan logo Anda sendiri
        ImageView logo = new ImageView();
        try {
            logo.setImage(new Image("/icons/logo.png"));
        } catch (Exception e) {
            // Jika gambar tidak ditemukan, buat placeholder text
            System.out.println("Logo tidak ditemukan, menggunakan placeholder");
        }
        logo.setFitWidth(120);
        logo.setFitHeight(120);
        logo.setPreserveRatio(true);
        
        // Bintang dekoratif (bisa diganti dengan icon atau gambar bintang)
        Text star1 = new Text("★");
        star1.getStyleClass().add("decorative-star");
        Text star2 = new Text("★");
        star2.getStyleClass().add("decorative-star");
        Text star3 = new Text("★");
        star3.getStyleClass().add("decorative-star");
        
        // Posisi bintang relatif terhadap logo
        StackPane logoWithStars = new StackPane();
        logoWithStars.getChildren().add(logo);
        
        // Menambahkan bintang dengan posisi manual
        StackPane.setAlignment(star1, Pos.TOP_LEFT);
        StackPane.setAlignment(star2, Pos.TOP_RIGHT);
        StackPane.setAlignment(star3, Pos.BOTTOM_LEFT);
        StackPane.setMargin(star1, new Insets(-20, 0, 0, -30));
        StackPane.setMargin(star2, new Insets(-15, -20, 0, 0));
        StackPane.setMargin(star3, new Insets(0, 0, -10, -40));
        
        logoWithStars.getChildren().addAll(star1, star2, star3);
        logoContainer.getChildren().add(logoWithStars);

        // Welcome text
        Text welcomeTitle = new Text("Welcome to PoTek !");
        welcomeTitle.getStyleClass().add("welcome-title");
        welcomeTitle.setStyle("-fx-fill: #EC4C69;");

        
        Text welcomeSubtitle = new Text("PoTek – Teman setia untuk setiap langkah perjalananmu.");
        welcomeSubtitle.getStyleClass().add("welcome-subtitle");
        welcomeSubtitle.setStyle("-fx-fill: #EC4C69;");

        
        VBox welcomeText = new VBox(15, welcomeTitle, welcomeSubtitle);
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.setPadding(new Insets(0, 50, 50, 50));

        // Action buttons
        ImageView hotelIcon = new ImageView(new Image("/icons/kasur-icon.png"));
        hotelIcon.setFitWidth(24);
        hotelIcon.setFitHeight(24);
        Button hotelBtn = new Button("Hotel");
        hotelBtn.setGraphic(hotelIcon);
        hotelBtn.getStyleClass().add("action-button");
        
        ImageView transportIcon = new ImageView(new Image("/icons/icon_transportasi.png"));
        transportIcon.setFitWidth(24);
        transportIcon.setFitHeight(24);
        Button transportBtn = new Button("Transportasi");
        transportBtn.setGraphic(transportIcon);
        transportBtn.getStyleClass().add("action-button");
        
        HBox actionButtons = new HBox(30, hotelBtn, transportBtn);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(0, 50, 40, 50));

        // Main content layout
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.getChildren().addAll(logoContainer, welcomeText, actionButtons);
        
        // Root layout
        VBox root = new VBox();
        root.getChildren().addAll(headerButtons, mainContent);
        root.getStyleClass().add("main-background");
        
        // Scene setup
        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/potek_landing.css").toExternalForm());
        
        // Button event handlers
        loginBtn.setOnAction(e -> {
            // Navigasi ke halaman login
            System.out.println("Login button clicked");
            new LoginView().start(stage);
        });
        
        registerBtn.setOnAction(e -> {
            // Navigasi ke halaman register
            System.out.println("Register button clicked");
            new RegisterView().start(stage);
        });
        
        hotelBtn.setOnAction(e -> {
            // Navigasi ke halaman hotel
            System.out.println("Hotel button clicked");
            // new HotelView().start(stage);
        });
        
        transportBtn.setOnAction(e -> {
            // Navigasi ke halaman transportasi
            System.out.println("Transportasi button clicked");
            // new TiketCariView().start(stage);
        });

        stage.setScene(scene);
        stage.setTitle("PoTek - Teman Setia Perjalananmu");
        stage.centerOnScreen();
        stage.show();
    }
    public Scene getScene() {
        return scene;
    }
}