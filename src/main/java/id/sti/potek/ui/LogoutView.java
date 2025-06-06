package id.sti.potek.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogoutView {

    private Runnable onCloseCallback;

    public LogoutView() {
        this(null);
    }

    public LogoutView(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    public void start(Stage ownerStage) {
        // Create the modal stage
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initOwner(ownerStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Close button
        Button closeButton = new Button("✕");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> {
            stage.close();
            if (this.onCloseCallback != null) {
                this.onCloseCallback.run();
            }
        });

        // Logout success message
        Label message = new Label("Berhasil logout!");
        message.getStyleClass().add("logout-message");

        StackPane modalContent = new StackPane();
        modalContent.getStyleClass().add("logout-modal");

        VBox messageBox = new VBox(message);
        messageBox.setAlignment(Pos.CENTER);

        modalContent.getChildren().addAll(messageBox, closeButton);
        StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);
        StackPane.setMargin(closeButton, new Insets(0));

        StackPane root = new StackPane(modalContent);
        root.getStyleClass().add("logout-root");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 200);
        scene.setFill(null);
        scene.getStylesheets().add(getClass().getResource("/css/logout.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}