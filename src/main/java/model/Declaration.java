package model;

import java.sql.Date;
import java.time.LocalDate;

public class Declaration {
    private int idDeclaration;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private String mission;
    private String statut;
    private int idUtilisateur;
    private int idEntreprise;

    public Declaration() {

    }

    public int getIdDeclaration() {
        return idDeclaration;
    }

    public void setIdDeclaration(int idDeclaration) {
        this.idDeclaration = idDeclaration;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }
}
