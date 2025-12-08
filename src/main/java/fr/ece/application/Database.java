package fr.ece.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/gestion_stage?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // mets ton utilisateur MySQL
    private static final String PASSWORD = ""; // mets ton mot de passe MySQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
