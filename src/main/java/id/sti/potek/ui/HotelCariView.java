package id.sti.potek.ui;

import java.time.LocalDate;
import java.util.List;

import id.sti.potek.controller.KamarController;
import id.sti.potek.model.Kamar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        VBox kotaGroup = createDropdownField("Cari", "/icons/icon_location_green.png", "Masukkan Kota");
        ComboBox<String> kotaDropdown = (ComboBox<String>) kotaGroup.getChildren().get(1);
        List<String> kotaDariDB = kamarController.getAllLokasi();
        kotaDropdown.getItems().addAll(kotaDariDB);


        VBox tanggalLabelBox = createLabeledOnly("Tanggal", "/icons/icon_calender.png");

        DatePicker checkInPicker = new DatePicker();
        checkInPicker.setPromptText("Masukkan Tanggal Check In");
        checkInPicker.setPrefWidth(170);

        TextField hariField = new TextField();
        hariField.setPromptText("Masukkan Jumlah Hari Menginap");
        hariField.setPrefWidth(170);

        HBox tanggalInputRow = new HBox(12, checkInPicker, hariField);
        tanggalInputRow.setAlignment(Pos.CENTER_LEFT);

        VBox tanggalGroup = new VBox(8, tanggalLabelBox, tanggalInputRow);
        tanggalGroup.getStyleClass().add("field-group");

        VBox tipeKamarGroup = createDropdownField("Tipe Kamar", "/icons/person_icon.png", "Masukkan Tipe Kamar");
        ((ComboBox<String>) tipeKamarGroup.getChildren().get(1)).getItems().addAll("Single", "Double", "Family");

        VBox jumlahKamarGroup = createLabeledField("Jumlah", "/icons/person_icon.png", "Masukkan Jumlah Kamar");

        HBox kamarRow = new HBox(12, tipeKamarGroup, jumlahKamarGroup);
        kamarRow.setAlignment(Pos.CENTER);

        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        formContainer.getChildren().addAll(kotaGroup, tanggalGroup, kamarRow);

        VBox content = new VBox(30, banner, formContainer, pesanBtn);
        content.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane(content);
        root.getStyleClass().add("main-background");
        StackPane.setMargin(content, new Insets(30, 0, 30, 0));

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/hotel_cari.css").toExternalForm());

        // Ganti bagian pesanBtn.setOnAction dengan kode ini:

        pesanBtn.setOnAction(e -> {
            try {
                LocalDate tanggal = checkInPicker.getValue();
                String hariStr = hariField.getText();
                ComboBox<String> tipeDropdown = (ComboBox<String>) tipeKamarGroup.getChildren().get(1);
                TextField jumlahField = (TextField) jumlahKamarGroup.getChildren().get(1);

                String kota = kotaDropdown.getValue();
                String tipe = tipeDropdown.getValue();
                String jumlahStr = jumlahField.getText();

                if (kota == null || tanggal == null || hariStr.isEmpty() || tipe == null || jumlahStr.isEmpty()) {
                    showPopup("Data Tidak Lengkap", "Isi semua kolom terlebih dahulu.");
                    return;
                }

                int malam;
                int jumlahKamar;
                try {
                    malam = Integer.parseInt(hariStr);
                    jumlahKamar = Integer.parseInt(jumlahStr);
                    if (malam <= 0 || jumlahKamar <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    showPopup("Input Tidak Valid", "Jumlah hari dan kamar harus berupa angka positif.");
                    return;
                }

                LocalDate checkout = tanggal.plusDays(malam);
                List<Kamar> hasil = kamarController.cariKamar(kota, tipe, jumlahKamar);

                if (hasil.isEmpty()) {
                    showPopup("Tidak Tersedia", "Hotel tidak tersedia pada kriteria tersebut.");
                } else {
                    try {
                        Stage newStage = new Stage();

                    } catch (Exception ex) {
                        String errorMsg = ex.getMessage();
                        if (errorMsg == null) errorMsg = ex.getClass().getSimpleName();

                        showPopup("Error View", "Detail error: " + errorMsg);

                        ex.printStackTrace();
                    }
                }
            } catch (Exception globalEx) {
                showPopup("Error Global", "Error: " + globalEx.getClass().getSimpleName() +
                        " - " + (globalEx.getMessage() != null ? globalEx.getMessage() : "Unknown"));
                globalEx.printStackTrace();
            }
        });
            stage.setScene(scene);
        stage.setTitle("Cari Hotel");
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createDropdownField(String labelText, String iconPath, String prompt) {
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

        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.setPromptText(prompt);
        dropdown.setPrefWidth(350);
        container.getChildren().add(dropdown);

        return container;
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

    private VBox createLabeledOnly(String labelText, String iconPath) {
        VBox container = new VBox(8);
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

        Button closeBtn = new Button("\u2715");
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