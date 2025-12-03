package test;

import dao.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println(" Connexion MySQL réussie !");
        } else {
            System.out.println(" Échec de la connexion.");
        }
    }
}
