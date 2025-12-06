package dao;

import model.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("idUtilisateur"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMotdepasse(rs.getString("mot_de_passe"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Utilisateur : " + e.getMessage());
        }

        return null;
    }

    public boolean insert(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, utilisateur.getNom());
            pst.setString(2, utilisateur.getPrenom());
            pst.setString(3, utilisateur.getEmail());
            pst.setString(4, utilisateur.getMotdepasse());
            pst.setString(5, utilisateur.getRole());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'insertion a réussi

        } catch (SQLException e) {
            System.err.println("Erreur DAO Utilisateur (Insert) : " + e.getMessage());
            return false;
        }
    }
}
