package id.sti.potek.ui;

import id.sti.potek.controller.KamarController;
import id.sti.potek.model.Kamar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class HotelCariView {
    private final KamarController kamarController = new KamarController();

    public void start(Stage stage) {
        ImageView logo = new ImageView(new Image("/icons/kasur-icon.png"));
        logo.setFitHeight(28);
        logo.setFitWidth(28);

        Text title = new Text("Hotel");
        title.getStyleClass().add("banner-text");

        HBox banner = new HBox(10, logo, title);
        banner.setAlignment(Pos.CENTER);
        banner.getStyleClass().add("banner-header");

        VBox formContainer = new VBox(16);
        formContainer.getStyleClass().add("form-container");
        formContainer.setAlignment(Pos.TOP_CENTER);
        formContainer.setPadding(new Insets(25, 40, 25, 40));

        VBox kotaGroup = createLabeledField("Cari", "/icons/icon_location_green.png", "Masukkan Kota");
        VBox tanggalGroup = createLabeledField("Tanggal", "/icons/icon_calender.png", "Masukkan Tanggal Check In");

        Button pesanBtn = new Button("Cari Hotel");
        pesanBtn.getStyleClass().add("pesan-button");

        formContainer.getChildren().addAll(kotaGroup, tanggalGroup);

        VBox content = new VBox(30, banner, formContainer, pesanBtn);
        content.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane(content);
        root.getStyleClass().add("main-background");
        StackPane.setMargin(content, new Insets(30, 0, 30, 0));

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/hotel_cari.css").toExternalForm());

        pesanBtn.setOnAction(e -> {
            TextField kotaField = (TextField) kotaGroup.getChildren().get(1);
            TextField tanggalField = (TextField) tanggalGroup.getChildren().get(1);

            String kota = kotaField.getText().trim();
            String tanggal = tanggalField.getText().trim();

            if (kota.isEmpty() || tanggal.isEmpty()) {
                showPopup("Data Tidak Lengkap", "Isi semua kolom terlebih dahulu.");
                return;
            }

            List<Kamar> hasil = kamarController.cariKamar(kota, tanggal);
            if (hasil.isEmpty()) {
                showPopup("Tidak Tersedia", "Hotel tidak ditemukan pada tanggal tersebut.");
            } else {
                new HotelPilihView(hasil, List.of()).start(stage);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Cari Hotel");
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createLabeledField(String labelText, String iconPath, String prompt) {
        VBox container = new VBox(8);
        container.getStyleClass().add("field-group");

        if (labelText != null && iconPath != null) {
            ImageView icon = new ImageView(new Image(iconPath));
            icon.setFitHeight(20);
            icon.setFitWidth(20);

            Text label = new Text(labelText);
            label.getStyleClass().add("field-label");

            HBox labelRow = new HBox(10, icon, label);
            labelRow.setAlignment(Pos.CENTER_LEFT);
            container.getChildren().add(labelRow);
        }

        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(350);
        container.getChildren().add(field);

        return container;
    }

    private void showPopup(String titleText, String subtitleText) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);

        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-border-radius: 12px; -fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 2);");

        Label title = new Label(titleText);
        title.setStyle("-fx-text-fill: #ea4c5d; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitle = new Label(subtitleText);
        subtitle.setStyle("-fx-text-fill: #ea4c5d; -fx-font-size: 14px;");

        Button closeBtn = new Button("✕");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-text-fill: #333;");
        closeBtn.setOnAction(e -> popupStage.close());

        StackPane closeWrapper = new StackPane(closeBtn);
        closeWrapper.setAlignment(Pos.TOP_RIGHT);

        VBox container = new VBox(closeWrapper, box);
        container.setStyle("-fx-padding: 10px;");

        box.getChildren().addAll(title, subtitle);

        Scene scene = new Scene(container, 320, 180);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}