package services;

import dao.DeclarationDAO;
import model.Declaration;
import model.Entreprise;

public class ServiceDeclaration {

    private final EntrepriseService entrepriseService = new EntrepriseService();
    private final DeclarationDAO declarationDAO = new DeclarationDAO();
    public boolean declareStage(Declaration declaration, Entreprise entreprise, int idUtilisateur) {

        // Créer une entreprise ou récupérer son id, si elle existe déja
        int idEntreprise = entrepriseService.getOrCreateEntrepriseId(entreprise);

        if (idEntreprise == -1) {
            System.err.println("Erreur Service: Impossible d'obtenir un ID d'entreprise.");
            return false;
        }

        // Assigner les clés étrangères à l'objet Déclaration
        declaration.setIdEntreprise(idEntreprise);
        declaration.setIdUtilisateur(idUtilisateur);
        return declarationDAO.insert(declaration);
    }

    public Declaration findActiveDeclarationByEleveId(int eleveId) {
        // ASSUREZ-VOUS que cette méthode appelle la méthode du DAO qui cherche par idUtilisateur
        return declarationDAO.findActiveDeclarationByEleveId(eleveId);
    }
}
