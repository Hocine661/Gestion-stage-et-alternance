

package fr.ece.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Declaration;
import model.Entreprise;
import model.Utilisateur;
import services.EntrepriseService;
import services.ServiceDeclaration;
import services.UserSession;

import java.io.IOException;

public class HomeEleveController {


    private final ServiceDeclaration serviceDeclaration = new ServiceDeclaration();
    private final EntrepriseService entrepriseService = new EntrepriseService();

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
        Utilisateur currentUser = UserSession.getCurrentUser();
        String prenom = currentUser != null? currentUser.getPrenom(): "Etudiant";
        welcomeLabel.setText("Welcome " + prenom);

        docsList.getItems().setAll(
                "Convention de stage — Manquante",
                "Attestation — Déposée (12/10/2025)"
        );

        Declaration declaration = serviceDeclaration.findActiveDeclarationByEleveId(currentUser.getIdUtilisateur());
        docsList.getItems().clear();
        commentsList.getItems().clear();
        clearCompanyInfo();

        if (declaration != null) {
            setStatus(declaration.getStatut(), true);
            Entreprise entreprise = entrepriseService.getEntrepriseById(declaration.getIdEntreprise());

            if (entreprise != null) {
                companyName.setText(entreprise.getNom());
                companyAddress.setText(entreprise.getAdresse());
                companyTutor.setText(entreprise.getTuteur());
                companyContact.setText(entreprise.getContact());
            }

            // Ajouter la base pour celui la
            docsList.getItems().setAll(
                    "Convention de stage — Déposée (via BDD)",
                    "Attestation — Manquante"
            );

        } else {
            setStatus("Nouveau", false);
        }
    }

   //Effacer les champs si plus nécissaires, par exemple refusé
    private void clearCompanyInfo() {
        companyName.setText("—");
        companyAddress.setText("—");
        companyTutor.setText("—");
        companyContact.setText("—");
    }

    private void setStatus(String status, boolean hasDeclaration) {
        statusLabel.setText(status);
        boolean isDeclared = hasDeclaration;
        switch (status.toLowerCase()) {
            case "validé":
            case "valide":
                isDeclared = true;
                break;
            case "à corriger":
            case "refusé":
            case "refuse":
                isDeclared = true;
                break;
            default:
                statusLabel.setStyle("-fx-padding: 6 10; -fx-background-radius: 999;"
                        + "-fx-background-color: #fff7e6; -fx-text-fill: #8a5a00; -fx-font-weight: 700;");

                if (!hasDeclaration) {
                    statusLabel.setText("Non déclarée"); // Afficher un statut clair pour le nouveau
                }
                break;
        }
        declareBtn.setVisible(!isDeclared);
        editDeclarationBtn.setVisible(isDeclared);
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
