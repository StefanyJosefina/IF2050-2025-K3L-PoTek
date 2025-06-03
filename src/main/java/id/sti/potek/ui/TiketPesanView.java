package id.sti.potek.ui;

import java.net.URL;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TiketPesanView {

    public void start(Stage stage, Tiket tiket) {
        TextField namaPemesan = new TextField();
        namaPemesan.setPromptText("Masukkan Nama Lengkap");

        TextField hpPemesan = new TextField();
        hpPemesan.setPromptText("Masukkan Nomor HP");

        TextField emailPemesan = new TextField();
        emailPemesan.setPromptText("Masukkan Email");

        VBox pemesanBox = new VBox(8,
            createLabeledField("Detail Pemesan", "Detail kontak ini akan digunakan untuk pengiriman e-tiket dan keperluan", namaPemesan, hpPemesan, emailPemesan)
        );
        pemesanBox.getStyleClass().add("pemesan-box");

        TextField namaPenumpang = new TextField();
        namaPenumpang.setPromptText("Masukkan Nama Lengkap");

        TextField hpPenumpang = new TextField();
        hpPenumpang.setPromptText("Masukkan Nomor HP");

        TextField emailPenumpang = new TextField();
        emailPenumpang.setPromptText("Masukkan Email");

        VBox penumpangBox = new VBox(8,
            createLabeledField("Detail Penumpang", "*Seperti di KTP/SIM/Paspor", namaPenumpang, hpPenumpang, emailPenumpang)
        );
        penumpangBox.getStyleClass().add("penumpang-box");

        VBox kiri = new VBox(20, pemesanBox, penumpangBox);

        // Kanan: Tiket Ringkasan
        Label asalTujuan = new Label("Asal → Tujuan");
        asalTujuan.getStyleClass().add("tujuan-title");

        Label detailTiket = new Label(
            "Rab, " + tiket.getTanggal() + "   No. Kursi -\n" +
            tiket.getJam() + "   JKT → BDG\n\n" +
            "Total Pembayaran\nIDR " + tiket.getHarga()
        );
        detailTiket.getStyleClass().add("tiket-detail");

        VBox ringkasanBox = new VBox(10, asalTujuan, detailTiket);
        ringkasanBox.getStyleClass().add("ringkasan-box");

        // Tombol pesan
        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        Label catatan = new Label("*akan lanjut ke pembayaran");

        VBox kanan = new VBox(20, ringkasanBox, pesanBtn, catatan);
        kanan.setAlignment(Pos.TOP_CENTER);

        HBox root = new HBox(30, kiri, kanan);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("pesan-root");

        Scene scene = new Scene(root, 900, 600);
        URL css = getClass().getResource("/css/pesan_tiket.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        pesanBtn.setOnAction(e -> {
            Pemesanan p = new Pemesanan();
            p.setIdPesanan("P" + new Random().nextInt(9999));
            p.setIdTiket(tiket.getIdTiket());
            p.setNamaPemesan(namaPemesan.getText());
            p.setNoHpPemesan(hpPemesan.getText());
            p.setEmailPemesan(emailPemesan.getText());
            p.setNamaPenumpang(namaPenumpang.getText());
            p.setNoHpPenumpang(hpPenumpang.getText());
            p.setEmailPenumpang(emailPenumpang.getText());
            p.setNoKursi(0); // placeholder

            PemesananDAO dao = new PemesananDAO();
            if (dao.simpanPemesanan(p)) {
                showAlert("Pemesanan berhasil!");
            } else {
                showAlert("Gagal menyimpan pesanan.");
            }
        });

        stage.setScene(scene);
        stage.setTitle("Pesan Transportasi");
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createLabeledField(String title, String subtitle, TextField... fields) {
        Label labelTitle = new Label(title);
        labelTitle.getStyleClass().add("section-title");

        Label labelSub = new Label(subtitle);
        labelSub.getStyleClass().add("section-sub");

        VBox vbox = new VBox(6);
        vbox.getChildren().addAll(labelTitle, labelSub);
        vbox.getChildren().addAll(fields);
        return vbox;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
