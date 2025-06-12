package id.sti.potek.ui;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import id.sti.potek.controller.TiketController;
import id.sti.potek.dao.PemesananDAO;
import id.sti.potek.model.Pemesanan;
import id.sti.potek.model.Tiket;
import id.sti.potek.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TiketPesanView {
    private User loggedInUser;

    // Konstruktor default
    public TiketPesanView() {
        this.loggedInUser = null;
    }

    // Konstruktor dengan user
    public TiketPesanView(User user) {
        this.loggedInUser = user;
    }

    public void start(Stage stage, Tiket tiket, int noKursi) {
        String asal = tiket.getKeberangkatan();
        String tujuan = tiket.getTujuan();
        // Create header
        HBox headerContent = new HBox();
        headerContent.setAlignment(Pos.CENTER_LEFT);
        headerContent.setSpacing(10);
        try {
            Image busImage = new Image(getClass().getResourceAsStream("/icons/icon_transportasi.png"));
            ImageView busIcon = new ImageView(busImage);
            busIcon.setFitHeight(24);
            busIcon.setFitWidth(24);
            headerContent.getChildren().add(busIcon);
        } catch (Exception e) {
            Label fallback = new Label("🚌");
            fallback.setStyle("-fx-font-size: 18px;");
            headerContent.getChildren().add(fallback);
        }
        Label headerLabel = new Label("Pesan Transportasi");
        headerContent.getChildren().add(headerLabel);
        StackPane headerBar = new StackPane(headerContent);
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
        VBox ticketSummary = createTicketSummary(tiket, noKursi, asal, tujuan);

        // Create order button
        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        // Payment note
        Label paymentNote = new Label("*akan lanjut ke pembayaran");
        paymentNote.getStyleClass().add("payment-note");

        VBox rightPanel = new VBox(25, ticketSummary, pesanBtn, paymentNote);
        rightPanel.getStyleClass().add("right-panel");
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Main content layout
        HBox mainContent = new HBox(40, leftPanel, rightPanel);
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
                p.setIdUser(loggedInUser != null ? loggedInUser.getIdUser() : null);
                p.setIdTiket(tiket.getIdTiket());
                p.setNamaPemesan(namaPemesan.getText());
                p.setNoHpPemesan(hpPemesan.getText());
                p.setEmailPemesan(emailPemesan.getText());
                p.setNamaPenumpang(namaPenumpang.getText());
                p.setNoHpPenumpang(hpPenumpang.getText());
                p.setEmailPenumpang(emailPenumpang.getText());
                p.setNoKursi(noKursi);

                TiketController controller = new TiketController();
                boolean success = controller.simpanPemesanan(
                    loggedInUser != null ? loggedInUser.getIdUser() : null,
                    tiket.getIdTiket(),
                    namaPemesan.getText(),
                    hpPemesan.getText(),
                    emailPemesan.getText(),
                    namaPenumpang.getText(),
                    hpPenumpang.getText(),
                    emailPenumpang.getText(),
                    noKursi
                );

                if (success) {
                    showAlert("Pemesanan berhasil!", Alert.AlertType.INFORMATION);
                    // Kembali ke MainView setelah pemesanan sukses
                    Stage newStage = new Stage();
                    MainView mainView = new MainView(loggedInUser);
                    mainView.start(newStage);
                    stage.close();
                } else {
                    showAlert("Gagal menyimpan pesanan.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Mohon lengkapi semua field yang diperlukan.", Alert.AlertType.WARNING);
            }
        });

        // scene size and CSS
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

    private VBox createTicketSummary(Tiket tiket, int noKursi, String asal, String tujuan) {
        // Destination header 
        Label destinationTitle = new Label(asal + " → " + tujuan);
        destinationTitle.getStyleClass().add("tujuan-title");

        // bg ticket details
        VBox ticketDetailsContainer = new VBox(20);
        ticketDetailsContainer.getStyleClass().add("ticket-details-container");

        // Date and seat info layout
        HBox dateAndSeat = new HBox();
        dateAndSeat.setAlignment(Pos.CENTER);
        
        // Use dynamic date from tiket.getTanggal()
        Label dateLabel = new Label("Rab, " + tiket.getTanggal());
        dateLabel.getStyleClass().add("trip-date");
        
        Label seatLabel = new Label("No. Kursi " + noKursi);
        seatLabel.getStyleClass().add("seat-info");
        
        dateAndSeat.getChildren().addAll(dateLabel, seatLabel);
        dateAndSeat.setSpacing(60);

        // Journey details bg
        HBox journeyContainer = new HBox(20);
        journeyContainer.getStyleClass().add("journey-container");
        journeyContainer.setAlignment(Pos.CENTER);

        // icon
        Circle circleIcon = new Circle(15);
        circleIcon.getStyleClass().add("journey-icon");

        // Departure info 
        VBox departureInfo = new VBox(5);
        departureInfo.setAlignment(Pos.CENTER);
        
        String departureTime = formatTime(tiket.getJam());
        Label departureTimeLabel = new Label(departureTime);
        departureTimeLabel.getStyleClass().add("journey-time");
        
        // Convert asal to city code (e.g., Jakarta -> JKT)
        String departureCode = getCityCode(asal);
        Label departureCityLabel = new Label(departureCode);
        departureCityLabel.getStyleClass().add("journey-code");
        
        departureInfo.getChildren().addAll(departureTimeLabel, departureCityLabel);

        // Dashed arrow
        Label dashedArrow = new Label("- - ->");
        dashedArrow.getStyleClass().add("dashed-arrow");

        // jam kedatangan
        VBox arrivalInfo = new VBox(5);
        arrivalInfo.setAlignment(Pos.CENTER);
        
        String arrivalTime = formatTime(calculateArrivalTime(tiket.getJam(), tiket.getWaktuPerjalanan()));
        Label arrivalTimeLabel = new Label(arrivalTime);
        arrivalTimeLabel.getStyleClass().add("journey-time");
        
        // Convert tujuan to city code (e.g., Bandung -> BDG)
        String arrivalCode = getCityCode(tujuan);
        Label arrivalCityLabel = new Label(arrivalCode);
        arrivalCityLabel.getStyleClass().add("journey-code");
        
        arrivalInfo.getChildren().addAll(arrivalTimeLabel, arrivalCityLabel);

        journeyContainer.getChildren().addAll(circleIcon, departureInfo, dashedArrow, arrivalInfo);

        // Payment section 
        Label totalLabel = new Label("Total Pembayaran");
        totalLabel.getStyleClass().add("total-payment");
        
        String formattedPrice = formatPrice(tiket.getHarga());
        Label priceLabel = new Label("IDR " + formattedPrice + ",-");
        priceLabel.getStyleClass().add("price-amount");

        ticketDetailsContainer.getChildren().addAll(dateAndSeat, journeyContainer, totalLabel, priceLabel);

        VBox summaryBox = new VBox(20, destinationTitle, ticketDetailsContainer);
        summaryBox.getStyleClass().add("ringkasan-box");
        
        return summaryBox;
    }

    // Convert city names to airport/city codes
    private String getCityCode(String cityName) {
        if (cityName == null) return "???";
        
        String city = cityName.toLowerCase().trim();
        
        // Map common Indonesian cities to their codes
        switch (city) {
            case "jakarta":
                return "JKT";
            case "bandung":
                return "BDG";
            case "surabaya":
                return "SBY";
            case "medan":
                return "MDN";
            case "yogyakarta":
            case "jogja":
                return "YGY";
            case "semarang":
                return "SMG";
            case "malang":
                return "MLG";
            case "solo":
            case "surakarta":
                return "SLO";
            case "denpasar":
            case "bali":
                return "DPS";
            case "makassar":
                return "MKS";
            case "palembang":
                return "PLB";
            case "balikpapan":
                return "BPN";
            case "pontianak":
                return "PNK";
            case "manado":
                return "MDO";
            case "pekanbaru":
                return "PKU";
            case "padang":
                return "PDG";
            case "banjarmasin":
                return "BJM";
            case "samarinda":
                return "SMD";
            case "jambi":
                return "JMB";
            case "bengkulu":
                return "BKL";
            case "lampung":
            case "bandar lampung":
                return "LPG";
            default:
                // kalo ga ada, return first 3 characters in uppercase
                return cityName.length() >= 3 ? 
                       cityName.substring(0, 3).toUpperCase() : 
                       cityName.toUpperCase();
        }
    }

    // Format time to show HH.mm format
    private String formatTime(String timeStr) {
        try {
            String[] parts = timeStr.split(":");
            if (parts.length >= 2) {
                return parts[0] + "." + parts[1];
            }
            return timeStr;
        } catch (Exception e) {
            return timeStr;
        }
    }

    // Calculate arrival time by adding duration (in minutes) to departure time
    private String calculateArrivalTime(String departureTimeStr, int waktuPerjalanan) {
        try {
            LocalTime departureTime = LocalTime.parse(departureTimeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime arrivalTime = departureTime.plusHours(waktuPerjalanan);
            return arrivalTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            System.err.println("Error calculating arrival time: " + e.getMessage());
            return "11:40:00";
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
