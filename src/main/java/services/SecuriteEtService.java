// Cette classe servira à vrifier si le mail entré duranr l'inscription n'existe pas déja, et aussi pour la sécurité: hashage du mt de passe, Faut lire le cahier des charges

package services;

import dao.UtilisateurDAO;
import model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

public class SecuriteEtService {
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    public boolean registerUser(String nom, String prenom,String email, String motDePasseClair, String role) {

        if (utilisateurDAO.findByEmail(email) != null) {
            return false;
        }

        String salt = BCrypt.gensalt();
        String motDePasseHache = BCrypt.hashpw(motDePasseClair, salt);


        Utilisateur newUtilisateur = new Utilisateur();
        newUtilisateur.setNom(nom);
        newUtilisateur.setPrenom(prenom);
        newUtilisateur.setEmail(email);
        newUtilisateur.setMotdepasse(motDePasseHache);
        newUtilisateur.setRole(role);

        return utilisateurDAO.insert(newUtilisateur);
    }

    public Utilisateur loginUser(String email, String motDePasseClair) {
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email);
        if (utilisateur != null && BCrypt.checkpw(motDePasseClair, utilisateur.getMotdepasse())) {
            return utilisateur;
        }
        return null;
    }


}
