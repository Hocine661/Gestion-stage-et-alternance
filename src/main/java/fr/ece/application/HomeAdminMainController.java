package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeAdminMainController {

    @FXML private Button btnDeclarations;
    @FXML private Button btnEtudiants;
    @FXML private Button btnEntreprises;
    @FXML private Button btnCommentaires;
    @FXML private Button btnLogout;
    @FXML private Button btnUtilisateurs; // nouveau bouton
    @FXML private Label adminNameLabel;

    @FXML
    public void initialize() {
        // Affichage du nom admin
        adminNameLabel.setText("Admin : " + CurrentUser.fullName);
    }





    @FXML
    private void openDeclarations()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeDeclaration.fxml")); // Utilisez le nom exact de votre fichier FXML
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

    @FXML
    private void openEtudiants()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeUtilisateur.fxml")); // Utilisez le nom exact de votre fichier FXML
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

    @FXML
    private void openEntreprises()  {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeEntreprise.fxml")); // Utilisez le nom exact de votre fichier FXML
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

    @FXML
    private void openCommentaires() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/commentaire.fxml"));
        Parent root = loader.load();
        btnCommentaires.getScene().setRoot(root);
    }

    // ✅ NOUVELLE MÉTHODE POUR LE BOUTON LISTEUTILISATEUR
    @FXML
    private void openUtilisateurs() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ece/application/listeUtilisateur.fxml"));
        Parent root = loader.load();
        btnUtilisateurs.getScene().setRoot(root);
    }

}
