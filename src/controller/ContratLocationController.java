package controller;

import dao.ContratLocationDAO;
import java.util.List;
import model.ContratLocation;

public class ContratLocationController {

    private ContratLocationDAO dao;

    public ContratLocationController() {
        dao = new ContratLocationDAO();
    }

    // Ajouter contrat
    public boolean ajouterContrat(ContratLocation contrat) {
        return dao.ajouter(contrat);
    }

    // Modifier
    public boolean modifierContrat(ContratLocation contrat) {
        return dao.modifier(contrat);
    }

    // Supprimer
    public boolean supprimerContrat(int id) {
        return dao.supprimer(id);
    }

    // Lister les valeurs
    public List<ContratLocation> getTousContrats() {
        return dao.lister();
    }

    public ContratLocation getContratById(int id) {
        return dao.findById(id);
    }
    
    // Méthode pour récupérer un contrat par ID
    public ContratLocation findById(int id) {
        return dao.findById(id);
    }
}
