package model;

import java.time.LocalDate;

public class Commentaire {
    private int idCommentaire;
    private String contenu;
    private LocalDate date;
    private String auteur;

    public Commentaire() {
        this.idCommentaire = idCommentaire;
        this.contenu = contenu;
        this.date = date;
        this.auteur = auteur;
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
