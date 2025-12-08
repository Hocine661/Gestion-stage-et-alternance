package fr.ece.application;

import com.mysql.cj.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class CommentaireController {

    @FXML
    private TextArea commentaireArea;

    @FXML
    private TextArea commentaireAffiche;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelAdmin;


    private String roleUtilisateur = Session.getRole(); // "admin" ou "etudiant"
    private int idEtudiant = Session.getIdEtudiant();

    @FXML
    public void initialize() {

        // Si l'utilisateur est étudiant → pas le droit d'écrire
        if (!roleUtilisateur.equals("admin")) {
            commentaireArea.setVisible(false);
            btnSave.setVisible(false);
            labelAdmin.setVisible(false);
        }

        // Charger le commentaire existant
        chargerCommentaire();
    }

    @FXML
    public void ajouterCommentaire() {

        if (commentaireArea.getText().isEmpty()) {
            showAlert("Erreur", "Le commentaire est vide !");
            return;
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_stage",
                "root", "")) {

            String sql = """
                INSERT INTO commentaire (id_etudiant, contenu)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE contenu = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEtudiant);
            ps.setString(2, commentaireArea.getText());
            ps.setString(3, commentaireArea.getText());

            ps.executeUpdate();

            commentaireAffiche.setText(commentaireArea.getText());
            commentaireArea.clear();

            showAlert("Succès", "Commentaire enregistré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerCommentaire() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_stage",
                "root", "")) {

            String sql = "SELECT contenu FROM commentaire WHERE id_etudiant = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEtudiant);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commentaireAffiche.setText(rs.getString("contenu"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
