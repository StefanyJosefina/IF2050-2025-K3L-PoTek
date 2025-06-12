package id.sti.potek.ui;

import java.util.ArrayList;
import java.util.List;

import id.sti.potek.controller.UlasanController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;


public class UlasanFormView {

    private final List<ImageView> stars = new ArrayList<>();
    private int selectedRating = 0;
    private final UlasanController ulasanController = new UlasanController();

    public void start(Stage stage) {
        Label title = new Label("Riwayat Pemesanan Hotel");
        title.getStyleClass().add("title-header");

        HBox titleBar = new HBox(title);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.getStyleClass().add("title-bar");

        Label hotelLabel = new Label("DParagon Matraman");
        hotelLabel.getStyleClass().add("hotel-name");

        Label statusLabel = new Label("Sudah Membayar");
        statusLabel.getStyleClass().add("status-label");

        HBox headerBar = new HBox(hotelLabel, new Pane(), statusLabel);
        HBox.setHgrow(headerBar.getChildren().get(1), Priority.ALWAYS);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(0, 20, 0, 20));

        GridPane detailGrid = new GridPane();
        detailGrid.setHgap(20);
        detailGrid.setVgap(10);
        detailGrid.setPadding(new Insets(10, 20, 10, 20));

        addDetailRow(detailGrid, 0, "Tanggal Penginapan", "Rab, 17 Aug 1945 - Rab, 18 Aug 1945");
        addDetailRow(detailGrid, 1, "Tanggal Pesan", "Senin, 15 April 2025 10:08:28");
        addDetailRow(detailGrid, 2, "Nama Pemesan", "Aulia Azka Azzahra");
        addDetailRow(detailGrid, 3, "No Kamar", "C05");
        addDetailRow(detailGrid, 4, "Harga Total", "Rp 333.000", "#FF0000");

        VBox detailBox = new VBox(10, headerBar, new Separator(), detailGrid, new Separator());

        Label ulasanLabel = new Label("Berikan ulasan");
        ulasanLabel.getStyleClass().add("ulasan-label");

        Label ratingLabel = new Label("Beri bintang:");
        ratingLabel.getStyleClass().add("rating-label");

        HBox starBox = new HBox(5);
        for (int i = 1; i <= 5; i++) {
            ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/icons/star.png")));
            star.setFitWidth(30);
            star.setFitHeight(30);
            int rating = i;
            star.setOnMouseClicked(e -> selectRating(rating));
            stars.add(star);
            starBox.getChildren().add(star);
        }

        HBox ratingSection = new HBox(10, ratingLabel, starBox);
        ratingSection.setAlignment(Pos.CENTER_LEFT);

        TextArea commentField = new TextArea();
        commentField.setPromptText("Tulis Ulasan ....");
        commentField.setWrapText(true);
        commentField.setPrefRowCount(3);
        commentField.setMaxWidth(700);
        commentField.getStyleClass().add("comment-box");

        Button submit = new Button("➤");
        submit.getStyleClass().add("submit-button");
        submit.setOnAction(e -> {
            String komentar = commentField.getText().trim();
            if (selectedRating == 0 || komentar.isEmpty()) {
                showCustomPopup("Harap isi semua data ulasan!", false);  // ⛔ jika gagal
            } else {
                ulasanController.kirimUlasan(1, "KMR001", selectedRating, komentar);
                showCustomPopup("Ulasan berhasil dikirim!", true);       // ✅ jika berhasil
                new UlasanListView().start(stage);
            }
        });
        

        HBox inputBox = new HBox(commentField, submit);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setSpacing(10);

        VBox ulasanBox = new VBox(10, ulasanLabel, ratingSection, inputBox);
        ulasanBox.setPadding(new Insets(10, 20, 20, 20));

        VBox root = new VBox(10, titleBar, detailBox, ulasanBox);
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/ulasanform.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void selectRating(int rating) {
        selectedRating = rating;
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).setOpacity(i < rating ? 1.0 : 0.3);
        }
    }

    private void addDetailRow(GridPane grid, int row, String label, String value) {
        addDetailRow(grid, row, label, value, "#000000");
    }

    private void addDetailRow(GridPane grid, int row, String label, String value, String color) {
        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-text-fill: #999; -fx-font-size: 16px;");

        Label valueNode = new Label(value);
        valueNode.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        grid.add(labelNode, 0, row);
        grid.add(valueNode, 1, row);
    }

    private void showCustomPopup(String message, boolean success) {
        if (success) {
            Stage popupStage = new Stage();
            popupStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
            popupStage.setAlwaysOnTop(true);
    
            // Background pink transparan
            Rectangle overlay = new Rectangle(900, 645); // atau bind jika mau fleksibel
            overlay.setFill(javafx.scene.paint.Color.web("#F8C8DC", 0.6));
    
            // Pesan
            Label msg = new Label(message);
            msg.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d94a64;");
    
            // Box putih bundar
            VBox popupBox = new VBox(msg);
            popupBox.setAlignment(Pos.CENTER);
            popupBox.setPadding(new Insets(30));
            popupBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 20px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);"
            );
    
            // StackPane = overlay + popup box
            StackPane root = new StackPane(overlay, popupBox);
            root.setAlignment(Pos.CENTER);
    
            Scene scene = new Scene(root, 380, 130);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            popupStage.setScene(scene);
            popupStage.show();
    
            // Auto-close setelah 2 detik
            new Thread(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(popupStage::close);
            }).start();
        } else {
            Stage popupStage = new Stage();
            popupStage.setTitle("Message");
            popupStage.setAlwaysOnTop(true);
    
            Label msg = new Label(message);
            msg.setStyle("-fx-font-size: 14px; -fx-text-fill: #222;");
    
            Button okBtn = new Button("OK");
            okBtn.setStyle(
                "-fx-background-color: #86A788;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 8 20 8 20;" +
                "-fx-background-radius: 8px;"
            );
            okBtn.setOnAction(e -> popupStage.close());
    
            VBox layout = new VBox(20, msg, okBtn);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(30));
            layout.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: lightgray;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
            );
    
            Scene scene = new Scene(layout, 350, 150);
            popupStage.setScene(scene);
            popupStage.showAndWait(); // Wajib tekan OK
        }
    }
    
    
}