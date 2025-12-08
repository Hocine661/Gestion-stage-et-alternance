package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Utilisateur;

public class ListeUtilisateur {

    @FXML
    private TableView<Utilisateur> userTable;

    @FXML
    private TableColumn<Utilisateur, String> nomCell;

    @FXML
    private TableColumn<Utilisateur, String> prenomCell;

    @FXML
    private TableColumn<Utilisateur, String> emailCell;

    @FXML
    private void initialize() {
        // Liaison des colonnes avec les propriétés de la classe Utilisateur
        nomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCell.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCell.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    // Méthode pour remplir le tableau depuis une liste existante
    public void setUsers(java.util.List<Utilisateur> utilisateurs) {
        userTable.getItems().setAll(utilisateurs);
    }
}
