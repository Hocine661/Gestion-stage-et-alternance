package dao;

import model.Declaration;

import java.sql.*;

public class DeclarationDAO {

    public Declaration findById(int idDeclaration) {
        String sql = "SELECT * FROM declaration WHERE idDeclaration = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idDeclaration);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Declaration d = new Declaration();
                d.setIdDeclaration(rs.getInt("idDeclaration"));
                d.setDateDebut(rs.getDate("date_debut").toLocalDate());
                d.setDateFin(rs.getDate("date_fin").toLocalDate());
                d.setType(rs.getString("type"));
                d.setMission(rs.getString("mission"));
                d.setStatut(rs.getString("statut"));
                d.setIdUtilisateur(rs.getInt("idUtilisateur"));
                d.setIdEntreprise(rs.getInt("idEntreprise"));
                return d;
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Declaration : " + e.getMessage());
        }

        return null;
    }

    public boolean insert(Declaration declaration) {
        String sql = "INSERT INTO declaration (date_debut, date_fin, type, mission, statut, idUtilisateur, idEntreprise)" + " VALUES (?, ?, ?, ?, 'en attente', ?, ?)";
        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(declaration.getDateDebut()));
            pst.setDate(2, Date.valueOf(declaration.getDateFin()));
            pst.setString(3, declaration.getType());
            pst.setString(4, declaration.getMission());
            pst.setInt(5, declaration.getIdUtilisateur());
            pst.setInt(6, declaration.getIdEntreprise());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'insertion a réussi

        } catch (SQLException e) {
            System.err.println("Erreur DAO declaration (Insert) : " + e.getMessage());
            return false;
        }
    }

    public Declaration findActiveDeclarationByEleveId(int idUtilisateur) {
        // Requête pour sélectionner la dernière déclaration insérée pour cet utilisateur.
        String sql = "SELECT * FROM declaration WHERE idUtilisateur = ? ORDER BY idDeclaration DESC LIMIT 1";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idUtilisateur);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Declaration d = new Declaration();
                // Mappage de TOUS les champs (comme dans findById)
                d.setIdDeclaration(rs.getInt("idDeclaration"));
                d.setDateDebut(rs.getDate("date_debut").toLocalDate());
                d.setDateFin(rs.getDate("date_fin").toLocalDate());
                d.setType(rs.getString("type"));
                d.setMission(rs.getString("mission"));
                d.setStatut(rs.getString("statut"));
                d.setIdUtilisateur(rs.getInt("idUtilisateur"));
                d.setIdEntreprise(rs.getInt("idEntreprise"));
                return d;
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Declaration (findActiveDeclarationByEleveId) : " + e.getMessage());
        }

        return null;
    }
}

