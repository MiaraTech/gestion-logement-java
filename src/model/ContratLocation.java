package model;

import java.time.LocalDate;

public class ContratLocation {
    private int id;
    private int idLogement;
    private int idLocataire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double loyerMensuel;
    
    public ContratLocation () {};

    public ContratLocation(int id, int idLogement, int idLocataire, LocalDate dateDebut, LocalDate dateFin, double loyerMensuel) {
        this.id = id;
        this.idLogement = idLogement;
        this.idLocataire = idLocataire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.loyerMensuel = loyerMensuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLogement() {
        return idLogement;
    }

    public void setIdLogement(int idLogement) {
        this.idLogement = idLogement;
    }

    public int getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(int idLocataire) {
        this.idLocataire = idLocataire;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public double getLoyerMensuel() {
        return loyerMensuel;
    }

    public void setLoyerMensuel(double loyerMensuel) {
        this.loyerMensuel = loyerMensuel;
    }
    
    
}
