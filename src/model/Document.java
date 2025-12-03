package model;

import java.time.LocalDate;
import java.util.Date;

public class Document {
    private int IdDocument;
    private String type;
    private String cheminFichier;
    private Date DateDepot;

    public Document() {
        IdDocument = IdDocument;
        this.type = type;
        this.cheminFichier = cheminFichier;
        DateDepot = dateDepot;
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

    public Date getDateDepot() {
        return DateDepot;
    }

    public void setDateDepot(Date dateDepot) {
        DateDepot = dateDepot;
    }

    public void setDateDepot(LocalDate dateDepot) {

    }
}
