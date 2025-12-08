package services;

import dao.DocumentDAO;
import model.Declaration;
import model.Document;

import java.util.List;

// Ce service agit comme la couche métier entre le contrôleur et le DAO
public class DocumentService {

    private final DocumentDAO documentDAO = new DocumentDAO();

    // 1. READ: Récupère la liste des documents pour une déclaration
    public List<Document> findDocumentsByDeclarationId(int idDeclaration) {
        return documentDAO.findByDeclaration(idDeclaration);
    }

    // 2. CREATE: Insère un nouveau document en BDD (à implémenter dans DocumentDAO)
    public boolean insertDocument(Document document) {
        // TODO: Implémenter la méthode insert dans DocumentDAO
        return documentDAO.insert(document);
    }

}
