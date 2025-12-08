package model;

import java.time.LocalDate;
import java.util.Date;

public class Document {
    private int IdDocument;
    private String type;
    private String cheminFichier;
    private LocalDate dateDepot;
    private int idDeclaration;

    public Document() {
        this.IdDocument = IdDocument;
        this.type = type;
        this.cheminFichier = cheminFichier;
        this.dateDepot = dateDepot;
        this.idDeclaration = idDeclaration;
    }

    public int getIdDocument() {
        return IdDocument;
    }

    public void setIdDocument(int idDocument) {
        IdDocument = idDocument;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public LocalDate getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDate dateDepot) {

        this.dateDepot = dateDepot;
    }

    public int getIdDeclaration() {
        return idDeclaration;
    }

    public void setIdDeclaration(int idDeclaration) {
        this.idDeclaration = idDeclaration;
    }

}
