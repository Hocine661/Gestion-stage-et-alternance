package services;

import dao.CommentaireDAO;
import dao.DeclarationDAO;
import model.Declaration;
import model.Entreprise;
import model.DeclarationView;

import java.util.List;

public class ServiceDeclaration {
    private final CommentaireDAO commentaireDAO = new CommentaireDAO();
    private final EntrepriseService entrepriseService = new EntrepriseService();
    private final DeclarationDAO declarationDAO = new DeclarationDAO();

    public boolean declareStage(Declaration declaration, Entreprise entreprise, int idUtilisateur) {

        // Créer une entreprise ou récupérer son id si elle existe déja
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
        return declarationDAO.findActiveDeclarationByEleveId(eleveId);
    }

    public List<DeclarationView> getAllDeclarations() {
        return declarationDAO.findAllForAdmin();
    }

    // Modifier le statut d’une déclaration
    public void updateStatut(int idDeclaration, String newStatus) {
        declarationDAO.updateStatut(idDeclaration, newStatus);
    }

    public boolean addComment(int idDeclaration, String comment) {
        // Récupérer l'auteur connecté automatiquement
        int idAuteur = UserSession.getCurrentUser().getIdUtilisateur();

        return commentaireDAO.addComment(idDeclaration, idAuteur, comment);
    }


}

