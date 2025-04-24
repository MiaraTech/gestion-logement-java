package model;

import java.time.LocalDate;

public class Paiement {
    private int id;
    private int idContrat;
    private LocalDate datePaiement;
    private double montant;

    
    public Paiement() {};
    
    public Paiement(int id, int idContrat, LocalDate datePaiement, double montant) {
        this.id = id;
        this.idContrat = idContrat;
        this.datePaiement = datePaiement;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    @Override
    public String toString() {
        return "Paiement du " + datePaiement + " - " + montant + " Ar";
    }
}
