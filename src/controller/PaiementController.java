package controller;

import dao.PaiementDAO;
import java.util.List;
import model.Paiement;

public class PaiementController {
    private PaiementDAO dao = new PaiementDAO();
    
    // Ajouter
    public boolean ajouterPaiement (Paiement p) {
        return dao.ajouter(p);
    }
    
    // Supprimer
    public boolean supprimerPaiement (int id) {
        return dao.supprimer(id);
    }
    
    // Lister
    public List<Paiement> getTousPaiements() {
        return dao.lister();
    }
}
