package controller;

import dao.PaiementDAO;
import java.util.List;
import model.ContratLocation;
import model.Paiement;

public class PaiementController {
    private PaiementDAO dao = new PaiementDAO();
    private ContratLocationController contratController = new ContratLocationController();
    
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
    
    // **Nouvelle méthode** pour récupérer un ContratLocation par ID
    public ContratLocation getContratById(int idContrat) {
        return contratController.findById(idContrat);
    }
}
