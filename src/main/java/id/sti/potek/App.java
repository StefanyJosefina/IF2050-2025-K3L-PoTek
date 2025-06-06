package id.sti.potek;

import id.sti.potek.ui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MainView().start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}