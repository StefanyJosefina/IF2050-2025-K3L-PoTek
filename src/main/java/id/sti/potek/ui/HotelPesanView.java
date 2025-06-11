package id.sti.potek.ui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import id.sti.potek.dao.PemesananHotelDAO;
import id.sti.potek.model.Kamar;
import id.sti.potek.model.PemesananHotel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HotelPesanView {
    private PemesananHotelDAO pemesananDAO;
    private Stage parentStage;

    public HotelPesanView() {
        try {
            this.pemesananDAO = new PemesananHotelDAO();
        } catch (Exception e) {
            System.out.println("Warning: Could not initialize PemesananHotelDAO: " + e.getMessage());
            this.pemesananDAO = null;
        }
    }

    public void start(Stage stage, Kamar kamar, String checkin, String checkout, int malam) {
        this.parentStage = stage;
        
        try {
            if (kamar == null) {
                showError(stage, "Data kamar tidak valid");
                return;
            }
            if (checkin == null || checkout == null) {
                showError(stage, "Tanggal check-in/check-out tidak valid");
                return;
            }
            if (malam <= 0) {
                showError(stage, "Jumlah malam tidak valid");
                return;
            }

            ImageView icon = new ImageView();
            try {
                icon.setImage(new Image("/icons/kasur-icon.png"));
                icon.setFitHeight(28);
                icon.setFitWidth(28);
            } catch (Exception e) {
                System.out.println("Icon tidak ditemukan: " + e.getMessage());
                icon.setFitHeight(28);
                icon.setFitWidth(28);
            }

            Text title = new Text("Pesan Hotel");
            title.getStyleClass().add("banner-text");
            
            HBox header = new HBox(10, icon, title);
            header.setAlignment(Pos.CENTER);
            header.getStyleClass().add("banner-header");

            Button backBtn = new Button("← Kembali");
            backBtn.getStyleClass().add("back-button");
            backBtn.setOnAction(e -> {
                try {
                    stage.close();
                } catch (Exception ex) {
                    System.out.println("Error closing stage: " + ex.getMessage());
                }
            });

            HBox topRow = new HBox();
            topRow.setAlignment(Pos.CENTER_LEFT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            topRow.getChildren().addAll(backBtn, spacer);

            Label pemesanTitle = new Label("Detail Pemesan");
            pemesanTitle.getStyleClass().add("section-title");
            pemesanTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            
            Label pemesanSubtitle = new Label("Detail kontak ini akan digunakan untuk pengiriman e-tiket dan keperluan");
            pemesanSubtitle.getStyleClass().add("section-sub");
            pemesanSubtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-wrap-text: true;");

            TextField namaField = new TextField();
            namaField.setPromptText("Masukkan Nama Lengkap");
            namaField.getStyleClass().add("form-field");
            
            Label noteNama = new Label("*Seperti di KTP/SIM/Paspor");
            noteNama.setStyle("-fx-font-size: 10px; -fx-text-fill: #999;");

            TextField hpField = new TextField();
            hpField.setPromptText("Masukkan Nomor HP");
            hpField.getStyleClass().add("form-field");

            TextField emailField = new TextField();
            emailField.setPromptText("Masukkan Email");
            emailField.getStyleClass().add("form-field");

            VBox pemesanBox = new VBox(8, pemesanTitle, pemesanSubtitle, namaField, noteNama, hpField, emailField);
            pemesanBox.setPadding(new Insets(16));
            pemesanBox.getStyleClass().add("form-box");
            pemesanBox.setStyle("-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            Label penginapanTitle = new Label("Detail Penginapan");
            penginapanTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            
            VBox fasilitasBox = new VBox(new Label("\uD83D\uDC64 1 Tamu"));
            fasilitasBox.setSpacing(2);

            VBox penginapanBox = new VBox(10,
                    penginapanTitle,
                    fasilitasBox,
                    new Separator(),
                    new Label("Kamar 1: " + (kamar.getTipeKamar() != null ? kamar.getTipeKamar() : "Standard"))
            );
            penginapanBox.setPadding(new Insets(16));
            penginapanBox.getStyleClass().add("form-box");
            penginapanBox.setStyle("-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            VBox leftPanel = new VBox(20, pemesanBox, penginapanBox);
            leftPanel.setPrefWidth(400);

            Label namaHotel = new Label(kamar.getNamaHotel() != null ? kamar.getNamaHotel() : "Hotel");
            namaHotel.getStyleClass().add("hotel-name");
            namaHotel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            
            Label checkinLabel = new Label("Check-in: " + checkin);
            checkinLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
            
            Label checkoutLabel = new Label("Check-out: " + checkout);
            checkoutLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
            
            Label malamLabel = new Label(malam + " Malam - 1 Kamar");
            malamLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            VBox detailRingkasan = new VBox(namaHotel, checkinLabel, checkoutLabel, malamLabel);
            detailRingkasan.setSpacing(5);

            Circle hotelIcon = new Circle(20);
            hotelIcon.setStyle("-fx-fill: #3498db;");
            
            HBox hotelBox = new HBox(10, hotelIcon, detailRingkasan);
            hotelBox.setAlignment(Pos.CENTER_LEFT);
            
            VBox ringkasanCard = new VBox(15, hotelBox);
            ringkasanCard.setPadding(new Insets(20));
            ringkasanCard.getStyleClass().add("ringkasan-box");
            ringkasanCard.setStyle("-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            HBox totalBox = new HBox();
            Label totalText = new Label("Total Pembayaran");
            totalText.getStyleClass().add("total-text");
            totalText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            
            int totalHarga = kamar.getHarga() * malam;
            Label totalHargaLabel = new Label("IDR " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format(totalHarga));
            totalHargaLabel.getStyleClass().add("total-amount");
            totalHargaLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            
            Region totalSpacer = new Region();
            HBox.setHgrow(totalSpacer, Priority.ALWAYS);
            totalBox.getChildren().addAll(totalText, totalSpacer, totalHargaLabel);
            totalBox.setAlignment(Pos.CENTER_LEFT);
            ringkasanCard.getChildren().add(totalBox);

            Button pesanBtn = new Button("Pesan");
            pesanBtn.getStyleClass().add("pesan-button");
            pesanBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 12px 30px; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-font-size: 16px; -fx-font-weight: bold;");
            
            Label note = new Label("*akan lanjut ke pembayaran");
            note.setStyle("-fx-font-size: 10px; -fx-text-fill: #999;");

            pesanBtn.setOnAction(e -> {
                try {
                    if (namaField.getText().trim().isEmpty()) {
                        showError(stage, "Nama tidak boleh kosong");
                        return;
                    }
                    if (hpField.getText().trim().isEmpty()) {
                        showError(stage, "Nomor HP tidak boleh kosong");
                        return;
                    }
                    if (emailField.getText().trim().isEmpty()) {
                        showError(stage, "Email tidak boleh kosong");
                        return;
                    }

                    PemesananHotel pemesanan = new PemesananHotel();
                    pemesanan.setIdKamar(kamar.getId());
                    pemesanan.setTanggalCheckIn(LocalDate.parse(checkin));
                    pemesanan.setTanggalCheckOut(LocalDate.parse(checkout));
                    pemesanan.setJumlahKamar(1);
                    pemesanan.setJumlahTamu(1);
                    pemesanan.setTotalHarga(totalHarga);
                    pemesanan.setNamaPemesan(namaField.getText().trim());
                    pemesanan.setNoHpPemesan(hpField.getText().trim());
                    pemesanan.setEmailPemesan(emailField.getText().trim());

                    if (pemesananDAO != null) {
                        boolean success = pemesananDAO.simpanPemesanan(pemesanan);
                        if (success) {
                            System.out.println("Pemesanan berhasil disimpan.");
                            showSuccess(stage, "Pemesanan berhasil disimpan!");
                            // TODO: Navigate to home 
                        } else {
                            showError(stage, "Gagal menyimpan pemesanan");
                        }
                    } else {
                        showError(stage, "Sistem pemesanan tidak tersedia");
                    }
                } catch (Exception ex) {
                    System.out.println("Error saving booking: " + ex.getMessage());
                    ex.printStackTrace();
                    showError(stage, "Terjadi kesalahan saat menyimpan pemesanan");
                }
            });
            
            ImageView heartLogo = new ImageView(new Image("/icons/logo.png"));
            heartLogo.setFitWidth(150); 
            heartLogo.setPreserveRatio(true);
            heartLogo.setSmooth(true);

            VBox rightPanel = new VBox(25, ringkasanCard, pesanBtn, note, heartLogo);
            rightPanel.setAlignment(Pos.TOP_CENTER);
            rightPanel.setPrefWidth(350);

            HBox main = new HBox(40, leftPanel, rightPanel);
            main.setAlignment(Pos.CENTER);
            main.setPadding(new Insets(20));

            VBox root = new VBox(20, header, topRow, main);
            root.setPadding(new Insets(20));
            root.getStyleClass().add("main-background");

            Scene scene = new Scene(root, 900, 645);
            
            try {
                String cssPath = getClass().getResource("/css/hotel_pesan.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.out.println("CSS file tidak ditemukan: " + e.getMessage());
                root.setStyle("-fx-background-color: #f5f5f5;");
            }

            stage.setTitle("Pesan Hotel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            System.out.println("Error in HotelPesanView.start(): " + e.getMessage());
            e.printStackTrace();
            showError(stage, "Gagal memuat halaman pemesanan: " + e.getMessage());
        }
    }

    private void showError(Stage parentStage, String message) {
        Stage errorStage = new Stage();
        if (parentStage != null) {
            errorStage.initModality(Modality.APPLICATION_MODAL);
            errorStage.initOwner(parentStage);
        }
        errorStage.setResizable(false);
        errorStage.setTitle("Error");

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: white; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label title = new Label("Error");
        title.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-wrap-text: true;");
        messageLabel.setMaxWidth(300);

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8px 20px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        okBtn.setOnAction(e -> errorStage.close());

        box.getChildren().addAll(title, messageLabel, okBtn);

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 200);
        errorStage.setScene(scene);
        errorStage.centerOnScreen();
        errorStage.showAndWait();
    }

    private void showSuccess(Stage parentStage, String message) {
        Stage successStage = new Stage();
        if (parentStage != null) {
            successStage.initModality(Modality.APPLICATION_MODAL);
            successStage.initOwner(parentStage);
        }
        successStage.setResizable(false);
        successStage.setTitle("Sukses");

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));
        box.setStyle("-fx-background-color: white; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label title = new Label("Sukses");
        title.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px; -fx-wrap-text: true;");
        messageLabel.setMaxWidth(300);

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 8px 20px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        okBtn.setOnAction(e -> {
            successStage.close();
            if (parentStage != null) {
                parentStage.close();
            }
        });

        box.getChildren().addAll(title, messageLabel, okBtn);

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 200);
        successStage.setScene(scene);
        successStage.centerOnScreen();
        successStage.showAndWait();
    }
}