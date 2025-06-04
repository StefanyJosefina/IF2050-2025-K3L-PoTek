package id.sti.potek.ui;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import id.sti.potek.dao.PemesananDAO;
import id.sti.potek.model.Pemesanan;
import id.sti.potek.model.Tiket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TiketPesanView {

    public void start(Stage stage, Tiket tiket, int noKursi) {
        // Create header
        Label headerLabel = new Label("Pesan Transportasi");
        StackPane headerBar = new StackPane(headerLabel);
        headerBar.getStyleClass().add("header-bar");
        headerBar.setMaxWidth(Double.MAX_VALUE);

        // Add space above header
        Region topSpace = new Region();
        topSpace.setPrefHeight(20);

        // Create form fields for booker details
        TextField namaPemesan = createStyledTextField("Masukkan Nama Lengkap");
        TextField hpPemesan = createStyledTextField("Masukkan Nomor HP");
        TextField emailPemesan = createStyledTextField("Masukkan Email");

        VBox pemesanBox = createFormSection(
            "Detail Pemesan", 
            "Detail kontak ini akan digunakan untuk pengiriman e-tiket dan keperluan",
            namaPemesan, hpPemesan, emailPemesan
        );
        pemesanBox.getStyleClass().add("pemesan-box");

        // Create form fields for passenger details
        TextField namaPenumpang = createStyledTextField("Masukkan Nama Lengkap");
        TextField hpPenumpang = createStyledTextField("Masukkan Nomor HP");
        TextField emailPenumpang = createStyledTextField("Masukkan Email");

        VBox penumpangBox = createFormSection(
            "Detail Penumpang", 
            "*Seperti di KTP/SIM/Paspor",
            namaPenumpang, hpPenumpang, emailPenumpang
        );
        penumpangBox.getStyleClass().add("penumpang-box");

        VBox leftPanel = new VBox(20, pemesanBox, penumpangBox);
        leftPanel.getStyleClass().add("left-panel");

        // Create ticket summary section
        VBox ticketSummary = createTicketSummary(tiket, noKursi);

        // Create order button
        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        // Payment note
        Label paymentNote = new Label("*akan lanjut ke pembayaran");
        paymentNote.getStyleClass().add("payment-note");

        VBox rightPanel = new VBox(20, ticketSummary, pesanBtn, paymentNote);
        rightPanel.getStyleClass().add("right-panel");
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Main content layout
        HBox mainContent = new HBox(30, leftPanel, rightPanel);
        mainContent.getStyleClass().add("content-container");
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.CENTER);

        // Root layout
        VBox root = new VBox();
        root.getChildren().addAll(topSpace, headerBar, mainContent);
        root.getStyleClass().add("pesan-root");

        // Set up button action
        pesanBtn.setOnAction(e -> {
            if (validateFields(namaPemesan, hpPemesan, emailPemesan, namaPenumpang, hpPenumpang, emailPenumpang)) {
                Pemesanan p = new Pemesanan();
                p.setIdPesanan("P" + new Random().nextInt(9999));
                p.setIdTiket(tiket.getIdTiket());
                p.setNamaPemesan(namaPemesan.getText());
                p.setNoHpPemesan(hpPemesan.getText());
                p.setEmailPemesan(emailPemesan.getText());
                p.setNamaPenumpang(namaPenumpang.getText());
                p.setNoHpPenumpang(hpPenumpang.getText());
                p.setEmailPenumpang(emailPenumpang.getText());
                p.setNoKursi(noKursi);

                PemesananDAO dao = new PemesananDAO();
                if (dao.simpanPemesanan(p)) {
                    showAlert("Pemesanan berhasil!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Gagal menyimpan pesanan.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Mohon lengkapi semua field yang diperlukan.", Alert.AlertType.WARNING);
            }
        });

        Scene scene = new Scene(root, 900, 645);
        URL css = getClass().getResource("/css/pesan_tiket.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.err.println("⚠️ Gagal menemukan CSS: /css/pesan_tiket.css");
        }

        stage.setScene(scene);
        stage.setTitle("Pesan Transportasi");
        stage.centerOnScreen();
        stage.show();
    }

    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.getStyleClass().add("text-field");
        return textField;
    }

    private VBox createFormSection(String title, String subtitle, TextField... fields) {
        Label labelTitle = new Label(title);
        labelTitle.getStyleClass().add("section-title");

        Label labelSub = new Label(subtitle);
        labelSub.getStyleClass().add("section-sub");

        VBox section = new VBox(8);
        section.getChildren().addAll(labelTitle, labelSub);
        
        for (TextField field : fields) {
            section.getChildren().add(field);
        }
        
        section.getStyleClass().add("form-section");
        return section;
    }

    private VBox createTicketSummary(Tiket tiket, int noKursi) {
        // Destination header
        Label destinationTitle = new Label("Asal → Tujuan");
        destinationTitle.getStyleClass().add("tujuan-title");

        // White container for ticket details
        VBox ticketDetailsContainer = new VBox(15);
        ticketDetailsContainer.getStyleClass().add("ticket-details-container");

        // Date and seat info on the same line
        HBox dateAndSeat = new HBox();
        dateAndSeat.setAlignment(Pos.CENTER);
        
        Label dateLabel = new Label("Rab, " + tiket.getTanggal());
        dateLabel.getStyleClass().add("trip-date");
        
        Label seatLabel = new Label("No. Kursi " + noKursi);
        seatLabel.getStyleClass().add("seat-info");
        
        dateAndSeat.getChildren().addAll(dateLabel, seatLabel);

        // Time and route info
        HBox timeAndRoute = new HBox(10);
        timeAndRoute.setAlignment(Pos.CENTER_LEFT);
        
        Label departureTime = new Label(tiket.getJam());
        departureTime.getStyleClass().add("departure-time");
        
        Label arrow = new Label("→");
        arrow.getStyleClass().add("time-arrow");
        
        // Calculate arrival time (departure + 100 minutes)
        String arrivalTime = calculateArrivalTime(tiket.getJam());
        Label arrivalTimeLabel = new Label(arrivalTime);
        arrivalTimeLabel.getStyleClass().add("arrival-time");
        
        Label routeLabel = new Label("JKT → BDG");
        routeLabel.getStyleClass().add("route-info");
        
        timeAndRoute.getChildren().addAll(departureTime, arrow, arrivalTimeLabel, routeLabel);

        // Payment section
        Label totalLabel = new Label("Total Pembayaran");
        totalLabel.getStyleClass().add("total-payment");
        
        String formattedPrice = formatPrice(tiket.getHarga());
        Label priceLabel = new Label("IDR " + formattedPrice + ",-");
        priceLabel.getStyleClass().add("price-amount");

        ticketDetailsContainer.getChildren().addAll(dateAndSeat, timeAndRoute, totalLabel, priceLabel);

        VBox summaryBox = new VBox(15, destinationTitle, ticketDetailsContainer);
        summaryBox.getStyleClass().add("ringkasan-box");
        
        return summaryBox;
    }

    // Calculate arrival time by adding 100 minutes to departure time
    private String calculateArrivalTime(String departureTimeStr) {
        try {
            // Parse the departure time (assuming format HH:mm:ss)
            LocalTime departureTime = LocalTime.parse(departureTimeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            // Add 100 minutes
            LocalTime arrivalTime = departureTime.plusMinutes(100);
            
            // Format back to HH:mm:ss
            return arrivalTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            System.err.println("Error calculating arrival time: " + e.getMessage());
            return "11:40:00"; // Fallback time
        }
    }

    // Helper method to format price properly
    private String formatPrice(Object price) {
        try {
            if (price instanceof Integer) {
                NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                return formatter.format((Integer) price);
            } else if (price instanceof Double) {
                NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                return formatter.format((Double) price);
            } else if (price instanceof Float) {
                NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                return formatter.format((Float) price);
            } else if (price instanceof Long) {
                NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                return formatter.format((Long) price);
            } else {
                String priceStr = price.toString();
                try {
                    double priceValue = Double.parseDouble(priceStr);
                    NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                    return formatter.format(priceValue);
                } catch (NumberFormatException e) {
                    return priceStr;
                }
            }
        } catch (Exception e) {
            System.err.println("Error formatting price: " + e.getMessage());
            return price.toString();
        }
    }

    private boolean validateFields(TextField... fields) {
        boolean isValid = true;
        
        for (TextField field : fields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                field.getStyleClass().removeAll("success", "error");
                field.getStyleClass().add("error");
                isValid = false;
            } else {
                field.getStyleClass().removeAll("success", "error");
                field.getStyleClass().add("success");
            }
        }
        
        return isValid;
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
