module com.javarush.cryptoanalyzer {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.javarush.cryptoanalyzer.application;
    opens com.javarush.cryptoanalyzer.controller to javafx.fxml;
    opens com.javarush.cryptoanalyzer.view to javafx.fxml;
}