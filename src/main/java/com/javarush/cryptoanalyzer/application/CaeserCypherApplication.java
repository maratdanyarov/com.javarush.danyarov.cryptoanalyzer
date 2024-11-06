package com.javarush.cryptoanalyzer.application;

import com.javarush.cryptoanalyzer.controller.CaesarCypherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main application class for the Caesar Cipher Analyzer.
 * Extends the JavaFX Application class and sets up the primary stage.
 */
public class CaeserCypherApplication extends Application {
    /**
     * Entry point for the JavaFX application.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CaeserCypherApplication.class.getResource("/com/javarush/cryptoanalyzer/view/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(scene);
        stage.show();

        CaesarCypherController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}