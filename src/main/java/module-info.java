module fr.ece.application {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.ece.application to javafx.fxml;
    exports fr.ece.application;
}