package dao;

public class DeclarationDAO {

    public Declaration findById(int idDeclaration) {
        String sql = "SELECT * FROM declaration WHERE idDeclaration = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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
}

