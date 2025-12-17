package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.UserSession;

import java.io.IOException;

public class HomeAdminMainController {

    @FXML
    private Button btnDeclarations;
    @FXML
    private Button btnEtudiants;
    @FXML
    private Button btnEntreprises;
    @FXML
    private Button btnLogout;// nouveau bouton
    @FXML
    private Label adminNameLabel;

    @FXML
    public void initialize() {
        adminNameLabel.setText("Admin : " + CurrentUser.fullName);
    }


    // voir toutes les déclaration effectuer par les eleves, c'est ici que ce fera le changemnt de statut et l'ajout de commentaire
    @FXML
    private void openDeclarations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeDeclaration.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Déclaration de Stage / Alternance");
            Stage currentStage = (Stage) btnDeclarations.getScene().getWindow();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            initialize();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur : Impossible d'ouvrir le formulaire de déclaration (Vérifiez le nom du fichier FXML).").showAndWait();
        }
    }

    // voir la liste de tous les étudiants inscrient
    @FXML
    private void openEtudiants() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeUtilisateur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Déclaration de Stage / Alternance");
            Stage currentStage = (Stage) btnDeclarations.getScene().getWindow();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            initialize();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur : Impossible d'ouvrir le formulaire de déclaration (Vérifiez le nom du fichier FXML).").showAndWait();
        }

    }

    // fenetres flottante pour afficher toutes les entreprises créer par les éleves
    @FXML
    private void openEntreprises() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeEntreprise.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Déclaration de Stage / Alternance");
            Stage currentStage = (Stage) btnDeclarations.getScene().getWindow();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            initialize();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur : Impossible d'ouvrir le formulaire de déclaration (Vérifiez le nom du fichier FXML).").showAndWait();
        }

    }


    // déconnexion du compte
    @FXML
    private void logout() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Veillez confirmer que vous souhaitez vous déconnecter !", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            UserSession.logout();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Accueil");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
