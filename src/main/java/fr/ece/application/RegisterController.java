package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.SecuriteEtService;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField mdpField;

    @FXML
    private RadioButton eleveRadio;

    @FXML
    private RadioButton scolaritéRadio;

    @FXML
    private Button inscriptionButton;

    @FXML
    private ToggleGroup roleGroup;

    private final SecuriteEtService securiteService =  new SecuriteEtService();


    @FXML
    private void inscription() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = mdpField.getText();

        RadioButton selectedRadio = (RadioButton) roleGroup.getSelectedToggle();
        // choix du role
        String role = null;
        if (selectedRadio == eleveRadio) {
            role = "eleve";
        } else if (selectedRadio == scolaritéRadio) {
            // Nous conservons "admin" comme valeur pour le rôle 'scolarité' en BDD
            role = "admin";
        }

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || role == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs et choisir un rôle.");
            alert.showAndWait();
            return;
        }

        boolean success = securiteService.registerUser(nom, prenom, email, motDePasse, role);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscription réussie !");
            alert.showAndWait();

            redirectToLogin();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'inscription. Verifier votre email.");
            alert.showAndWait();
        }
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur: Impossible de charger la page connexion!");
            alert.showAndWait();
        }
    }
}

