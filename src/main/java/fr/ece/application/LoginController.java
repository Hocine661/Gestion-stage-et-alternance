package fr.ece.application;

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

    private String role = Session.getRole(); // admin ou eleve
    private int idDeclaration = Session.getIdDeclaration(); // IMPORTANT

    private final String URL = "jdbc:mysql://localhost:3306/gestion_stage";
    private final String USER = "root";
    private final String PASS = "";

    @FXML
    public void initialize() {

        // Si élève → on cache la partie admin
        if (!role.equals("admin")) {
            commentaireArea.setVisible(false);
            btnSave.setVisible(false);
            labelAdmin.setVisible(false);
        }

        chargerCommentaire();
    }

    @FXML
    public void ajouterCommentaire() {

        if (commentaireArea.getText().isEmpty()) {
            show("Erreur", "Commentaire vide !");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            // est-ce qu'un commentaire existe déjà pour cette déclaration ?
            String check = "SELECT idCommentaire FROM commentaire WHERE idDeclaration = ?";
            PreparedStatement checkPs = conn.prepareStatement(check);
            checkPs.setInt(1, idDeclaration);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // UPDATE
                String update = """
                    UPDATE commentaire
                    SET contenu = ?, date = CURDATE(), auteur = 'admin'
                    WHERE idDeclaration = ?
                """;

                PreparedStatement ps = conn.prepareStatement(update);
                ps.setString(1, commentaireArea.getText());
                ps.setInt(2, idDeclaration);
                ps.executeUpdate();

            } else {
                // INSERT
                String insert = """
                    INSERT INTO commentaire (contenu, date, auteur, idDeclaration)
                    VALUES (?, CURDATE(), 'admin', ?)
                """;

                PreparedStatement ps = conn.prepareStatement(insert);
                ps.setString(1, commentaireArea.getText());
                ps.setInt(2, idDeclaration);
                ps.executeUpdate();
            }

            commentaireAffiche.setText(commentaireArea.getText());
            commentaireArea.clear();

            show("Succès", "Commentaire enregistré !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerCommentaire() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            String sql = "SELECT contenu FROM commentaire WHERE idDeclaration = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idDeclaration);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commentaireAffiche.setText(rs.getString("contenu"));
            } else {
                commentaireAffiche.setText("Aucun commentaire pour l'instant.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

