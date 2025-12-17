package services;

import dao.DocumentDAO;
import model.Declaration;
import model.Document;

import java.util.List;


public class DocumentService {

    private final DocumentDAO documentDAO = new DocumentDAO();

    // Récupere la liste des documents pour une déclaration
    public List<Document> findDocumentsByDeclarationId(int idDeclaration) {
        return documentDAO.findByDeclaration(idDeclaration);
    }

    //  Insere un nouveau document en BDD
    public boolean insertDocument(Document document) {
        return documentDAO.insert(document);
    }

}
