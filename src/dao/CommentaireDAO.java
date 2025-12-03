package dao;

public class CommentaireDAO {

    public List<Commentaire> findByDeclaration(int idDeclaration) {
        List<Commentaire> commentaires = new ArrayList<>();

        String sql = "SELECT * FROM commentaire WHERE idDeclaration = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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
}
