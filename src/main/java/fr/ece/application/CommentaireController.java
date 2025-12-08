package fr.ece.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

import services.UserSession;
import model.Utilisateur;
import dao.DatabaseConnection;
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

    private Utilisateur currentUser;
    private String roleUtilisateur;
    private String nomAuteur;
    private int declarationId = -1;

    @FXML
    public void initialize() {

        // RÉCUPÉRATION DE LA SESSION AU BON MOMENT
        currentUser = UserSession.getCurrentUser();

        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de session", "Aucun utilisateur connecté.");
            return;
        }

        roleUtilisateur = currentUser.getRole();
        nomAuteur = currentUser.getPrenom() + " " + currentUser.getNom();

        // Par défaut, tout est désactivé
        commentaireArea.setDisable(true);
        btnSave.setDisable(true);

        updateViewAccess();

        // Pour debug / visibilité du rôle
        labelAdmin.setText("Connecté en tant que : " + roleUtilisateur);
    }

    // Reçoit la déclaration sélectionnée
    public void setDeclarationContext(Declaration declaration) {
        if (declaration != null) {
            this.declarationId = declaration.getIdDeclaration();
            chargerCommentaire();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de contexte",
                    "L'ID de la Déclaration est manquant. Fonctionnalité commentaire désactivée.");
        }
    }

    // Active ou désactive la zone d'édition selon le rôle
    private void updateViewAccess() {

        boolean isAdmin = "admin".equalsIgnoreCase(roleUtilisateur)
                || "scolarité".equalsIgnoreCase(roleUtilisateur);

        commentaireArea.setDisable(!isAdmin);
        btnSave.setDisable(!isAdmin);
        labelAdmin.setVisible(isAdmin);
    }

    @FXML
    public void ajouterCommentaire() {

        boolean isAdmin = "admin".equalsIgnoreCase(roleUtilisateur)
                || "scolarité".equalsIgnoreCase(roleUtilisateur);

        // Sécurité côté BACK-END
        if (!isAdmin) {
            showAlert(Alert.AlertType.ERROR, "Accès refusé",
                    "Seul un administrateur peut ajouter un commentaire.");
            return;
        }

        if (declarationId <= 0) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le contexte de déclaration est manquant.");
            return;
        }

        if (commentaireArea.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Le commentaire est vide !");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = """
            INSERT INTO commentaire (contenu, date, auteur, idDeclaration)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE contenu = ?, date = ?, auteur = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            // INSERT
            ps.setString(1, commentaireArea.getText());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, nomAuteur);
            ps.setInt(4, declarationId);

            // UPDATE
            ps.setString(5, commentaireArea.getText());
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setString(7, nomAuteur);

            ps.executeUpdate();

            commentaireAffiche.setText(commentaireArea.getText());
            commentaireArea.clear();

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Commentaire enregistré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL",
                    "Problème d'enregistrement du commentaire en base de données.");
        }
    }

    // Charge les commentaires existants (pour l'étudiant)
    private void chargerCommentaire() {

        if (declarationId <= 0) return;

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT contenu FROM commentaire WHERE idDeclaration = ? ORDER BY date DESC LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, declarationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commentaireAffiche.setText(rs.getString("contenu"));

                // Si admin, on met dans l’éditeur aussi
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

    // Méthode utilitaire pour afficher des messages
    private void showAlert(Alert.AlertType type, String titre, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
