package model;

import java.time.LocalDate;

public class Entretien {
    private int id;
    private int idLogement;
    private LocalDate dateEntretien;
    private String description;

    public Entretien () {};
    
    public Entretien(int id, int idLogement, LocalDate dateEntretien, String description) {
        this.id = id;
        this.idLogement = idLogement;
        this.dateEntretien = dateEntretien;
        this.description = description;
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

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDate dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
