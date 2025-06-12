package id.sti.potek.ui;

import java.util.List;

import id.sti.potek.controller.TiketController;
import id.sti.potek.model.Tiket;
import id.sti.potek.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TiketCariView {

    private User loggedInUser;

    public TiketCariView(User user) {
        this.loggedInUser = user;
    }

    public TiketCariView() {
        this(null); // Konstruktor tanpa user untuk pengguna yang belum login
    }

    public void start(Stage stage) {
        // Banner Header
        ImageView bannerIcon = new ImageView(new Image("/icons/icon_transportasi.png"));
        bannerIcon.setFitHeight(30);
        bannerIcon.setFitWidth(40);

        Text bannerText = new Text("Transportasi");
        bannerText.getStyleClass().add("banner-text");

        HBox banner = new HBox(10, bannerIcon, bannerText);
        banner.setAlignment(Pos.CENTER);
        banner.getStyleClass().add("banner-header");

        // main yang pink hot
        VBox formContainer = new VBox(20);
        formContainer.getStyleClass().add("form-container");
        formContainer.setAlignment(Pos.TOP_CENTER);
        formContainer.setPadding(new Insets(20, 30, 20, 30));
        
        // asal
        ImageView asalIcon = new ImageView(new Image("/icons/icon_location_green.png"));
        asalIcon.setFitWidth(18);
        asalIcon.setFitHeight(24);
        TextField asalField = new TextField();
        asalField.setPromptText("Masukkan Kota");
        VBox asalBox = createFieldWithLabelAndIcon("Asal", asalIcon, asalField);
        
        // tujuan
        ImageView tujuanIcon = new ImageView(new Image("/icons/icon_location_pink.png"));
        tujuanIcon.setFitWidth(18);
        tujuanIcon.setFitHeight(24);
        TextField tujuanField = new TextField();
        tujuanField.setPromptText("Masukkan Kota");
        VBox tujuanBox = createFieldWithLabelAndIcon("Tujuan", tujuanIcon, tujuanField);
        
        // box for asal and tujuan
        VBox originDestContainer = new VBox(12, asalBox, tujuanBox);
        originDestContainer.getStyleClass().add("field-group");
        formContainer.getChildren().add(originDestContainer);
        
        // tanggal
        ImageView tanggalIcon = new ImageView(new Image("/icons/icon_calendar.png"));
        tanggalIcon.setFitWidth(20);
        tanggalIcon.setFitHeight(20);
        TextField tanggalField = new TextField();
        tanggalField.setPromptText("yyyy-mm-dd");
        VBox tanggalBox = createFieldWithLabelAndIcon("Tanggal", tanggalIcon, tanggalField);
        
        VBox dateContainer = new VBox(tanggalBox);
        dateContainer.getStyleClass().add("field-group");
        formContainer.getChildren().add(dateContainer);

        // Book button
        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");
        formContainer.getChildren().add(pesanBtn);

        // Main layout
        VBox content = new VBox(60, banner, formContainer);
        content.setAlignment(Pos.TOP_CENTER);
        
        StackPane root = new StackPane(content);
        root.getStyleClass().add("main-background");
       
        StackPane.setMargin(content,new Insets(30, 0, 20, 0));
        
        
        // Scene setup
        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/cari_tiket.css").toExternalForm());
        
        // Button handler
        pesanBtn.setOnAction(e -> {
            String asal = asalField.getText();
            String tujuan = tujuanField.getText();
            String tanggal = tanggalField.getText();

            TiketController controller = new TiketController();
            List<Tiket> hasil = controller.cariTiket(asal, tujuan, tanggal);
            if (hasil.isEmpty()) {
                // Show dialog if no tickets found
                showDialog(" Tiket tidak ditemukan untuk rute tersebut.");
                return;
            }else {
                // PERBAIKAN: Pass loggedInUser ke TiketPilihView
                TiketPilihView tiketPilihView = new TiketPilihView(loggedInUser);
                tiketPilihView.start(stage, hasil, asal, tujuan, tanggal);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Transportasi");
        stage.centerOnScreen();
        stage.show();
    }

    // field with label and icon 
    private VBox createFieldWithLabelAndIcon(String labelText, ImageView icon, TextField field) {
        Text label = new Text(labelText);
        label.getStyleClass().add("field-label");
        
        HBox labelIconContainer = new HBox(10);
        labelIconContainer.setAlignment(Pos.CENTER_LEFT);
        labelIconContainer.getChildren().addAll(label, icon);
        
        VBox fieldContainer = new VBox(12);
        fieldContainer.getChildren().addAll(labelIconContainer, field);
        
        return fieldContainer;
    }
    public void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
