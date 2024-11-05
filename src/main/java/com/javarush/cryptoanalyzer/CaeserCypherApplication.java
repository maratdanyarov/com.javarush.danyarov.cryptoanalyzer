package com.javarush.cryptoanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CaeserCypherApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CaeserCypherApplication.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

        CaesarCypherController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}