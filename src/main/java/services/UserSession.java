package services;

import model.Utilisateur;

public class UserSession {

    private static Utilisateur utilisateurConnecte;

    public static void login(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getCurrentUser() {
        return utilisateurConnecte;
    }

    public static void logout() {
        utilisateurConnecte = null;
    }

    public static String getPrenom() {
        return utilisateurConnecte.getPrenom();
    }


}
