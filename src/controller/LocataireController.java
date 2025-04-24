package controller;

import dao.LocataireDAO;
import java.util.List;
import model.Locataire;

public class LocataireController {
    private LocataireDAO locataireDAO;
    
    public LocataireController() {
        locataireDAO = new LocataireDAO();
    }
    
    // Ajouter  
//    
//    public boolean ajouterLocataire(String nom, String prenom, String cin, String telephone) {
//        Locataire locataire = new Locataire(
//                0, nom, prenom, cin, telephone
//        );
//        return locataireDAO.ajouter(locataire);
//    }
    
    public boolean ajouterLocataire(Locataire locataire) {
        return locataireDAO.ajouter(locataire);
    }
    
    // modifier
    public boolean modifierLocataire(Locataire locataire) {
        return locataireDAO.modifier(locataire);
    }
    
    // Supprimer
    public boolean supprimerLocataire(int id) {
        return locataireDAO.supprimer(id);
    }
    
    // Lister
    public List<Locataire> getTousLocataire() {
        return locataireDAO.lister();
    }
}
