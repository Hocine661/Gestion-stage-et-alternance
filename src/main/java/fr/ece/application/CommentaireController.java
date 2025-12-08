package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage; // Pour fermer la vue si nécessaire
import java.sql.*;
import java.time.LocalDate; // Pour la date du commentaire

// Importations correctes
import services.UserSession;
import model.Utilisateur;
import dao.DatabaseConnection; // Supposé être dans le package 'dao'
import model.Declaration;

public class CommentaireController {

    @FXML
    private TextArea commentaireArea;

    @FXML
    private TextArea commentaireAffiche;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelAdmin;

    // Contexte de l'utilisateur connecté
    private Utilisateur currentUser = UserSession.getCurrentUser();
    private String roleUtilisateur = currentUser != null ? currentUser.getRole() : "invite";
    private String nomAuteur = currentUser != null ? currentUser.getPrenom() + " " + currentUser.getNom() : "Système";

    // Contexte essentiel : L'ID de la Déclaration actuelle
    private int declarationId = -1;


    @FXML
    public void initialize() {
        // Initialisation minimale, la logique d'accès est dans updateViewAccess.
        // On désactive tout par défaut
        commentaireArea.setDisable(true);
        btnSave.setDisable(true);

        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de session", "Aucun utilisateur connecté.");
        }
    }

    /**
     * Méthode à appeler par le contrôleur parent (ex: HomeEleveController)
     * pour définir le contexte de la déclaration.
     */
    public void setDeclarationContext(Declaration declaration) {
        if (declaration != null) {
            this.declarationId = declaration.getIdDeclaration();
            // Si vous avez un label pour afficher l'ID de la déclaration, mettez-le à jour ici.
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de contexte", "L'ID de la Déclaration est manquant. Fonctionnalité commentaire désactivée.");
        }

        updateViewAccess();
        chargerCommentaire();
    }

    /**
     * Met à jour la visibilité et l'accès en fonction du rôle.
     */
    private void updateViewAccess() {
        // Le rôle 'eleve' est l'élève, 'admin' ou 'scolarité' (d'après register.fxml) est l'administrateur.
        boolean isAdmin = "admin".equalsIgnoreCase(roleUtilisateur) || "scolarité".equalsIgnoreCase(roleUtilisateur);

        // Seul l'admin a le droit d'écrire/modifier
        commentaireArea.setDisable(!isAdmin);
        btnSave.setDisable(!isAdmin);
        labelAdmin.setVisible(isAdmin); // Assurez-vous que ce label n'est visible que pour l'admin
    }


    @FXML
    public void ajouterCommentaire() {
        if (declarationId <= 0) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le contexte de déclaration est manquant. Sauvegarde impossible.");
            return;
        }
        if (commentaireArea.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Le commentaire est vide !");
            return;
        }

        // Utilisation de la connexion centralisée (comme dans les DAOs)
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Requête corrigée : utilise idDeclaration, date, et auteur.
            // ON DUPLICATE KEY UPDATE ne fonctionne que si vous avez mis une clé UNIQUE sur idDeclaration dans votre BDD.
            String sql = """
                INSERT INTO commentaire (contenu, date, auteur, idDeclaration)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE contenu = ?, date = ?, auteur = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, commentaireArea.getText());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, nomAuteur);
            ps.setInt(4, declarationId); // Correction : Utiliser l'ID de la déclaration

            // Pour l'UPDATE
            ps.setString(5, commentaireArea.getText());
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setString(7, nomAuteur);

            ps.executeUpdate();

            // Mettre à jour l'affichage
            commentaireAffiche.setText(commentaireArea.getText());
            commentaireArea.clear(); // Effacer la zone de saisie pour confirmer l'envoi

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Commentaire enregistré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Problème d'enregistrement du commentaire en base de données.");
        }
    }

    private void chargerCommentaire() {
        if (declarationId <= 0) return;

        // Utilisation de la connexion centralisée (comme dans les DAOs)
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Correction : Chercher par idDeclaration. On prend le dernier pour affichage.
            String sql = "SELECT contenu FROM commentaire WHERE idDeclaration = ? ORDER BY date DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, declarationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commentaireAffiche.setText(rs.getString("contenu"));
                // Remplir l'aire de saisie (pour l'admin) avec le commentaire chargé
                if (!commentaireArea.isDisable()) {
                    commentaireArea.setText(rs.getString("contenu"));
                }
            } else {
                commentaireAffiche.setText("Aucun commentaire de l'administration pour cette déclaration.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String titre, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}