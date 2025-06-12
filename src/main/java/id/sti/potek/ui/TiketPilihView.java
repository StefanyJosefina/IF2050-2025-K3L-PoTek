package id.sti.potek.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import id.sti.potek.controller.TiketController;
import id.sti.potek.model.Tiket;
import id.sti.potek.model.User;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TiketPilihView {
    private int kursiTerpilih = -1;
    private Button kursiTerpilihBtn = null;
    private Tiket tiketTerpilih = null;
    private GridPane seatGrid;
    private final Map<Integer, Button> seatButtons = new HashMap<>();
    private CheckBox keretaCheckbox;
    private CheckBox busCheckbox;
    private CheckBox pesawatCheckbox;
    private VBox dropdownBox;
    private ScrollPane scrollPaneJadwal;
    private List<Tiket> allTiketList;
    private User loggedInUser;

    // Konstruktor default
    public TiketPilihView() {
        this.loggedInUser = null;
    }

    // Konstruktor dengan user
    public TiketPilihView(User user) {
        this.loggedInUser = user;
    }

    public void start(Stage stage, List<Tiket> res, String asal, String tujuan, String tanggal) {
        this.allTiketList = res;

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

        VBox scheduleList = new VBox(15);
        for (Tiket tiket : res) {
            // Create the main schedule item container
            StackPane itemContainer = new StackPane();
            itemContainer.getStyleClass().add("schedule-item");
            
            // Create the content VBox (left side)
            VBox contentBox = new VBox(5);
            Label time = new Label(tiket.getJam());
            time.getStyleClass().add("time-label");
            int waktu_perjalanan = tiket.getWaktuPerjalanan();
            Label est = new Label("(Estimasi perjalanan " + waktu_perjalanan +" jam)");
            est.getStyleClass().add("availability-label");
            Label available = new Label("Tersedia " + tiket.getTersediaKursi() + " Kursi");
            available.getStyleClass().add("availability-label");
            contentBox.getChildren().addAll(time, est, available);
            
            // Create transportation icon (top right)
            ImageView transportIcon = createTransportationIcon(tiket.getIdTiket());
            
            // Position the content and icon
            StackPane.setAlignment(contentBox, Pos.CENTER_LEFT);
            StackPane.setAlignment(transportIcon, Pos.TOP_RIGHT);
            StackPane.setMargin(transportIcon, new Insets(10, 10, 0, 0));
            
            itemContainer.getChildren().addAll(contentBox, transportIcon);
            
            itemContainer.setOnMouseClicked(e -> {
                for (var node : scheduleList.getChildren()) {
                    node.getStyleClass().remove("selected-schedule");
                }
                itemContainer.getStyleClass().add("selected-schedule");
                tiketTerpilih = tiket;
                renderSeats(tiket.getIdTiket());
            });
            
            scheduleList.getChildren().add(itemContainer);
        }

        // Create filter button with proper styling
        ImageView filterIcon = new ImageView();
        try {
            Image filterImage = new Image(getClass().getResourceAsStream("/icons/filter.png"));
            filterIcon.setImage(filterImage);
        } catch (Exception e) {
            // Fallback to emoji if icon not found
            System.err.println("⚠️ Filter icon not found, using emoji fallback");
        }
        filterIcon.setFitWidth(20);
        filterIcon.setFitHeight(20);

        StackPane filterButton = new StackPane(filterIcon);
        filterButton.getStyleClass().add("filter-button");
        filterButton.setAlignment(Pos.CENTER);

        // Create checkboxes with proper styling
        keretaCheckbox = new CheckBox("Kereta");
        keretaCheckbox.getStyleClass().add("check-box");
        
        busCheckbox = new CheckBox("Bus");
        busCheckbox.getStyleClass().add("check-box");
        busCheckbox.setSelected(true);
        
        pesawatCheckbox = new CheckBox("Pesawat");
        pesawatCheckbox.getStyleClass().add("check-box");

        // Create dropdown title
        Label filterTitle = new Label("Mode Transportasi");
        filterTitle.getStyleClass().add("filter-title");

        // Create dropdown container with proper styling
        dropdownBox = new VBox(filterTitle, keretaCheckbox, busCheckbox, pesawatCheckbox);
        dropdownBox.getStyleClass().add("filter-dropdown");
        dropdownBox.setVisible(false);

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

            // Validasi login
            if (loggedInUser == null) {
                showDialog("Anda harus login terlebih dahulu untuk memesan.");
                Stage loginStage = new Stage();
                loginStage.initModality(Modality.APPLICATION_MODAL);
                new LoginView().start(loginStage);
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

        // Create filter area with proper alignment
        VBox filterArea = new VBox(filterButton);
        filterArea.setAlignment(Pos.TOP_RIGHT);
        filterArea.setSpacing(10);

        // Create route label
        Label routeLabel = new Label(asal + " → " + tujuan);
        routeLabel.getStyleClass().add("route-label");

        // Create scroll pane for schedule
        scrollPaneJadwal = new ScrollPane(scheduleList);
        scrollPaneJadwal.setFitToWidth(true);
        scrollPaneJadwal.setPrefHeight(500);
        scrollPaneJadwal.getStyleClass().add("schedule-scroll");

        // Create left panel with proper layout
        VBox leftPanel = new VBox(15);
        leftPanel.setPrefWidth(400);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.getChildren().addAll(filterArea, routeLabel, scrollPaneJadwal);

        // Filter button click handler
        filterButton.setOnMouseClicked(e -> {
            if (leftPanel.getChildren().contains(dropdownBox)) {
                leftPanel.getChildren().remove(dropdownBox);
            } else {
                dropdownBox.setVisible(true);
                int index = leftPanel.getChildren().indexOf(filterArea);
                leftPanel.getChildren().add(index + 1, dropdownBox);
            }
        });

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

        // Add filter listeners
        ChangeListener<Boolean> filterListener = (obs, oldVal, newVal) -> {
            updateScheduleList();
        };

        keretaCheckbox.selectedProperty().addListener(filterListener);
        busCheckbox.selectedProperty().addListener(filterListener);
        pesawatCheckbox.selectedProperty().addListener(filterListener);

        stage.setTitle("Pilih Tiket - " + asal + " ke " + tujuan);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Creates transportation icon based on ticket ID prefix
     * K = Kereta (Train), P = Pesawat (Plane), T = Bus
     */
    private ImageView createTransportationIcon(String tiketId) {
        String prefix = tiketId.substring(0, 1).toUpperCase();
        String iconPath;
        String fallbackEmoji;
        
        switch (prefix) {
            case "K": // Kereta (Train)
                iconPath = "/icons/train-icon.png";
                fallbackEmoji = "🚆";
                break;
            case "P": // Pesawat (Plane)
                iconPath = "/icons/plane-icon.png";
                fallbackEmoji = "✈️";
                break;
            case "T": // Bus
            default:
                iconPath = "/icons/bus-icon.png";
                fallbackEmoji = "🚌";
                break;
        }
        
        ImageView iconView = new ImageView();
        iconView.setFitHeight(24);
        iconView.setFitWidth(24);
        iconView.getStyleClass().add("transport-icon");
        
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(icon);
        } catch (Exception e) {
            System.err.println("⚠️ Icon not found: " + iconPath + ", using emoji fallback");
            iconView.setImage(null);
        }
        
        return iconView;
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
        
        if (!seatGrid.getChildren().contains(supir)) {
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

    void updateScheduleList() {
        List<String> allowedTypes = new ArrayList<>();
        if (keretaCheckbox.isSelected()) allowedTypes.add("kereta");
        if (busCheckbox.isSelected()) allowedTypes.add("bus");
        if (pesawatCheckbox.isSelected()) allowedTypes.add("pesawat");

        if (allowedTypes.isEmpty()) return; 

        scrollPaneJadwal.setContent(generateFilteredItems(allowedTypes));
    }
    
    private VBox generateFilteredItems(List<String> allowedTypes) {
        VBox scheduleList = new VBox(15);
        for (Tiket tiket : allTiketList) {
            String prefix = tiket.getIdTiket().substring(0, 1).toUpperCase();
            String jenis = switch (prefix) {
                case "K" -> "kereta";
                case "P" -> "pesawat";
                case "T" -> "bus";
                default -> "lain";
            };

            if (!allowedTypes.contains(jenis)) continue;

            StackPane itemContainer = new StackPane();
            itemContainer.getStyleClass().add("schedule-item");

            VBox contentBox = new VBox(5);
            Label time = new Label(tiket.getJam());
            time.getStyleClass().add("time-label");
            Label est = new Label("(Estimasi perjalanan " + tiket.getWaktuPerjalanan() +" jam)");
            est.getStyleClass().add("availability-label");
            Label available = new Label("Tersedia " + tiket.getTersediaKursi() + " Kursi");
            available.getStyleClass().add("availability-label");
            contentBox.getChildren().addAll(time, est, available);

            ImageView transportIcon = createTransportationIcon(tiket.getIdTiket());

            StackPane.setAlignment(contentBox, Pos.CENTER_LEFT);
            StackPane.setAlignment(transportIcon, Pos.TOP_RIGHT);
            StackPane.setMargin(transportIcon, new Insets(10, 10, 0, 0));
            itemContainer.getChildren().addAll(contentBox, transportIcon);

            itemContainer.setOnMouseClicked(e -> {
                for (var node : scheduleList.getChildren()) {
                    node.getStyleClass().remove("selected-schedule");
                }
                itemContainer.getStyleClass().add("selected-schedule");
                tiketTerpilih = tiket;
                renderSeats(tiket.getIdTiket());
            });

            scheduleList.getChildren().add(itemContainer);
        }
        return scheduleList;
    }
}
