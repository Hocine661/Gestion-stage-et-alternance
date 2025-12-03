package dao;

public class EntrepriseDAO {

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

        } catch (Exception e) {
            System.out.println("Erreur DAO Entreprise : " + e.getMessage());
        }

        return null;
    }
}
