package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HomeControllerUser {

    @FXML
    private TextField entrepriseField;

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private ComboBox<String> typeField;

    @FXML
    private Button validerButton;

    @FXML
    public void initialize() {

        validerButton.setOnAction(event -> {

            String entreprise = entrepriseField.getText();
            String dateDebut = String.valueOf(dateDebutField.getValue());
            String dateFin = String.valueOf(dateFinField.getValue());
            String type = typeField.getValue();

            System.out.println("Entreprise : " + entreprise);
            System.out.println("Date début : " + dateDebut);
            System.out.println("Date fin : " + dateFin);
            System.out.println("Type : " + type);

            // Tu pourras ajouter ici l’insertion en base de données
        });
    }
}
