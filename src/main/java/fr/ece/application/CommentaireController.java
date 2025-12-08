package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

public class CommentaireController {

    @FXML
    private ComboBox<String> etudiantComboBox;

    @FXML
    private TextArea commentaireTextArea;

    @FXML
    public void initialize() {
        // Charge les étudiants (exemple)
        etudiantComboBox.getItems().addAll("Mohamed", "Sara", "Julie");
    }

    @FXML
    private void ajouterCommentaire() {

        String etudiant = etudiantComboBox.getValue();
        String commentaire = commentaireTextArea.getText();

        if (etudiant == null || commentaire.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez choisir un étudiant et entrer un commentaire.");
            alert.show();
            return;
        }

        // TODO : enregistrer le commentaire dans la base

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Succès");
        alert.setContentText("Le commentaire a été ajouté !");
        alert.show();

        commentaireTextArea.clear();
    }
}
