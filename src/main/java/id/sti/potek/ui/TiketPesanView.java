package id.sti.potek.ui;


import id.sti.potek.dao.PemesananDAO;
import id.sti.potek.model.Pemesanan;
import id.sti.potek.model.Tiket;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TiketPesanView {

    public void start(Stage stage, Tiket tiket) {
        TextField namaPemesan = new TextField();
        TextField hpPemesan = new TextField();
        TextField emailPemesan = new TextField();

        TextField namaPenumpang = new TextField();
        TextField hpPenumpang = new TextField();
        TextField emailPenumpang = new TextField();

        TextField noKursi = new TextField();

        Button pesanBtn = new Button("Pesan");

        VBox root = new VBox(10,
                new Label("Pemesan"), namaPemesan, hpPemesan, emailPemesan,
                new Label("Penumpang"), namaPenumpang, hpPenumpang, emailPenumpang,
                new Label("Nomor Kursi (angka):"), noKursi,
                pesanBtn);
        root.setPadding(new Insets(20));

        pesanBtn.setOnAction(e -> {
            Pemesanan p = new Pemesanan();
            p.setIdPesanan("P" + System.currentTimeMillis());
            p.setIdTiket(tiket.getIdTiket());
            p.setNamaPemesan(namaPemesan.getText());
            p.setNoHpPemesan(hpPemesan.getText());
            p.setEmailPemesan(emailPemesan.getText());
            p.setNamaPenumpang(namaPenumpang.getText());
            p.setNoHpPenumpang(hpPenumpang.getText());
            p.setEmailPenumpang(emailPenumpang.getText());
            p.setNoKursi(Integer.parseInt(noKursi.getText()));

            PemesananDAO pemesananDAO = new PemesananDAO();
            if (pemesananDAO.simpanPemesanan(p)) {
                showAlert("Pemesanan berhasil!");
            } else {
                showAlert("Gagal menyimpan pesanan.");
            }
        });

        stage.setScene(new Scene(root, 500, 500));
        stage.setTitle("Pemesanan Tiket");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
