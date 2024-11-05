module com.javarush.cryptoanalyzer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.javarush.cryptoanalyzer to javafx.fxml;
    exports com.javarush.cryptoanalyzer;
}