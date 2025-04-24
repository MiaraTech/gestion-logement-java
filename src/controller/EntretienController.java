package controller;

import dao.EntretienDAO;
import java.util.List;
import model.Entretien;

public class EntretienController {
    private EntretienDAO dao = new EntretienDAO();
    
    // Ajouter
    public boolean ajouterEntretien(Entretien e) {
        return dao.ajouter(e);
    }
    
    // Supprimer
    public boolean supprimerEntretien(int id) {
        return dao.supprimer(id);
    }
    
    // Lister
    public List<Entretien> getTousEntretiens() {
        return dao.lister();
    }
}
