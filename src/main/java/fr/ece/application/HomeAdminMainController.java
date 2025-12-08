package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeAdminMainController {

    @FXML private Button btnDeclarations;
    @FXML private Button btnEtudiants;
    @FXML private Button btnEntreprises;
    @FXML private Button btnCommentaires;
    @FXML private Button btnLogout;
    @FXML private Label adminNameLabel;

    @FXML
    public void initialize() {

        // Affichage du nom admin
        adminNameLabel.setText("Admin : " + CurrentUser.fullName);

        btnDeclarations.setOnAction(e -> openPage("homeAdminDeclarations.fxml"));
        btnEtudiants.setOnAction(e -> openPage("listeEtudiants.fxml"));
        btnEntreprises.setOnAction(e -> openPage("AdminEntreprises.fxml"));
        btnCommentaires.setOnAction(e -> openPage("commentaire.fxml"));

        // Déconnexion
        btnLogout.setOnAction(e -> openPage("login.fxml"));
    }

    private void openPage(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) btnDeclarations.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
    @FXML
    private void openDeclarations() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/listeDeclaration.fxml"));
        Parent root = loader.load();
        btnDeclarations.getScene().setRoot(root);
    }

    @FXML
    private void openEtudiants() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/listeUtilisateur.fxml"));
        Parent root = loader.load();
        btnEtudiants.getScene().setRoot(root);
    }


    @FXML
    private void openEntreprises() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/listeEntreprise.fxml"));
        Parent root = loader.load();
        btnEntreprises.getScene().setRoot(root);
    }


    @FXML
    private void openCommentaires() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/commentaire.fxml"));
        Parent root = loader.load();
        btnCommentaires.getScene().setRoot(root);
    }

}
