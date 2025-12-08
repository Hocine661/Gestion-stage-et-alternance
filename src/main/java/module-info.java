module fr.ece.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires jbcrypt;
    requires mysql.connector.j;


    // <<< OBLIGATOIRE POUR UTILISER JDBC

    opens fr.ece.application to javafx.fxml;
    exports fr.ece.application;
    exports dao;
    opens dao;
}