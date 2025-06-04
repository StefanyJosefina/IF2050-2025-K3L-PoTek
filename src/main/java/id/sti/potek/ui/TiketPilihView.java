package id.sti.potek.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.sti.potek.controller.TiketController;
import id.sti.potek.model.Tiket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TiketPilihView {
    private int kursiTerpilih = -1;
    private Button kursiTerpilihBtn = null;
    private Tiket tiketTerpilih = null;
    private GridPane seatGrid;
    private final Map<Integer, Button> seatButtons = new HashMap<>();

    public void start(Stage stage, List<Tiket> res, String asal, String tujuan, String tanggal) {
        HBox headerContent = new HBox(10);
        headerContent.setAlignment(Pos.CENTER);
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

        Label headerLabel = new Label("Transportasi");
        headerLabel.getStyleClass().add("header-text");
        headerContent.getChildren().add(headerLabel);
        StackPane headerBar = new StackPane(headerContent);
        headerBar.getStyleClass().add("header-bar");

        Region topSpace = new Region();
        topSpace.setPrefHeight(20);

        Label routeLabel = new Label(asal + " → " + tujuan);
        routeLabel.getStyleClass().add("route-label");

        VBox scheduleList = new VBox(15);
        for (Tiket tiket : res) {
            VBox item = new VBox(5);
            item.getStyleClass().add("schedule-item");
            Label time = new Label(tiket.getJam());
            time.getStyleClass().add("time-label");
            int waktu_perjalanan = tiket.getWaktuPerjalanan();
            Label est = new Label("(Estimasi perjalanan " + waktu_perjalanan +" jam)");
            est.getStyleClass().add("availability-label");
            Label available = new Label("Tersedia " + tiket.getTersediaKursi() + " Kursi");
            available.getStyleClass().add("availability-label");
            item.getChildren().addAll(time, est, available);
            item.setOnMouseClicked(e -> {
                for (var node : scheduleList.getChildren()) {
                    node.getStyleClass().remove("selected-schedule");
                }
                item.getStyleClass().add("selected-schedule");
                tiketTerpilih = tiket;
                renderSeats(tiket.getIdTiket());
            });
            scheduleList.getChildren().add(item);
        }

        ScrollPane scheduleScroll = new ScrollPane(scheduleList);
        scheduleScroll.setFitToWidth(true);
        scheduleScroll.setPrefHeight(500);
        scheduleScroll.getStyleClass().add("schedule-scroll");

        seatGrid = new GridPane();
        seatGrid.getStyleClass().add("seat-grid");
        seatGrid.setHgap(15);
        seatGrid.setVgap(15);
        seatGrid.setPadding(new Insets(20));
        seatGrid.setAlignment(Pos.CENTER);

        Label supir = new Label("supir");
        supir.getStyleClass().add("supir-label");
        seatGrid.add(supir, 0, 0);

        Button pesanBtn = new Button("Pesan");
        pesanBtn.getStyleClass().add("pesan-button");
        pesanBtn.setPrefWidth(400);

        pesanBtn.setOnAction(e -> {
            if (kursiTerpilih == -1) {
                showDialog("⚠️ Silakan pilih kursi terlebih dahulu.");
                return;
            }
            if (tiketTerpilih == null) {
                showDialog("⚠️ Silakan pilih jadwal terlebih dahulu.");
                return;
            }

            new TiketPesanView().start(stage, tiketTerpilih, kursiTerpilih);
        });

        HBox legendBox = new HBox(20);
        legendBox.getStyleClass().add("legend-box");
        legendBox.setAlignment(Pos.CENTER);
        legendBox.setStyle("-fx-font-size: 14px;");
        legendBox.getChildren().addAll(
            createLegend("legend-booked", "booked"),
            createLegend("legend-empty", "empty")
        );
        

        VBox leftPanel = new VBox(15);
        leftPanel.getChildren().addAll(routeLabel, scheduleScroll);
        leftPanel.setPrefWidth(400);
        leftPanel.setAlignment(Pos.TOP_LEFT);

        VBox rightPanel = new VBox(15);
        rightPanel.getChildren().addAll(seatGrid, legendBox, pesanBtn);
        rightPanel.setPrefWidth(400);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        HBox mainContent = new HBox(60, leftPanel, rightPanel);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.CENTER);
        mainContent.getStyleClass().add("content-container");

        VBox root = new VBox(40);
        root.getChildren().addAll(topSpace, headerBar, mainContent);
        root.getStyleClass().add("root");
        StackPane.setMargin(mainContent, new Insets(30, 0, 20, 0));

        Scene scene = new Scene(root, 900, 645);
        URL css = getClass().getResource("/css/pilih_tiket.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.err.println("⚠️ Gagal menemukan CSS: /css/pilih_tiket.css");
        }

        stage.setTitle("Pilih Tiket - " + asal + " ke " + tujuan);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }




    private HBox createLegend(String styleClass, String labelText) {
        HBox box = new HBox(5);
        box.getStyleClass().add("legend-item");
        box.setAlignment(Pos.CENTER_LEFT);
        Region square = new Region();
        square.getStyleClass().addAll("legend-square", styleClass);
        square.setPrefSize(15, 15);
        Label label = new Label(labelText);
        box.getChildren().addAll(square, label);
        return box;
    }

    public void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }    
    
    private void renderSeats(String tiketId) {
        kursiTerpilih = -1;
        kursiTerpilihBtn = null;
        seatButtons.clear();
        seatGrid.getChildren().removeIf(node -> node instanceof Button || node instanceof Label);

        TiketController controller = new TiketController();
        List<Integer> booked = controller.getKursiTerbooking(tiketId);

        String prefix = tiketId.substring(0, 1).toUpperCase();
        int[][] layout;
        int totalKursi;

        switch (prefix) {
            case "K": // Kereta
                layout = getLayoutKereta();
                totalKursi = 12;
                break;
            case "P": // Pesawat
                layout = getLayoutPesawat();
                totalKursi = 12;
                break;
            case "T": // Bus
            default:
                totalKursi = tiketTerpilih != null ? tiketTerpilih.getTotalKursi() : 9;
                layout = (totalKursi == 12) ? getLayout12() : getLayout9();
                break;
        }

        for (int[] data : layout) {
            int seatNumber = data[0];
            int col = data[1];
            int row = data[2];

            Button btn = new Button(String.valueOf(seatNumber));
            btn.getStyleClass().add("seat-button");

            if (booked.contains(seatNumber)) {
                btn.getStyleClass().add("seat-booked");
                btn.setDisable(true);
            } else {
                btn.getStyleClass().add("seat-empty");
                btn.setOnAction(e -> {
                    if (kursiTerpilihBtn != null) {
                        kursiTerpilihBtn.getStyleClass().remove("seat-selected");
                        kursiTerpilihBtn.getStyleClass().add("seat-empty");
                    }

                    kursiTerpilih = seatNumber;
                    kursiTerpilihBtn = btn;

                    btn.getStyleClass().remove("seat-empty");
                    btn.getStyleClass().add("seat-selected");
                });
            }

            seatButtons.put(seatNumber, btn);
            seatGrid.add(btn, col, row);
        }

        Label supir = new Label("supir");
        supir.getStyleClass().add("supir-label");
        seatGrid.add(supir, 3, 0);
    


        // supir
        if (totalKursi == 9) {
            //Label supir = new Label("supir");
            supir.getStyleClass().add("supir-label");
            seatGrid.add(supir, 3, 0);
        }
        
        if (totalKursi == 12) {
            //Label supir = new Label("supir");
            supir.getStyleClass().add("supir-label");
            seatGrid.add(supir, 3, 0);
        }
    }

    private int[][] getLayout9() {
        return new int[][] {
            {1, 0, 0},
            {2, 1, 1},
            {3, 2, 1},
            {4, 3, 1},
            {5, 3, 2},
            {6, 2, 2},
            {7, 0, 2},
            {8, 0, 3},
            {9, 1, 3}
        };
    }
    private int[][] getLayout12() {
        return new int[][] {
            {1, 0, 0},
            {2, 1, 1}, {3, 2, 1}, {4, 3, 1},
            {5, 3, 2}, {6, 2, 2}, {7, 0, 2},
            {8, 0, 3}, {9, 2, 3}, {10, 3, 3},
            {11, 0, 4}, {12, 1, 4}
        };
    }

    private int[][] getLayoutKereta() {
        return new int[][] {
            {1, 0, 0}, {2, 1, 0}, {3, 2, 0}, {4, 3, 0},
            {5, 0, 1}, {6, 1, 1}, {7, 2, 1}, {8, 3, 1},
            {9, 0, 2}, {10, 1, 2}, {11, 2, 2}, {12, 3, 2}
        };
    }

    private int[][] getLayoutPesawat() {
        return new int[][] {
            {1, 0, 0}, {2, 1, 0},          {3, 2, 0}, {4, 3, 0},
            {5, 0, 1}, {6, 1, 1},          {7, 2, 1}, {8, 3, 1},
            {9, 0, 2}, {10, 1, 2},         {11, 2, 2}, {12, 3, 2}
        };
    }


}

