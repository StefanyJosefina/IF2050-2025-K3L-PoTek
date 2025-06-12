package id.sti.potek;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import id.sti.potek.ui.PoTekLandingView;

import id.sti.potek.ui.LoginView;
import id.sti.potek.ui.RegisterView;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        PoTekLandingView view = new PoTekLandingView();
        view.setOnLoginClicked(() -> {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
            RegisterView registerView = new RegisterView();
            registerView.start(primaryStage);
        });
        view.start(primaryStage);
}


    public static void main(String[] args) {
        launch(args);
    }
}