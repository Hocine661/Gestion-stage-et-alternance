package dao;

import model.Commentaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentaireDAO {

    public List<Commentaire> findByDeclaration(int idDeclaration) {
        List<Commentaire> commentaires = new ArrayList<>();

        String sql = "SELECT * FROM commentaire WHERE idDeclaration = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idDeclaration);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setIdCommentaire(rs.getInt("idCommentaire"));
                c.setContenu(rs.getString("contenu"));
                c.setDate(rs.getDate("date").toLocalDate());
                c.setAuteur(rs.getString("auteur"));
                commentaires.add(c);
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Commentaire : " + e.getMessage());
        }

        return commentaires;
    }

    public boolean addComment(int idDeclaration, int idAuteur, String contenu) {
        String sql = "INSERT INTO commentaire (contenu, date, auteur, idDeclaration) VALUES (?, NOW(), ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, contenu);
            pst.setInt(2, idAuteur);
            pst.setInt(3, idDeclaration);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Erreur addComment : " + e.getMessage());
            return false;
        }
    }
}
