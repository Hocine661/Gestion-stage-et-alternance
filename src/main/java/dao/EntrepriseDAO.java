package dao;

import model.Entreprise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDAO {

    // Récupérer une entreprise par ID
    public Entreprise findById(int idEntreprise) {
        String sql = "SELECT * FROM entreprise WHERE idEntreprise = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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

        } catch (SQLException e) {
            System.err.println("Erreur DAO Entreprise (findById) : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Récupérer une entreprise par nom et contact
    public Entreprise findByNameAndContact(String nom, String contact) {
        String sql = "SELECT * FROM entreprise WHERE nom = ? AND contact = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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

        } catch (SQLException e) {
            System.err.println("Erreur DAO Entreprise (findByNameAndContact) : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Insérer une entreprise
    public int insert(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise (nom, adresse, tuteur, contact) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getAdresse());
            pst.setString(3, entreprise.getTuteur());
            pst.setString(4, entreprise.getContact());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur DAO Entreprise (insert) : " + e.getMessage());
            e.printStackTrace();
        }

        return generatedId;
    }

    // Récupérer toutes les entreprises
    public List<Entreprise> findAll() {
        List<Entreprise> entreprises = new ArrayList<>();
        String sql = "SELECT * FROM entreprise";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entreprise e = new Entreprise();
                e.setIdEntreprise(rs.getInt("idEntreprise"));
                e.setNom(rs.getString("nom"));
                e.setAdresse(rs.getString("adresse"));
                e.setTuteur(rs.getString("tuteur"));
                e.setContact(rs.getString("contact"));

                System.out.println("DEBUG : Entreprise trouvée -> " + rs.getString("nom"));

                entreprises.add(e);
            }
        } catch (SQLException e)  {
            System.err.println("Erreur DAO Entreprise (findAll) : " + e.getMessage());
            e.printStackTrace();
        }

        return entreprises;
    }

    public List<Entreprise> findAllUsedByDeclarations() {
        List<Entreprise> entreprises = new ArrayList<>();

        String sql = "SELECT DISTINCT e.* FROM entreprise e "
                + "JOIN declaration d ON e.idEntreprise = d.idEntreprise "
                + "ORDER BY e.nom ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Entreprise e = new Entreprise();
                e.setIdEntreprise(rs.getInt("idEntreprise"));
                e.setNom(rs.getString("nom"));
                e.setAdresse(rs.getString("adresse"));
                e.setTuteur(rs.getString("tuteur"));
                e.setContact(rs.getString("contact"));

                entreprises.add(e);
            }

        } catch (SQLException e) {
            System.err.println("Erreur DAO Entreprise (findAllUsedByDeclarations) : " + e.getMessage());
        }
        return entreprises;
    }
}
