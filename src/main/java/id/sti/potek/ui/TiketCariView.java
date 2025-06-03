package id.sti.potek.ui;

import id.sti.potek.controller.TiketController;
import id.sti.potek.model.Tiket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class TiketCariView {

    public void start(Stage stage) {
        // Banner Header
        ImageView bannerIcon = new ImageView(new Image("/icons/icon_transportasi.png"));
        bannerIcon.setFitHeight(34);
        bannerIcon.setFitWidth(50);

        Text bannerText = new Text("Transportasi");
        bannerText.getStyleClass().add("banner-text");

        HBox banner = new HBox(10, bannerIcon, bannerText);
        banner.setAlignment(Pos.CENTER);
        banner.getStyleClass().add("banner-header");

        // Field: Asal
        ImageView asalIcon = new ImageView(new Image("/icons/icon_location_green.png"));
        asalIcon.setFitWidth(24);
        asalIcon.setFitHeight(24);
        TextField asalField = new TextField();
        asalField.setPromptText("Masukkan Kota");
        VBox asalBox = createLabeledField("Asal", asalIcon, asalField);

        // Field: Tujuan
        ImageView tujuanIcon = new ImageView(new Image("/icons/icon_location_pink.png"));
        tujuanIcon.setFitWidth(24);
        tujuanIcon.setFitHeight(24);
        TextField tujuanField = new TextField();
        tujuanField.setPromptText("Masukkan Kota");
        VBox tujuanBox = createLabeledField("Tujuan", tujuanIcon, tujuanField);

        // Field: Tanggal
        ImageView tanggalIcon = new ImageView(new Image("/icons/icon_calendar.png"));
        tanggalIcon.setFitWidth(24);
        tanggalIcon.setFitHeight(24);
        TextField tanggalField = new TextField();
        tanggalField.setPromptText("Masukkan Tanggal Berangkat : yyyy-mm-dd");
        VBox tanggalBox = createLabeledField("Tanggal", tanggalIcon, tanggalField);

        // Tombol Pesan
        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        VBox formContainer = new VBox(24, asalBox, tujuanBox, tanggalBox, pesanBtn);
        formContainer.getStyleClass().add("form-container");
        formContainer.setAlignment(Pos.TOP_CENTER);

        VBox content = new VBox(banner, formContainer);
        content.setAlignment(Pos.TOP_CENTER);
        content.setSpacing(30);

        StackPane root = new StackPane(content);
        root.getStyleClass().add("main-background");
        StackPane.setAlignment(content, Pos.TOP_CENTER);
        StackPane.setMargin(content, new Insets(20, 0, 20, 0));

        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("/css/cari_tiket.css").toExternalForm());

        // Tombol handler
        pesanBtn.setOnAction(e -> {
            String asal = asalField.getText();
            String tujuan = tujuanField.getText();
            String tanggal = tanggalField.getText();

            TiketController controller = new TiketController();
            List<Tiket> hasil = controller.cariTiket(asal, tujuan, tanggal);
            new TiketPilihView().start(stage, hasil);
        });

        stage.setScene(scene);
        stage.setTitle("Transportasi");
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createLabeledField(String label, ImageView icon, TextField field) {
        Text labelText = new Text(label);
        HBox fieldRow = new HBox(10, icon, field);
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        VBox box = new VBox(8, labelText, fieldRow);
        box.getStyleClass().add("field-group");
        return box;
    }
}
