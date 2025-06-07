package id.sti.potek;

import javafx.application.Application;
import javafx.stage.Stage;
import id.sti.potek.ui.UlasanFormView;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        new UlasanFormView().start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
