

package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.UserSession;

import java.io.IOException;

public class HomeEleveController {

    @FXML
    private Hyperlink deconnexionButton;

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
        String prenom = UserSession.getPrenom();
        welcomeLabel.setText("Welcome " + prenom);

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
                boolean hasDeclaration = false; // TODO: interroger la BDD
                declareBtn.setVisible(!hasDeclaration);
                editDeclarationBtn.setVisible(hasDeclaration);
        }
    }

    // ===== Menu =====
    @FXML private void goDashboard()    { /* déjà ici */ }
    @FXML private void goDeclarations() { /* TODO: ouvrir vue déclarations */ }
    @FXML private void goDocuments()    { /* TODO: ouvrir module documents */ }
    @FXML private void goProfile()      { /* TODO: ouvrir vue profil */ }
    @FXML private void logout()         {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Veillez confirmer que vous souhaitez vous déconnecter !", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            UserSession.logout();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) welcomeLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Accueil");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ===== Actions =====
    @FXML private void declareInternship() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("declaration.fxml")); // Utilisez le nom exact de votre fichier FXML
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Déclaration de Stage / Alternance");
            Stage currentStage = (Stage) declareBtn.getScene().getWindow();
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
    @FXML private void editInternship()    { /* TODO: ouvrir formulaire en édition */ }
    @FXML private void uploadDocument()    { /* TODO: file chooser + upload */ }
    @FXML private void openDocuments()     { /* TODO: naviguer vers documents */ }
    @FXML private void editCompany()       { /* TODO: éditer entreprise */ }
    @FXML private void openCompany()       { /* TODO: détails entreprise */ }
    @FXML private void openCommentsHistory(){ /* TODO: historique complet */ }
}
