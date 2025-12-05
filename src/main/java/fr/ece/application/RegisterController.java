package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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





    @FXML
    private void inscription() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = mdpField.getText();

        // choix du role
        RadioButton selectedRadio = (RadioButton) roleGroup.getSelectedToggle();
        String role = selectedRadio != null ?
                (selectedRadio.getText().equalsIgnoreCase("Eleve") ? "eleve" : "admin") :
                null;


        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || role == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs et choisir un rôle.");
            alert.showAndWait();
            return;
        }

        //En attent de la création d'un package pour gerer la connexion, les mots de passes.....
        boolean success = .registerUser(nom, prenom, email, motDePasse, role);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscription réussie !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'inscription. Verifier votre email.");
            alert.showAndWait();
        }
    }
}
