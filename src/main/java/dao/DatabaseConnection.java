package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_stage";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ton mot de passe MySQL

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Erreur connexion DB : " + e.getMessage());
            return null;
        }
    }
}
