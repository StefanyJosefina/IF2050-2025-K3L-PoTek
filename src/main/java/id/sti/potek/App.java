package id.sti.potek;


import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plain Stage");
        javafx.scene.control.Label label = new javafx.scene.control.Label("Just words");
        javafx.scene.Scene scene = new javafx.scene.Scene(new javafx.scene.layout.StackPane(label), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
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