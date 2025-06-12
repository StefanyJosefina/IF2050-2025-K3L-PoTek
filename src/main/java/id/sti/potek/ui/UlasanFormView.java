package id.sti.potek.ui;

import id.sti.potek.controller.UlasanController;
import id.sti.potek.util.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UlasanFormView {

    private final List<ImageView> stars = new ArrayList<>();
    private int selectedRating = 0;
    private final UlasanController ulasanController = new UlasanController();

    private final String idPesananKamar;
    private String idUser;
    private String idKamar;

    public UlasanFormView(String idPesananKamar) {
        this.idPesananKamar = idPesananKamar;
    }

    public void start(Stage stage) {
        String namaPemesan = "";
        String checkIn = "";
        String checkOut = "";
        int totalHarga = 0;
        String namaHotel = "";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("""
                 SELECT pk.idUser, pk.idKamar, pk.namaPemesan, pk.tanggalCheckIn, pk.tanggalCheckOut, pk.totalHarga, k.namaHotel
                 FROM pesanankamar pk
                 JOIN kamar k ON pk.idKamar = k.idKamar
                 WHERE pk.idPesananKamar = ?
             """)) {

            stmt.setString(1, idPesananKamar);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idUser = rs.getString("idUser");
                idKamar = rs.getString("idKamar");
                namaPemesan = rs.getString("namaPemesan");
                
                // Get dates as strings and parse them manually
                String checkInStr = rs.getString("tanggalCheckIn");
                String checkOutStr = rs.getString("tanggalCheckOut");
                
                if (checkInStr != null && checkOutStr != null) {
                    try {
                        // Parse the date strings and format them
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH);
                        
                        checkIn = LocalDate.parse(checkInStr, inputFormatter).format(outputFormatter);
                        checkOut = LocalDate.parse(checkOutStr, inputFormatter).format(outputFormatter);
                    } catch (Exception dateEx) {
                        System.out.println("Date parsing error: " + dateEx.getMessage());
                        checkIn = checkInStr;  // Fallback to original string
                        checkOut = checkOutStr;
                    }
                }
                
                totalHarga = rs.getInt("totalHarga");
                namaHotel = rs.getString("namaHotel");
                
                System.out.println("Debug - Data retrieved:");
                System.out.println("Hotel: " + namaHotel);
                System.out.println("Check-in: " + checkIn);
                System.out.println("Check-out: " + checkOut);
                System.out.println("Total: " + totalHarga);
            } else {
                System.out.println("No data found for idPesananKamar: " + idPesananKamar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving data: " + e.getMessage());
        }

        Label title = new Label("Riwayat Pemesanan Hotel");
        title.getStyleClass().add("title-header");

        HBox titleBar = new HBox(title);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.getStyleClass().add("title-bar");

        Label hotelLabel = new Label(namaHotel.isEmpty() ? "Hotel Name Not Found" : namaHotel);
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

        addDetailRow(detailGrid, 0, "ID Pemesanan", idPesananKamar);
        addDetailRow(detailGrid, 1, "ID Kamar", idKamar);
        
        String tanggalPenginapan = "";
        if (!checkIn.isEmpty() && !checkOut.isEmpty()) {
            tanggalPenginapan = checkIn + " - " + checkOut;
        } else {
            tanggalPenginapan = "Date not available";
        }
        addDetailRow(detailGrid, 2, "Tanggal Penginapan", tanggalPenginapan);
        
        addDetailRow(detailGrid, 3, "Nama Pemesan", namaPemesan.isEmpty() ? "Name not found" : namaPemesan);
        
        String formattedPrice = "Rp " + NumberFormat.getNumberInstance(Locale.GERMANY).format(totalHarga);
        addDetailRow(detailGrid, 4, "Harga Total", formattedPrice, "#FF0000");

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
                showCustomPopup("Harap isi semua data ulasan!", false);
            } else {
                try {
                    int userIdInt = Integer.parseInt(idUser.replaceAll("[^\\d]", ""));
                    ulasanController.kirimUlasan(userIdInt, idKamar, selectedRating, komentar);
                    showCustomPopup("Ulasan berhasil dikirim!", true);
                    new UlasanListView(idKamar).start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showCustomPopup("Gagal mengirim ulasan!", false);
                }
            }
        });

        HBox ratingSection = new HBox(10, ratingLabel, starBox);
        ratingSection.setAlignment(Pos.CENTER_LEFT);
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
        Stage popupStage = new Stage();
        popupStage.setAlwaysOnTop(true);

        if (success) {
            popupStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
            Rectangle overlay = new Rectangle(900, 645);
            overlay.setFill(javafx.scene.paint.Color.web("#F8C8DC", 0.6));

            Label msg = new Label(message);
            msg.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d94a64;");

            VBox popupBox = new VBox(msg);
            popupBox.setAlignment(Pos.CENTER);
            popupBox.setPadding(new Insets(30));
            popupBox.setStyle("-fx-background-color: white; -fx-background-radius: 20px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);");

            StackPane root = new StackPane(overlay, popupBox);
            root.setAlignment(Pos.CENTER);

            Scene scene = new Scene(root, 380, 130);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            popupStage.setScene(scene);
            popupStage.show();

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(popupStage::close);
            }).start();
        } else {
            Label msg = new Label(message);
            msg.setStyle("-fx-font-size: 14px; -fx-text-fill: #222;");
            Button okBtn = new Button("OK");
            okBtn.setStyle("-fx-background-color: #86A788; -fx-text-fill: white; -fx-font-size: 14px;" +
                    "-fx-font-weight: bold; -fx-padding: 8 20 8 20; -fx-background-radius: 8px;");
            okBtn.setOnAction(e -> popupStage.close());

            VBox layout = new VBox(20, msg, okBtn);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(30));
            layout.setStyle("-fx-background-color: white;" +
                    "-fx-border-color: lightgray; -fx-border-width: 1;" +
                    "-fx-border-radius: 10px; -fx-background-radius: 10px;");

            Scene scene = new Scene(layout, 350, 150);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        }
    }
}