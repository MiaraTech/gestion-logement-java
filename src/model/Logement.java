package model;

public class Logement {
    private int id;
    private String adresse;
    private String type;
    private double superficie;
    private double loyer;
    
    public Logement(){}
    
    public Logement(int id, String adresse, String type, double superficie, double loyer) {
        this.id = id;
        this.adresse = adresse;
        this.type = type;
        this.superficie = superficie;
        this.loyer = loyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getLoyer() {
        return loyer;
    }

    public void setLoyer(double loyer) {
        this.loyer = loyer;
    }
    
}
