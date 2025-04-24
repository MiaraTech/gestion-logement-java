package controller;

import dao.LogementDAO;
import java.util.List;
import model.Logement;

public class LogementController {

    private LogementDAO logementDAO;

    public LogementController() {
        logementDAO = new LogementDAO();
    }

    // Controller ajout nouveau logement
    public boolean ajouterLogement(String adresse, String type, double superficie, double loyer) {
        Logement logement = new Logement(
                0, adresse, type, superficie, loyer
        );
        return logementDAO.ajouter(logement);
    }
    
    // Controller lister les données du table logement
    public List<Logement> getTousLesLogements() {
        return logementDAO.lister();
    }
    
    // Controller supprimer les informations d'un logement séléctionné
    public boolean supprimerLogement(int id) {
        return logementDAO.supprimer(id);
    }
    
    // Controller modifier les informations d'un logement séléctionné
    public boolean modifierLogement(int id, String adresse, String type, double superficie, double loyer) {
        Logement logement = new Logement(id, adresse, type, superficie, loyer);
        return logementDAO.modifier(logement);
    }
}
