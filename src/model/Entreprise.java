package model;

public class Entreprise {
    private int idEntreprise;
    private String nom;
    private String adresse;
    private String tuteur;
    private String contact;

    public Entreprise() {
        this.idEntreprise = idEntreprise;
        nom = nom;
        adresse = adresse;
        tuteur = tuteur;
        contact = contact;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTuteur() {
        return tuteur;
    }

    public void setTuteur(String tuteur) {
        this.tuteur = tuteur;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
