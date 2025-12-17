package model;

import java.time.LocalDate;

public class DeclarationView {

    private int idDeclaration;   // 🔥 ID ajouté ici
    private String email;
    private String entreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private String statut;

    // Nouveau constructeur avec ID
    public DeclarationView(int idDeclaration, String email, String entreprise, LocalDate dateDebut, LocalDate dateFin, String type, String statut) {
        this.idDeclaration = idDeclaration;
        this.email = email;
        this.entreprise = entreprise;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.statut = statut;
    }

    public int getIdDeclaration() {
        return idDeclaration;
    }

    public String getEmail() {
        return email;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getType() {
        return type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
