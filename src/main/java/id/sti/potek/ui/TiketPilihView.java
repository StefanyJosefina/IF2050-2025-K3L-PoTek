package id.sti.potek.ui;


import id.sti.potek.model.Tiket;


import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TiketPilihView {

    public void start(Stage stage, List<Tiket> hasil) {
        ListView<Tiket> listView = new ListView<>(FXCollections.observableArrayList(hasil));
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Tiket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getIdTiket() + " - " + item.getJam() + " - Rp" + item.getHarga() + " - Kursi: " + item.getTersediaKursi());
                }
            }
        });

        Button lanjutBtn = new Button("Lanjut");

        VBox root = new VBox(10, new Label("Pilih Jadwal:"), listView, lanjutBtn);
        root.setPadding(new Insets(20));

        lanjutBtn.setOnAction(e -> {
            Tiket selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                new TiketPesanView().start(stage, selected);
            } else {
                showAlert("Pilih salah satu tiket terlebih dahulu.");
            }
        });

        stage.setScene(new Scene(root, 500, 300));
        stage.setTitle("Pilih Tiket");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }
}
