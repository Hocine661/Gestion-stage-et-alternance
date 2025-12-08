package model;

import java.time.LocalDate;

public class DeclarationView {
    private String email;
    private String entreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private String statut;

    public DeclarationView(String email, String entreprise, LocalDate dateDebut, LocalDate dateFin, String type, String statut) {
        this.email = email;
        this.entreprise = entreprise;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.statut = statut;
    }

    public String getEmail() { return email; }
    public String getEntreprise() { return entreprise; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public String getType() { return type; }
    public String getStatut() { return statut; }
}
