package fr.ece.application;
// page réservé a l'admin pour afficher la liste des étudiants inscrit

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListeUtilisateur {

    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, String> nomCell;
    @FXML
    private TableColumn<Utilisateur, String> prenomCell;
    @FXML
    private TableColumn<Utilisateur, String> emailCell;

    private ObservableList<Utilisateur> userList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCell.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCell.setCellValueFactory(new PropertyValueFactory<>("email"));
        System.out.println("Initialisation ListeUtilisateur");
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        String query = "SELECT nom, prenom, email FROM utilisateur WHERE role='eleve'";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connexion réussie et requête exécutée");

            userList.clear();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");

                System.out.println("Utilisateur chargé : " + nom + " " + prenom + " " + email);

                userList.add(new Utilisateur(nom, prenom, email));
            }

            userTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
