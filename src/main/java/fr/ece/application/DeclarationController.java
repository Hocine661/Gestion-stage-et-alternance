package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Declaration;
import model.Entreprise;
import services.ServiceDeclaration;
import services.UserSession;

import java.time.LocalDate;

public class DeclarationController {

    @FXML
    private TextField entrepriseField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField tutorField;

    @FXML
    private TextField contactField;

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private ComboBox<String> typeField;

    @FXML
    private TextArea missionField;

    @FXML
    private Button validerButton;

    private final ServiceDeclaration declarationService = new ServiceDeclaration();

    @FXML
    public void initialize() {
        if (typeField != null) {
            typeField.getSelectionModel().selectFirst();
        }
    }

    // Méthode pour la création de déclaration et d'entreprise
    @FXML
    private void validation() {
        int idUtilisateur = UserSession.getCurrentUser().getIdUtilisateur();
        String nomEntreprise = entrepriseField.getText();
        LocalDate dateDebut = dateDebutField.getValue();
        LocalDate dateFin = dateFinField.getValue();
        String type = typeField.getValue();
        String mission = missionField.getText();
        if (nomEntreprise.isEmpty() || dateDebut == null || dateFin == null) {
            new Alert(Alert.AlertType.ERROR, "Veuillez remplir au moins le nom de l'entreprise et les dates.").showAndWait();
            return;
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setNom(nomEntreprise);
        entreprise.setAdresse(addressField.getText());
        entreprise.setTuteur(tutorField.getText());
        entreprise.setContact(contactField.getText());
        Declaration declaration = new Declaration();
        declaration.setDateDebut(dateDebut);
        declaration.setDateFin(dateFin);
        declaration.setType(type);
        declaration.setMission(mission);
        boolean success = declarationService.declareStage(declaration, entreprise, idUtilisateur);
        if (success) {
            new Alert(Alert.AlertType.INFORMATION, "Déclaration enregistrée avec succès !").showAndWait();
            Stage stage = (Stage) validerButton.getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement de la déclaration. Vérifiez les données.").showAndWait();
        }
    }
}
