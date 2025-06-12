package id.sti.potek.ui;

import java.time.LocalDate;
import java.util.ArrayList;
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
        ImageView logo = new ImageView();
        try {
            logo.setImage(new Image("/icons/kasur-icon.png"));
            logo.setFitHeight(28);
            logo.setFitWidth(28);
        } catch (Exception e) {
            System.out.println("Icon tidak ditemukan: " + e.getMessage());
            logo.setFitHeight(28);
            logo.setFitWidth(28);
        }

        Text title = new Text("Hotel");
        title.getStyleClass().add("banner-text");

        HBox banner = new HBox(10, logo, title);
        banner.setAlignment(Pos.CENTER);
        banner.getStyleClass().add("banner-header");

        VBox formContainer = new VBox(16);
        formContainer.getStyleClass().add("form-container");
        formContainer.setAlignment(Pos.TOP_CENTER);
        formContainer.setPadding(new Insets(25, 60, 25, 60)); 

        VBox kotaGroup = createDropdownField("Cari", "/icons/icon_location_green.png", "Masukkan Kota");
        ComboBox<String> kotaDropdown = (ComboBox<String>) kotaGroup.getChildren().get(1);
        
        try {
            List<String> kotaDariDB = kamarController.getAllLokasi();
            if (kotaDariDB != null && !kotaDariDB.isEmpty()) {
                kotaDropdown.getItems().addAll(kotaDariDB);
            } else {
                kotaDropdown.getItems().addAll("Jakarta", "Bandung", "Surabaya", "Medan", "Semarang");
            }
        } catch (Exception e) {
            System.out.println("Error loading cities: " + e.getMessage());
            kotaDropdown.getItems().addAll("Jakarta", "Bandung", "Surabaya", "Medan", "Semarang");
        }

        VBox tanggalLabelBox = createLabeledOnly("Tanggal", "/icons/icon_calender.png");

        DatePicker checkInPicker = new DatePicker();
        checkInPicker.setPromptText("Masukkan Tanggal Check In");
        checkInPicker.setPrefWidth(350); 
        checkInPicker.setMaxWidth(350);
        checkInPicker.setValue(LocalDate.now()); 

        TextField hariField = new TextField();
        hariField.setPromptText("Masukkan Jumlah Hari");
        hariField.setPrefWidth(350);
        hariField.setMaxWidth(350);

        HBox tanggalInputRow = new HBox(20, checkInPicker, hariField); 
        tanggalInputRow.setAlignment(Pos.CENTER);

        VBox tanggalGroup = new VBox(8, tanggalLabelBox, tanggalInputRow);
        tanggalGroup.getStyleClass().add("field-group");

        VBox tipeKamarGroup = createDropdownField("Tipe Kamar", "/icons/person_icon.png", "Masukkan Tipe Kamar");
        ComboBox<String> tipeDropdown = (ComboBox<String>) tipeKamarGroup.getChildren().get(1);
        tipeDropdown.getItems().addAll("Single", "Double", "Family");

        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");
        pesanBtn.setPrefWidth(720); 
        pesanBtn.setMaxWidth(720);

        formContainer.getChildren().addAll(kotaGroup, tanggalGroup, tipeKamarGroup);

        VBox content = new VBox(30, banner, formContainer, pesanBtn);
        content.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane(content);
        root.getStyleClass().add("main-background");
        StackPane.setMargin(content, new Insets(30, 0, 30, 0));

        Scene scene = new Scene(root, 900, 645);
        
        try {
            String cssPath = getClass().getResource("/css/hotel_cari.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.out.println("CSS file tidak ditemukan: " + e.getMessage());
            root.setStyle("-fx-background-color: #f5f5f5;");
            pesanBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        }

        pesanBtn.setOnAction(e -> {
            try {
                LocalDate tanggal = checkInPicker.getValue();
                String hariStr = hariField.getText().trim();

                String kota = kotaDropdown.getValue();
                String tipe = tipeDropdown.getValue();

                if (kota == null || kota.isEmpty()) {
                    showPopup("Data Tidak Lengkap", "Pilih kota terlebih dahulu.");
                    return;
                }
                
                if (tanggal == null) {
                    showPopup("Data Tidak Lengkap", "Pilih tanggal check-in.");
                    return;
                }
                
                if (tanggal.isBefore(LocalDate.now())) {
                    showPopup("Tanggal Tidak Valid", "Tanggal check-in tidak boleh di masa lalu.");
                    return;
                }
                
                if (hariStr.isEmpty()) {
                    showPopup("Data Tidak Lengkap", "Masukkan jumlah hari menginap.");
                    return;
                }
                
                if (tipe == null || tipe.isEmpty()) {
                    showPopup("Data Tidak Lengkap", "Pilih tipe kamar.");
                    return;
                }

                int malam;
                try {
                    malam = Integer.parseInt(hariStr);
                    
                    if (malam <= 0) {
                        showPopup("Input Tidak Valid", "Jumlah hari harus lebih dari 0.");
                        return;
                    }
                    if (malam > 365) {
                        showPopup("Input Tidak Valid", "Jumlah hari maksimal 365 hari.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showPopup("Input Tidak Valid", "Jumlah hari harus berupa angka.");
                    return;
                }

                LocalDate checkout = tanggal.plusDays(malam);
                
                List<Kamar> hasil = null;
                try {
                    hasil = kamarController.cariKamar(kota, tipe, 1); 
                } catch (Exception ex) {
                    showPopup("Error Database", "Gagal mencari kamar: " + ex.getMessage());
                    return;
                }

                if (hasil == null || hasil.isEmpty()) {
                    showPopup("Tidak Tersedia", "Hotel tidak tersedia pada kriteria tersebut.");
                } else {
                    try {
                        String checkinStr = tanggal.toString();
                        String checkoutStr = checkout.toString();
                        List<String> unlocked = new ArrayList<>();

                        Stage newStage = new Stage();
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        
                        HotelPilihView pilihView = new HotelPilihView(hasil, unlocked, checkinStr, checkoutStr, malam);
                        pilihView.start(newStage);

                        stage.close();

                    } catch (Exception ex) {
                        String errorMsg = ex.getMessage();
                        if (errorMsg == null) errorMsg = ex.getClass().getSimpleName();
                        showPopup("Error", "Gagal membuka halaman pilih hotel: " + errorMsg);
                        ex.printStackTrace();
                    }
                }
            } catch (Exception globalEx) {
                showPopup("Error", "Terjadi kesalahan: " + 
                        (globalEx.getMessage() != null ? globalEx.getMessage() : globalEx.getClass().getSimpleName()));
                globalEx.printStackTrace();
            }
        });

        stage.setScene(scene);
        stage.setTitle("Cari Hotel");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createDropdownField(String labelText, String iconPath, String prompt) {
        VBox container = new VBox(8);
        container.getStyleClass().add("field-group");

        if (labelText != null && iconPath != null) {
            ImageView icon = new ImageView();
            try {
                icon.setImage(new Image(iconPath));
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            } catch (Exception e) {
                System.out.println("Icon tidak ditemukan: " + iconPath);
                // Create placeholder or skip icon
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            }

            Text label = new Text(labelText);
            label.getStyleClass().add("field-label");

            HBox labelRow = new HBox(10, icon, label);
            labelRow.setAlignment(Pos.CENTER_LEFT);
            container.getChildren().add(labelRow);
        }

        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.setPromptText(prompt);
        dropdown.setPrefWidth(720); 
        dropdown.setMaxWidth(720);
        container.getChildren().add(dropdown);

        return container;
    }

    private VBox createLabeledField(String labelText, String iconPath, String prompt) {
        VBox container = new VBox(8);
        container.getStyleClass().add("field-group");

        if (labelText != null && iconPath != null) {
            ImageView icon = new ImageView();
            try {
                icon.setImage(new Image(iconPath));
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            } catch (Exception e) {
                System.out.println("Icon tidak ditemukan: " + iconPath);
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            }

            Text label = new Text(labelText);
            label.getStyleClass().add("field-label");

            HBox labelRow = new HBox(10, icon, label);
            labelRow.setAlignment(Pos.CENTER_LEFT);
            container.getChildren().add(labelRow);
        }

        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(720);
        field.setMaxWidth(720);
        container.getChildren().add(field);

        return container;
    }

    private VBox createLabeledOnly(String labelText, String iconPath) {
        VBox container = new VBox(8);
        if (labelText != null && iconPath != null) {
            ImageView icon = new ImageView();
            try {
                icon.setImage(new Image(iconPath));
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            } catch (Exception e) {
                System.out.println("Icon tidak ditemukan: " + iconPath);
                icon.setFitHeight(20);
                icon.setFitWidth(20);
            }

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
        popupStage.setTitle("Informasi");

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: white; -fx-border-radius: 12px; -fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 2);");

        Label title = new Label(titleText);
        title.setStyle("-fx-text-fill: #ea4c5d; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subtitle = new Label(subtitleText);
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-wrap-text: true;");
        subtitle.setMaxWidth(280);

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #ea4c5d; -fx-text-fill: white; -fx-padding: 8px 20px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        okBtn.setOnAction(e -> popupStage.close());

        box.getChildren().addAll(title, subtitle, okBtn);

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 200);
        popupStage.setScene(scene);
        popupStage.centerOnScreen();
        popupStage.showAndWait();
    }
}