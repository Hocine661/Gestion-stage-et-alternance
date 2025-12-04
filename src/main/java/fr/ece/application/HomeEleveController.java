

package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class HomeEleveController {

    @FXML private ImageView logoView;
    @FXML private Label welcomeLabel;

    // Statut
    @FXML private Label statusLabel;
    @FXML private Button declareBtn;
    @FXML private Button editDeclarationBtn;

    // Documents
    @FXML private ListView<String> docsList;

    // Entreprise
    @FXML private Label companyName, companyAddress, companyTutor, companyContact;

    // Commentaires
    @FXML private ListView<String> commentsList;

    @FXML
    public void initialize() {
        // Données d'exemple — remplace par ta BDD/tes services
        welcomeLabel.setText("Bienvenue, Sidney !");
        setStatus("En attente");

        docsList.getItems().setAll(
                "Convention de stage — Manquante",
                "Attestation — Déposée (12/10/2025)"
        );

        companyName.setText("ACME Corp.");
        companyAddress.setText("10 rue des Lilas, 75012 Paris");
        companyTutor.setText("Mme Dupont");
        companyContact.setText("dupont@acme.com / 01 45 00 00 00");

        commentsList.getItems().setAll(
                "[02/12] Merci d’ajouter la signature manquante sur la convention.",
                "[28/11] Déclaration reçue, en cours d’examen."
        );
    }

    private void setStatus(String status) {
        // Met le texte du statut et applique le style inline correspondant
        statusLabel.setText(status);
        switch (status.toLowerCase()) {
            case "validé":
            case "valide":
                statusLabel.setStyle("-fx-padding: 6 10; -fx-background-radius: 999;"
                        + "-fx-background-color: #e6ffed; -fx-text-fill: #05603a; -fx-font-weight: 700;");
                declareBtn.setVisible(false);
                editDeclarationBtn.setVisible(false);
                break;
            case "à corriger":
            case "refusé":
            case "refuse":
                statusLabel.setStyle("-fx-padding: 6 10; -fx-background-radius: 999;"
                        + "-fx-background-color: #ffe6e6; -fx-text-fill: #7a0619; -fx-font-weight: 700;");
                declareBtn.setVisible(false);
                editDeclarationBtn.setVisible(true);
                break;
            default: // en attente
                statusLabel.setStyle("-fx-padding: 6 10; -fx-background-radius: 999;"
                        + "-fx-background-color: #fff7e6; -fx-text-fill: #8a5a00; -fx-font-weight: 700;");
                boolean hasDeclaration = true; // TODO: interroger la BDD
                declareBtn.setVisible(!hasDeclaration);
                editDeclarationBtn.setVisible(hasDeclaration);
        }
    }

    // ===== Menu =====
    @FXML private void goDashboard()    { /* déjà ici */ }
    @FXML private void goDeclarations() { /* TODO: ouvrir vue déclarations */ }
    @FXML private void goDocuments()    { /* TODO: ouvrir module documents */ }
    @FXML private void goProfile()      { /* TODO: ouvrir vue profil */ }
    @FXML private void logout()         { /* TODO: retour page login */ }

    // ===== Actions =====
    @FXML private void declareInternship() { /* TODO: ouvrir formulaire de déclaration */ }
    @FXML private void editInternship()    { /* TODO: ouvrir formulaire en édition */ }
    @FXML private void uploadDocument()    { /* TODO: file chooser + upload */ }
    @FXML private void openDocuments()     { /* TODO: naviguer vers documents */ }
    @FXML private void editCompany()       { /* TODO: éditer entreprise */ }
    @FXML private void openCompany()       { /* TODO: détails entreprise */ }
    @FXML private void openCommentsHistory(){ /* TODO: historique complet */ }
}
