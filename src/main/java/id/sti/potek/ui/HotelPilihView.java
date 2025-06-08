package id.sti.potek.ui;

import id.sti.potek.model.Kamar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class HotelPilihView {
    private VBox hotelListContainer;
    private ToggleGroup filterGroup;

    public void start(Stage stage, List<Kamar> hasil, List<String> unlocked) {
        // Header
        ImageView icon = new ImageView(new Image("/icons/kasur-icon.png"));
        icon.setFitHeight(28);
        icon.setFitWidth(28);
        Text title = new Text("Hotel");
        title.getStyleClass().add("banner-text");
        HBox header = new HBox(10, icon, title);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("banner-header");

        // Filter button + options
        ImageView filterImg = new ImageView(new Image("/icons/filter.png"));
        filterImg.setFitHeight(20);
        filterImg.setFitWidth(20);
        StackPane filterBtn = new StackPane(filterImg);
        filterBtn.setAlignment(Pos.CENTER_RIGHT);
        filterBtn.getStyleClass().add("filter-button");

        RadioButton hargaBtn = new RadioButton("Harga");
        RadioButton ratingBtn = new RadioButton("Rating");
        hargaBtn.getStyleClass().add("filter-radio");
        ratingBtn.getStyleClass().add("filter-radio");
        filterGroup = new ToggleGroup();
        hargaBtn.setToggleGroup(filterGroup);
        ratingBtn.setToggleGroup(filterGroup);

        HBox filterBox = new HBox(10, hargaBtn, ratingBtn);
        filterBox.setPadding(new Insets(0, 10, 0, 0));
        filterBox.setAlignment(Pos.CENTER_RIGHT);
        VBox filterContainer = new VBox(filterBtn, filterBox);
        filterContainer.setAlignment(Pos.TOP_RIGHT);

        // Card list scrollable
        hotelListContainer = new VBox(20);
        hotelListContainer.setPadding(new Insets(20));
        updateHotelList(hasil);

        ScrollPane scrollPane = new ScrollPane(hotelListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(520);
        scrollPane.getStyleClass().add("hotel-scroll");

        // Top layout
        HBox topRow = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(spacer, filterContainer);

        VBox root = new VBox(30, header, topRow, scrollPane);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("main-background");

        // Listener for filter
        filterGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal == hargaBtn) {
                    hasil.sort(Comparator.comparingInt(Kamar::getHarga));
                } else if (newVal == ratingBtn) {
                    hasil.sort(Comparator.comparingDouble(Kamar::getRating).reversed());
                }
                updateHotelList(hasil);
            }
        });

        Scene scene = new Scene(root, 900, 645);
        scene.getStylesheets().add(getClass().getResource("/css/pilih_hotel.css").toExternalForm());
        stage.setTitle("Pilih Hotel");
        stage.setScene(scene);
        stage.show();
    }

    private void updateHotelList(List<Kamar> hasil) {
        hotelListContainer.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        int col = 0, row = 0;
        for (Kamar kamar : hasil) {
            VBox card = createHotelCard(kamar);
            grid.add(card, col, row);
            col++;
            if (col >= 2) {
                col = 0;
                row++;
            }
        }
        hotelListContainer.getChildren().add(grid);
    }

    private VBox createHotelCard(Kamar kamar) {
        VBox card = new VBox(5);
        card.getStyleClass().add("hotel-card");
        card.setPadding(new Insets(12));

        Region thumbnail = new Region();
        thumbnail.setPrefSize(80, 80);
        thumbnail.getStyleClass().add("thumbnail-placeholder");

        Label name = new Label(kamar.getNamaHotel());
        name.getStyleClass().add("hotel-name");
        Label lokasi = new Label(kamar.getLokasi());
        lokasi.getStyleClass().add("hotel-location");

        HBox ratingBox = new HBox(5);
        ImageView bintang = new ImageView(new Image("/icons/star.png"));
        bintang.setFitHeight(16);
        bintang.setFitWidth(16);
        Label rating = new Label(String.format("%.1f/5 (%d review)", kamar.getRating(), kamar.getJumlahUlasan()));
        rating.getStyleClass().add("hotel-rating");
        ratingBox.getChildren().addAll(bintang, rating);

        Label harga = new Label("IDR " + kamar.getHarga());
        harga.getStyleClass().add("harga-text");

        Label note = new Label("*Belum termasuk pajak");
        note.getStyleClass().add("harga-note");

        card.getChildren().addAll(thumbnail, name, lokasi, ratingBox, harga, note);
        return card;
    }
}