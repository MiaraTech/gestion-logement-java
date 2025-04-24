package dao;

import model.Logement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogementDAO {

    private Connection conn;

    public LogementDAO() {
        conn = Connexion.getConnection();
    }

    // fonction ajout nouveau Logement dans la base de données
    public boolean ajouter(Logement logement) {
        String sql = "INSERT INTO logement(adresse, type, superficie, loyer) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, logement.getAdresse());
            stmt.setString(2, logement.getType());
            stmt.setDouble(3, logement.getSuperficie());
            stmt.setDouble(4, logement.getLoyer());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // fonction lister les données de la table Logement
    public List<Logement> lister() {
        List<Logement> liste = new ArrayList<>();
        String sql = "SELECT * FROM logement";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Logement l = new Logement(rs.getInt("id"), rs.getString("adresse"), rs.getString("type"),
                        rs.getDouble("superficie"), rs.getDouble("loyer"));
                liste.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    
    // fonction supprimer les informations de logement enregistrer
    public boolean supprimer(int id) {
        String sql = "DELETE FROM logement WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction modifier les informations de logement séléctionner
    public boolean modifier(Logement logement) {
        String sql = "UPDATE logement SET adresse = ?, type = ?, superficie = ?, loyer = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, logement.getAdresse());
            stmt.setString(2, logement.getType());
            stmt.setDouble(3, logement.getSuperficie());
            stmt.setDouble(4, logement.getLoyer());
            stmt.setInt(5, logement.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
