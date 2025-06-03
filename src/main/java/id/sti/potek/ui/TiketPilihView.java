package id.sti.potek.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.sti.potek.controller.TiketController;
import id.sti.potek.model.Tiket;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TiketPilihView {
    private final Map<Integer, Boolean> ketersediaanKursi = new HashMap<>();
    private int kursiTerpilih = -1;
    private Button kursiTerpilihBtn = null;

    public void start(Stage stage, List<Tiket> res, String asal, String tujuan, String tanggal) {
        for (int i = 1; i <= 9; i++) {
            ketersediaanKursi.put(i, i != 7); // Kursi 7 booked
        }

        ListView<Tiket> listView = new ListView<>(FXCollections.observableArrayList(res));
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Tiket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String text = item.getJam() + " (Estimasi 3 jam)\n";
                    if (item.getTersediaKursi() == 0) {
                        text += "Kursi Tidak Tersedia";
                    } else {
                        text += "Tersedia " + item.getTersediaKursi() + " Kursi";
                    }
                    setText(text);
                }
            }
        });

        GridPane seatGrid = new GridPane();
        seatGrid.setHgap(10);
        seatGrid.setVgap(10);
        seatGrid.setPadding(new Insets(10));

        Label seatLabel = new Label("Ketersediaan");

        int col = 0, row = 0;
        for (int i = 1; i <= 9; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.getStyleClass().add("seat-button");

            boolean available = ketersediaanKursi.getOrDefault(i, true);
            btn.getStyleClass().add(available ? "seat-empty" : "seat-booked");
            btn.setDisable(!available);

            if (available) {
                int nomorKursi = i;
                btn.setOnAction(e -> {
                    if (kursiTerpilihBtn != null) {
                        kursiTerpilihBtn.getStyleClass().remove("seat-selected");
                        kursiTerpilihBtn.getStyleClass().add("seat-empty");
                    }

                    kursiTerpilih = nomorKursi;
                    kursiTerpilihBtn = btn;

                    btn.getStyleClass().remove("seat-empty");
                    btn.getStyleClass().add("seat-selected");

                    System.out.println("Kursi dipilih: " + kursiTerpilih);
                });
            }

            seatGrid.add(btn, col, row);
            col++;
            if (i % 3 == 0) {
                col = 0;
                row++;
            }
        }

        Label supir = new Label("supir");
        supir.getStyleClass().add("supir-label");
        seatGrid.add(supir, 2, 0);

        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");

        pesanBtn.setOnAction(e -> {
            if (kursiTerpilih == -1) {
                System.out.println("⚠️ Silakan pilih kursi terlebih dahulu.");
                return;
            }
            System.out.println("Memesan tiket dengan kursi nomor " + kursiTerpilih);

            TiketController controller = new TiketController();
            Tiket tiketTerpilih = listView.getSelectionModel().getSelectedItem();
            if (tiketTerpilih == null) {
                System.out.println("⚠️ Silakan pilih jadwal terlebih dahulu.");
                return;
            }
            List<Integer> hasilBooking = controller.getKursiTerbooking(tiketTerpilih.getIdTiket());

            new TiketPesanView().start(stage, tiketTerpilih);


        });


        VBox jadwalBox = new VBox(10, new Label("Jadwal"), listView);
        VBox seatBox = new VBox(10, seatLabel, seatGrid, pesanBtn);
        HBox root = new HBox(20, jadwalBox, seatBox);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 900, 645);
        URL css = getClass().getResource("/css/pilih_tiket.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.err.println("⚠️ Gagal menemukan CSS: /css/tiketpilih.css");
        }

        stage.setTitle("Pilih Tiket");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
