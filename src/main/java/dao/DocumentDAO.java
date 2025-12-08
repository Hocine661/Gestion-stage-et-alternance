package dao;

import model.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public List<Document> findByDeclaration(int idDeclaration) {
        List<Document> docs = new ArrayList<>();

        String sql = "SELECT * FROM document WHERE idDeclaration = ?";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idDeclaration);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Document doc = new Document();
                doc.setIdDocument(rs.getInt("idDocument"));
                doc.setType(rs.getString("type"));
                doc.setCheminFichier(rs.getString("chemin_fichier"));
                doc.setDateDepot(rs.getDate("date_depot").toLocalDate());
                docs.add(doc);
            }

        } catch (Exception e) {
            System.out.println("Erreur DAO Document : " + e.getMessage());
        }

        return docs;
    }


    public boolean insert(Document document) {
        // La requête utilise la fonction NOW() de MySQL pour stocker la date/heure actuelle du dépôt
        String sql = "INSERT INTO document (type, chemin_fichier, date_depot, idDeclaration) VALUES (?, ?, NOW(), ?)";

        try (Connection conn = dao.DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, document.getType());
            pst.setString(2, document.getCheminFichier());
            pst.setInt(3, document.getIdDeclaration());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur DAO Document (Insert) : " + e.getMessage());
            return false;
        }
    }
}

