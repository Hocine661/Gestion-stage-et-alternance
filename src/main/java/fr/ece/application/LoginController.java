package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import model.Utilisateur;
import services.SecuriteEtService;
import services.UserSession;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField mdpField;
    private final SecuriteEtService securiteService = new SecuriteEtService();


    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String motDePasseClair = mdpField.getText();
        if (email.isEmpty() || motDePasseClair.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer votre email et votre mot de passe.");
            alert.showAndWait();
            return;
        }
        Utilisateur utilisateur = securiteService.loginUser(email, motDePasseClair);

        if (utilisateur != null) {
            UserSession.login(utilisateur);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Connexion réussie ! Bienvenue " + utilisateur.getPrenom() + ".");
            alert.showAndWait();
            redirectToHome(utilisateur.getRole());

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Identifiants invalides : email ou mot de passe incorrect.");
            alert.showAndWait();
        }
    }
    private void redirectToHome(String role) {
        String fxmlFile;
        if ("eleve".equalsIgnoreCase(role)) {
            fxmlFile = "homeEleve.fxml";
        } else if ("admin".equalsIgnoreCase(role)) {
            fxmlFile = "homeAdmin.fxml";
        } else {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil - Gestion Stages/Alternances");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur de chargement de l'interface : " + fxmlFile);
            alert.showAndWait();
        }
    }
}