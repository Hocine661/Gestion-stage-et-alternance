package services;

import dao.EntrepriseDAO;
import model.Entreprise;

public class EntrepriseService {

    private final EntrepriseDAO entrepriseDAO = new EntrepriseDAO();
    public int getOrCreateEntrepriseId(Entreprise entreprise) {

        Entreprise existing = entrepriseDAO.findByNameAndContact(entreprise.getNom(), entreprise.getContact());

        if (existing != null) {
            return existing.getIdEntreprise();
        }

        int generatedId = entrepriseDAO.insert(entreprise);

        return generatedId;
    }
    public Entreprise getEntrepriseById(int idEntreprise) {
        return entrepriseDAO.findById(idEntreprise);
    }
}