package dao;

import model.Entreprise;

import java.sql.*;

public class EntrepriseDAO {

    public Entreprise findById(int idEntreprise) {
        String sql = "SELECT * FROM entreprise WHERE idEntreprise = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idEntreprise);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Entreprise e = new Entreprise();
                e.setIdEntreprise(rs.getInt("idEntreprise"));
                e.setNom(rs.getString("nom"));
                e.setAdresse(rs.getString("adresse"));
                e.setTuteur(rs.getString("tuteur"));
                e.setContact(rs.getString("contact"));
                return e;
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Entreprise : " + e.getMessage());
        }

        return null;
    }


    public Entreprise findByNameAndContact(String nom, String contact) {
        String sql = "SELECT * FROM entreprise WHERE nom = ? and contact = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nom);
            pst.setString(2, contact);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Entreprise e = new Entreprise();
                e.setIdEntreprise(rs.getInt("idEntreprise"));
                e.setNom(rs.getString("nom"));
                e.setAdresse(rs.getString("adresse"));
                e.setTuteur(rs.getString("tuteur"));
                e.setContact(rs.getString("contact"));
                return e;
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Entreprise : " + e.getMessage());
        }

        return null;
    }

    public int insert(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise (nom, adresse, tuteur, contact) VALUES (?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getAdresse());
            pst.setString(3, entreprise.getTuteur());
            pst.setString(4, entreprise.getContact());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Récupérer le résultat contenant l'ID généré
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1); // Récupère l'ID dans la première colonne
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur DAO Utilisateur (Insert) : " + e.getMessage());
        }

        return generatedId;
    }
}

