package id.sti.potek.ui;

import id.sti.potek.model.Ulasan;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Arrays;

public class UlasanListView {

    public void start(Stage stage) {
        Label header = new Label("Review");
        header.getStyleClass().add("header-title");

        HBox headerBar = new HBox(header);
        headerBar.getStyleClass().add("header-bar");
        headerBar.setAlignment(Pos.CENTER);

        VBox infoPanel = new VBox(10);
        infoPanel.getStyleClass().add("info-panel");

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/icons/hotel.png")));
        image.setFitWidth(260);
        image.setFitHeight(150);
        image.setPreserveRatio(true);

        Label namaHotel = new Label("Dparagon Matraman");
        namaHotel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lokasi = new Label("Matraman, Jakarta Timur");

        HBox ratingBox = new HBox(5);
        ImageView starIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/star.png")));
        starIcon.setFitWidth(18);
        starIcon.setFitHeight(18);
        Label rating = new Label("4.8/5 (1000 review)");
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

        infoPanel.getChildren().addAll(image, namaHotel, lokasi, ratingBox, fasilitas);
        infoPanel.setPrefWidth(300);

        VBox reviewList = new VBox(15);
        reviewList.setPadding(new Insets(20));

        List<Ulasan> dummy = Arrays.asList(
                new Ulasan("Aulia", "Feb 17, 2025", "Kamar nya bersih dan nyaman", 4.9),
                new Ulasan("Fina", "Feb 18, 2025", "Suka banget!!!!", 4.9),
                new Ulasan("Sonya", "Feb 19, 2025", "Mantap WiFi nya kenceng", 4.8),
                new Ulasan("Matilda", "Feb 20, 2025", "Suka banget makanannya enak-enak!", 4.8),
                new Ulasan("Ivana", "Feb 21, 2025", "Fasilitasnya sesuai harga, oklah", 4.7)
        );

        for (Ulasan u : dummy) {
            VBox card = new VBox(5);
            card.getStyleClass().add("review-card");

            Label avatar = new Label(u.getNama().substring(0, 1));
            avatar.getStyleClass().add("avatar");

            Label nama = new Label(u.getNama());
            nama.getStyleClass().add("user-name");

            Label tanggal = new Label(u.getTanggal());
            tanggal.getStyleClass().add("comment-date");

            VBox namaTanggal = new VBox(nama, tanggal);

            HBox headerReview = new HBox(avatar, namaTanggal);
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

    private static class Ulasan {
        private final String nama;
        private final String tanggal;
        private final String komentar;
        private final double rating;

        public Ulasan(String nama, String tanggal, String komentar, double rating) {
            this.nama = nama;
            this.tanggal = tanggal;
            this.komentar = komentar;
            this.rating = rating;
        }

        public String getNama() { return nama; }
        public String getTanggal() { return tanggal; }
        public String getKomentar() { return komentar; }
        public double getRating() { return rating; }
    }
}