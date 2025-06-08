package id.sti.potek.ui;

import id.sti.potek.model.Pemesanan;
import id.sti.potek.model.Kamar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.text.NumberFormat;
import java.util.Locale;

public class HotelPesanView {
    public void start(Stage stage, Kamar kamar, String checkin, String checkout, int malam) {
        // Header
        Label headerLabel = new Label("Pesan Hotel");
        headerLabel.getStyleClass().add("header-title");
        StackPane header = new StackPane(headerLabel);
        header.getStyleClass().add("header-bar");

        Label pemesanTitle = new Label("Detail Pemesan");
        pemesanTitle.getStyleClass().add("section-title");
        Label pemesanSubtitle = new Label("Detail kontak ini akan digunakan untuk pengiriman e-tiket dan keperluan");
        pemesanSubtitle.getStyleClass().add("section-sub");
        TextField namaField = new TextField();
        namaField.setPromptText("Masukkan Nama Lengkap");
        Label noteNama = new Label("*Seperti di KTP/SIM/Paspor");
        TextField hpField = new TextField();
        hpField.setPromptText("Masukkan Nomor HP");
        TextField emailField = new TextField();
        emailField.setPromptText("Masukkan Email");

        VBox pemesanBox = new VBox(8, pemesanTitle, pemesanSubtitle, namaField, noteNama, hpField, emailField);
        pemesanBox.setPadding(new Insets(16));
        pemesanBox.getStyleClass().add("form-box");

        VBox fasilitasBox = new VBox(
                new Label("\uD83D\uDC64 1 Tamu"),
                new Label("1 Kasur Single"),
                new Label("WiFi"),
                new Label("Sarapan"),
                new Label("Bebas Asap Rokok")
        );
        fasilitasBox.setSpacing(2);

        Label namaKamarLabel = new Label("Kamar 1 : Nama Lengkap");
        VBox penginapanBox = new VBox(10,
                new Label("Detail Penginapan"),
                fasilitasBox,
                new Separator(),
                namaKamarLabel
        );
        penginapanBox.setPadding(new Insets(16));
        penginapanBox.getStyleClass().add("form-box");

        VBox leftPanel = new VBox(20, pemesanBox, penginapanBox);
        leftPanel.setPrefWidth(400);

        Label namaHotel = new Label(kamar.getNamaHotel());
        namaHotel.getStyleClass().add("hotel-name");
        Label tanggal = new Label(checkin + " - " + checkout);
        Label malamKamar = new Label(malam + " Malam - 1 Kamar");
        VBox detailRingkasan = new VBox(namaHotel, tanggal, malamKamar);
        detailRingkasan.setSpacing(5);

        HBox hotelBox = new HBox(10, new Circle(20), detailRingkasan);
        VBox ringkasanCard = new VBox(15, hotelBox);
        ringkasanCard.setPadding(new Insets(20));
        ringkasanCard.getStyleClass().add("ringkasan-box");

        HBox totalBox = new HBox();
        Label totalText = new Label("Total Pembayaran");
        totalText.getStyleClass().add("total-text");
        Label totalHarga = new Label("IDR " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format(kamar.getHarga()));
        totalHarga.getStyleClass().add("total-amount");
        totalBox.getChildren().addAll(totalText, totalHarga);
        totalBox.setSpacing(10);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        ringkasanCard.getChildren().add(totalBox);

        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");
        Label note = new Label("*akan lanjut ke pembayaran");

        VBox rightPanel = new VBox(25, ringkasanCard, pesanBtn, note);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        ImageView logo = new ImageView(new Image("/icons/logo.png"));
        logo.setFitWidth(80);
        logo.setFitHeight(80);
        StackPane.setAlignment(logo, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(logo, new Insets(0, 20, 20, 0));

        HBox main = new HBox(40, leftPanel, rightPanel);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(20));

        VBox rootLayout = new VBox(header, main);
        rootLayout.setSpacing(20);
        rootLayout.getStyleClass().add("pesan-root");

        StackPane root = new StackPane(rootLayout, logo);
        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/pesan_hotel.css").toExternalForm());
        stage.setTitle("Pesan Hotel");
        stage.setScene(scene);
        stage.show();
    }
}