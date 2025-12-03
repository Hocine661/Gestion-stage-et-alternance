package model;

import java.sql.Date;
import java.time.LocalDate;

public class Declaration {
    private int idDeclaration;
    private Date DateDebut;
    private Date DateFin;
    private String type;
    private String mission;
    private String statut;
    private int idUtilisateur;
    private int idEntreprise;

    public Declaration() {
        this.idDeclaration = idDeclaration;
        DateDebut = dateDebut;
        DateFin = dateFin;
        this.type = type;
        this.mission = mission;
        this.statut = statut;
        this.idUtilisateur = idUtilisateur;
        this.idEntreprise = idEntreprise;
    }

    public int getIdDeclaration() {
        return idDeclaration;
    }

    public void setIdDeclaration(int idDeclaration) {
        this.idDeclaration = idDeclaration;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        DateDebut = dateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        DateFin = dateFin;
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
