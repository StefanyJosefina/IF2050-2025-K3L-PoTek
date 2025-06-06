package id.sti.potek.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginPesanView {

    private Stage stage;
    private Runnable onOkCallback;

    public LoginPesanView(String message) {
        this(message, null);
    }

    public LoginPesanView(String message, Runnable onOkCallback) {
        this.onOkCallback = onOkCallback;

        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Message");

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("pesan-label");

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("pesan-button");
        okButton.setOnAction(e -> {
            stage.close();
            if (this.onOkCallback != null) {
                this.onOkCallback.run();
            }
        });

        HBox messageBox = new HBox(10, messageLabel);
        messageBox.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(20, messageBox, okButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("pesan-root");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/pesanlogin.css").toExternalForm());

        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }
}
