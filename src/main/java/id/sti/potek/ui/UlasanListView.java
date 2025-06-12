package id.sti.potek.ui;

import id.sti.potek.model.Ulasan; // Import model Ulasan yang sudah ada
import id.sti.potek.model.User;
import id.sti.potek.util.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UlasanListView {
    private User loggedInUser; // Ambil user yang sedang login

    private final String idKamar;

    public UlasanListView(String idKamar) {
        this.idKamar = idKamar;
    }

    public void start(Stage stage) {
        String namaHotel = "Nama Hotel";
        String lokasiHotel = "Lokasi Hotel";
        int totalReview = 0;
        double totalRating = 0.0;

        List<Ulasan> ulasanList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            // Ambil nama & lokasi hotel
            PreparedStatement stmtKamar = conn.prepareStatement("SELECT namaHotel, lokasi FROM kamar WHERE idKamar = ?");
            stmtKamar.setString(1, idKamar);
            ResultSet rsKamar = stmtKamar.executeQuery();
            if (rsKamar.next()) {
                namaHotel = rsKamar.getString("namaHotel");
                lokasiHotel = rsKamar.getString("lokasi");
            }
            System.out.println("Hotel ditemukan: " + namaHotel + " di " + lokasiHotel);

            // Ambil ulasan + nama user - menggunakan constructor untuk tampilkan ulasan
            PreparedStatement stmtUlasan = conn.prepareStatement("""
                SELECT u.komentar, u.rating, us.nama
                FROM ulasan u
                JOIN user us ON u.idUser = us.idUser
                WHERE u.idKamar = ?
                ORDER BY u.rating DESC
            """);
            stmtUlasan.setString(1, idKamar);
            ResultSet rsUlasan = stmtUlasan.executeQuery();
            while (rsUlasan.next()) {
                String komentar = rsUlasan.getString("komentar");
                int rating = rsUlasan.getInt("rating");
                String nama = rsUlasan.getString("nama");

                // Menggunakan constructor untuk tampilkan ulasan dari model Ulasan
                ulasanList.add(new Ulasan(nama, idKamar, rating, komentar));
                totalRating += rating;
                totalReview++;
                System.out.println("Ulasan ditemukan - Nama: " + nama + ", Rating: " + rating + ", Komentar: " + komentar);
            }
        } catch (Exception e) {
            System.err.println("Error saat mengambil data: " + e.getMessage());
            e.printStackTrace();
        }

        double rataRata = totalReview > 0 ? totalRating / totalReview : 0.0;
        String labelRating = String.format("%.1f/5 (%d review)", rataRata, totalReview);

        Label header = new Label("Review");
        header.getStyleClass().add("header-title");

        HBox headerBar = new HBox(header);
        headerBar.getStyleClass().add("header-bar");
        headerBar.setAlignment(Pos.CENTER);

        ImageView arrowIcon = new ImageView(new Image("/icons/Vector.png"));
        arrowIcon.setFitWidth(20);
        arrowIcon.setFitHeight(20);

        Button backBtn = new Button("Kembali");
        backBtn.setGraphic(arrowIcon);
        backBtn.getStyleClass().add("back-button");
        backBtn.setOnAction(e -> {
            try {
                Stage newStage = new Stage();
                PoTekLandingView mainView = new PoTekLandingView();
                mainView.start(newStage);
                stage.close();
            } catch (Exception ex) {
                System.out.println("Error opening search view: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox infoPanel = new VBox(10);
        infoPanel.getStyleClass().add("info-panel");

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/icons/hotel.png")));
        image.setFitWidth(260);
        image.setFitHeight(150);
        image.setPreserveRatio(true);

        Label namaHotelLabel = new Label(namaHotel);
        namaHotelLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lokasi = new Label(lokasiHotel);

        HBox ratingBox = new HBox(5);
        ImageView starIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/star.png")));
        starIcon.setFitWidth(18);
        starIcon.setFitHeight(18);
        Label rating = new Label(labelRating);
        ratingBox.getChildren().addAll(starIcon, rating);
        ratingBox.setAlignment(Pos.CENTER_LEFT);

        VBox fasilitas = new VBox(5,
                makeFasilitas("40m2", "wide.png"),
                makeFasilitas("Bathtub", "bathtub.png"),
                makeFasilitas("1 Tamu", "person.png"),
                makeFasilitas("1 Kasur Single", "bed.png"),
                makeFasilitas("WiFi", "wifi.png"),
                makeFasilitas("Sarapan", "breakfast.png"),
                makeFasilitas("Bebas Asap Rokok", "smoke-free.png")
        );

        infoPanel.getChildren().addAll(image, namaHotelLabel, lokasi, ratingBox, fasilitas, backBtn); //////
        infoPanel.setPrefWidth(300);

        VBox reviewList = new VBox(15);
        reviewList.setPadding(new Insets(20));

        // Tambahkan debug info jika tidak ada ulasan
        if (ulasanList.isEmpty()) {
            Label noReview = new Label("Belum ada ulasan untuk kamar ini");
            noReview.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            reviewList.getChildren().add(noReview);
            System.out.println("Tidak ada ulasan ditemukan untuk kamar: " + idKamar);
        }

        for (Ulasan u : ulasanList) {
            VBox card = new VBox(5);
            card.getStyleClass().add("review-card");

            // Menggunakan getNamaUser() dari model Ulasan
            Label avatar = new Label(u.getNamaUser().substring(0, 1));
            avatar.getStyleClass().add("avatar");

            Label nama = new Label(u.getNamaUser());
            nama.getStyleClass().add("user-name");

            VBox namaBox = new VBox(nama);
            HBox headerReview = new HBox(avatar, namaBox);
            headerReview.setSpacing(10);
            headerReview.setAlignment(Pos.CENTER_LEFT);

            Label komentar = new Label(u.getKomentar());
            komentar.getStyleClass().add("comment-text");

            ImageView bintang = new ImageView(new Image(getClass().getResourceAsStream("/icons/star.png")));
            bintang.setFitWidth(12);
            bintang.setFitHeight(12);

            Label nilai = new Label(String.valueOf(u.getRating()));
            nilai.getStyleClass().add("comment-rating");

            HBox ratingSection = new HBox(5, bintang, nilai);
            ratingSection.setAlignment(Pos.CENTER_RIGHT);

            BorderPane footer = new BorderPane();
            footer.setRight(ratingSection);

            card.getChildren().addAll(headerReview, komentar, footer);
            reviewList.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(reviewList);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setPrefWidth(540);

        HBox mainLayout = new HBox(30, infoPanel, scroll);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #F8C8DC;");

        VBox root = new VBox(headerBar, mainLayout);
        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/ulasanlist.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private HBox makeFasilitas(String text, String iconFile) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/" + iconFile)));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        Label label = new Label(text);
        return new HBox(5, icon, label);
    }

}