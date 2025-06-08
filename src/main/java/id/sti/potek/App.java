package id.sti.potek;

import javafx.application.Application;
import javafx.stage.Stage;
import id.sti.potek.ui.HotelCariView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        HotelCariView view = new HotelCariView();
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
