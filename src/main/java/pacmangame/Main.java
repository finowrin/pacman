package pacmangame;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Main class to allow our game to run as a stand alone application
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pacman");

        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/fxmls/start.fxml")));

        Scene st = new Scene(root);
        stage.setScene(st);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}