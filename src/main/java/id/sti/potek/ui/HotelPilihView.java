package id.sti.potek.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import id.sti.potek.dao.UlasanDAO;
import id.sti.potek.model.Kamar;
import id.sti.potek.model.User;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HotelPilihView {
    private VBox hotelListContainer;
    private UlasanDAO ulasanDAO;
    private List<Kamar> hasil;
    private List<String> unlocked;

    private String checkin;
    private String checkout;
    private int malam;
    private User loggedInUser;

    public HotelPilihView(List<Kamar> hasil, List<String> unlocked, String checkin, String checkout, int malam, User user) {
        this.hasil = hasil != null ? hasil : new ArrayList<>();
        this.unlocked = unlocked != null ? unlocked : new ArrayList<>();
        this.checkin = checkin;
        this.checkout = checkout;
        this.malam = malam;
        this.loggedInUser = user;
        
        try {
            this.ulasanDAO = new UlasanDAO();
        } catch (Exception e) {
            System.out.println("Warning: Could not initialize UlasanDAO: " + e.getMessage());
            this.ulasanDAO = null;
        }
    }
    
    public HotelPilihView(List<Kamar> hasil, String checkin, String checkout, int malam) {
        this(hasil, new ArrayList<>(), checkin, checkout, malam, null);
    }

    public void start(Stage stage) {
        try {
            if (hasil == null || hasil.isEmpty()) {
                showError(stage, "Tidak ada hotel tersedia");
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

            Text title = new Text("Pilih Hotel");
            title.getStyleClass().add("banner-text");
            
            HBox header = new HBox(10, icon, title);
            header.setAlignment(Pos.CENTER);
            header.getStyleClass().add("banner-header");

            ImageView arrowIcon = new ImageView(new Image("/icons/Vector.png"));
            arrowIcon.setFitWidth(20);
            arrowIcon.setFitHeight(20);

            Button backBtn = new Button("Kembali");
            backBtn.setGraphic(arrowIcon);
            backBtn.getStyleClass().add("back-button");
            backBtn.setOnAction(e -> {
                try {
                    Stage newStage = new Stage();
                    HotelCariView cariView = new HotelCariView(loggedInUser);
                    cariView.start(newStage);
                    stage.close();
                } catch (Exception ex) {
                    System.out.println("Error opening search view: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            ImageView filterImg = new ImageView();
            try {
                filterImg.setImage(new Image("/icons/filter.png"));
                filterImg.setFitHeight(20);
                filterImg.setFitWidth(20);
            } catch (Exception e) {
                System.out.println("Filter icon tidak ditemukan: " + e.getMessage());
                filterImg.setFitHeight(20);
                filterImg.setFitWidth(20);
            }

            Label filterLabel = new Label("Filter");
            filterLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            HBox filterHeader = new HBox(5, filterImg, filterLabel);
            filterHeader.setAlignment(Pos.CENTER_RIGHT);
            filterHeader.getStyleClass().add("filter-button");
            filterHeader.setStyle("-fx-cursor: hand; -fx-padding: 5px 10px; -fx-background-color: #f8f9fa; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px;");

            VBox filterDropdown = new VBox(5);
            filterDropdown.setVisible(false);
            filterDropdown.setManaged(false);
            filterDropdown.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1px; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

            Button defaultBtn = new Button("Default (Tanpa Filter)");
            Button hargaBtn = new Button("Harga (Termurah - Termahal)");
            Button ratingBtn = new Button("Rating (Terbaik - Terburuk)");

            defaultBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;");
            hargaBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;");
            ratingBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;");

            defaultBtn.setOnMouseEntered(e -> defaultBtn.setStyle(
                "-fx-background-color: #f8f9fa; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));
            defaultBtn.setOnMouseExited(e -> defaultBtn.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));

            hargaBtn.setOnMouseEntered(e -> hargaBtn.setStyle(
                "-fx-background-color: #f8f9fa; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));
            hargaBtn.setOnMouseExited(e -> hargaBtn.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));

            ratingBtn.setOnMouseEntered(e -> ratingBtn.setStyle(
                "-fx-background-color: #f8f9fa; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));
            ratingBtn.setOnMouseExited(e -> ratingBtn.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-text-fill: #333; -fx-alignment: center-left; -fx-cursor: hand; -fx-padding: 5px 10px;"
            ));

            filterDropdown.getChildren().addAll(defaultBtn, hargaBtn, ratingBtn);

            VBox filterContainer = new VBox();
            filterContainer.getChildren().addAll(filterHeader, filterDropdown);
            filterContainer.setAlignment(Pos.TOP_RIGHT);

            filterHeader.setOnMouseClicked(e -> {
                boolean isVisible = filterDropdown.isVisible();
                filterDropdown.setVisible(!isVisible);
                filterDropdown.setManaged(!isVisible);
            });

            hargaBtn.setOnAction(e -> {
                try {
                    hasil.sort(Comparator.comparingInt(Kamar::getHarga));
                    updateHotelList(hasil);
                    
                    filterLabel.setText("Filter: Harga");
                    
                    filterDropdown.setVisible(false);
                    filterDropdown.setManaged(false);
                    
                    System.out.println("Hotels sorted by price (lowest to highest)");
                } catch (Exception ex) {
                    System.out.println("Error sorting by price: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            ratingBtn.setOnAction(e -> {
                try {
                    hasil.sort((a, b) -> {
                        double ratingA = getAverageRating(String.valueOf(a.getId()));
                        double ratingB = getAverageRating(String.valueOf(b.getId()));
                        return Double.compare(ratingB, ratingA); 
                    });
                    updateHotelList(hasil);
                    
                    filterLabel.setText("Filter: Rating");
                    
                    filterDropdown.setVisible(false);
                    filterDropdown.setManaged(false);
                    
                    System.out.println("Hotels sorted by rating (highest to lowest)");
                } catch (Exception ex) {
                    System.out.println("Error sorting by rating: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            filterContainer.setOnMouseExited(e -> {
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(event -> {
                    if (!filterContainer.isHover()) {
                        filterDropdown.setVisible(false);
                        filterDropdown.setManaged(false);
                    }
                });
                pause.play();
            });

            hotelListContainer = new VBox(20);
            hotelListContainer.setPadding(new Insets(20));

            try {
                hasil.sort(Comparator.comparingInt(Kamar::getHarga));
            } catch (Exception e) {
                System.out.println("Error sorting by price: " + e.getMessage());
            }
            updateHotelList(hasil);

            ScrollPane scrollPane = new ScrollPane(hotelListContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(520);
            scrollPane.getStyleClass().add("hotel-scroll");

            HBox topRow = new HBox();
            topRow.setAlignment(Pos.CENTER_LEFT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            topRow.getChildren().addAll(backBtn, spacer, filterContainer);

            VBox root = new VBox(20, header, topRow, scrollPane);
            root.setPadding(new Insets(20));
            root.getStyleClass().add("main-background");

            Scene scene = new Scene(root, 900, 645);
            
            try {
                String cssPath = getClass().getResource("/css/hotel_pilih.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
                scene.getStylesheets().add(getClass().getResource("/css/back_button.css").toExternalForm());
            } catch (Exception e) {
                System.out.println("CSS file tidak ditemukan: " + e.getMessage());
                root.setStyle("-fx-background-color: #f5f5f5;");
            }

            stage.setTitle("Pilih Hotel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            System.out.println("Error in HotelPilihView.start(): " + e.getMessage());
            e.printStackTrace();
            showError(stage, "Gagal memuat halaman pilih hotel: " + e.getMessage());
        }
    }

    private void updateHotelList(List<Kamar> hasil) {
        try {
            hotelListContainer.getChildren().clear();
            
            if (hasil == null || hasil.isEmpty()) {
                Label noHotel = new Label("Tidak ada hotel tersedia");
                noHotel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
                hotelListContainer.getChildren().add(noHotel);
                return;
            }

            GridPane grid = new GridPane();
            grid.setHgap(20);
            grid.setVgap(20);

            int col = 0, row = 0;
            for (Kamar kamar : hasil) {
                if (kamar != null) {
                    VBox card = createHotelCard(kamar);
                    grid.add(card, col, row);
                    col++;
                    if (col >= 2) {
                        col = 0;
                        row++;
                    }
                }
            }
            hotelListContainer.getChildren().add(grid);
        } catch (Exception e) {
            System.out.println("Error updating hotel list: " + e.getMessage());
        }
    }

    private VBox createHotelCard(Kamar kamar) {
        VBox card = new VBox(8);
        card.getStyleClass().add("hotel-card");
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2); -fx-cursor: hand;");

        try {
            Label name = new Label(kamar.getNamaHotel() != null ? kamar.getNamaHotel() : "Hotel");
            name.getStyleClass().add("hotel-name");
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            Label lokasi = new Label(kamar.getLokasi() != null ? kamar.getLokasi() : "");
            lokasi.getStyleClass().add("hotel-location");
            lokasi.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            HBox ratingBox = new HBox(5);
            ratingBox.setAlignment(Pos.CENTER_LEFT);
            
            ImageView bintang = new ImageView();
            try {
                bintang.setImage(new Image("/icons/star.png"));
                bintang.setFitHeight(16);
                bintang.setFitWidth(16);
            } catch (Exception e) {
                System.out.println("Star icon tidak ditemukan");
                bintang.setFitHeight(16);
                bintang.setFitWidth(16);
            }

            double avgRating = getAverageRating(String.valueOf(kamar.getId()));
            Label rating = new Label(String.format("%.1f / 5", avgRating));
            rating.getStyleClass().add("hotel-rating");
            rating.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
            ratingBox.getChildren().addAll(bintang, rating);

            Label tipeKamar = new Label("Tipe: " + (kamar.getTipeKamar() != null ? kamar.getTipeKamar() : "Standard"));
            tipeKamar.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            Label harga = new Label("IDR " + String.format("%,d", kamar.getHarga()));
            harga.getStyleClass().add("harga-text");
            harga.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

            Label note = new Label("*Belum termasuk pajak");
            note.getStyleClass().add("harga-note");
            note.setStyle("-fx-font-size: 10px; -fx-text-fill: #999;");

            card.getChildren().addAll(name, lokasi, ratingBox, tipeKamar, harga, note);

            card.setOnMouseClicked(e -> {
                try {
                    if (loggedInUser == null) {
                        Stage loginStage = new Stage();
                        loginStage.initModality(Modality.APPLICATION_MODAL);
                        new LoginView().start(loginStage);
                        return;
                    }

                    Stage newStage = new Stage();
                    newStage.initModality(Modality.APPLICATION_MODAL);

                    HotelPesanView pesanView = new HotelPesanView(loggedInUser, hasil, unlocked);
                    pesanView.start(newStage, kamar, checkin, checkout, malam);
                    
                    Stage currentStage = (Stage) card.getScene().getWindow();
                    currentStage.close();

                } catch (Exception ex) {
                    System.out.println("Error opening booking view: " + ex.getMessage());
                    ex.printStackTrace();
                    showError(null, "Gagal membuka halaman pemesanan");
                }
            });

            card.setOnMouseEntered(e -> {
                card.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 3); -fx-cursor: hand;");
            });

            card.setOnMouseExited(e -> {
                card.setStyle("-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2); -fx-cursor: hand;");
            });

        } catch (Exception e) {
            System.out.println("Error creating hotel card: " + e.getMessage());
        }

        return card;
    }

    private double getAverageRating(String kamarId) {
        try {
            if (ulasanDAO != null) {
                return ulasanDAO.getAverageRating(kamarId);
            }
        } catch (Exception e) {
            System.out.println("Error getting average rating: " + e.getMessage());
        }
        return 4.0; 
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
}