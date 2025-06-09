package id.sti.potek;


import javafx.application.Application;
import javafx.stage.Stage;
import id.sti.potek.ui.PoTekLandingView;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        PoTekLandingView view = new PoTekLandingView();
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// import javafx.application.Application;
// import javafx.stage.Stage;

// public class App extends Application {
//     @Override
//     public void start(Stage primaryStage) {
//         primaryStage.setTitle("Hello JavaFX!");
//         primaryStage.show(); // <= WAJIB: Tanpa ini tidak muncul apa-apa
//     }

//     public static void main(String[] args) {
//         launch(args); // <= WAJIB untuk memulai JavaFX
//     }
// }