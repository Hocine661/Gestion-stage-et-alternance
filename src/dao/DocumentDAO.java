package dao;

public class DocumentDAO {

    public List<Document> findByDeclaration(int idDeclaration) {
        List<Document> docs = new ArrayList<>();

        String sql = "SELECT * FROM document WHERE idDeclaration = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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
}

