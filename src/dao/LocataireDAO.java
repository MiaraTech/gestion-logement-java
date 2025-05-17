package dao;

import model.Locataire;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocataireDAO {
    
    // fonction ajouter un locataire dans la base de données
    public boolean ajouter(Locataire locataire) {
        String sql = "INSERT INTO locataire (nom, prenom, cin, telephone) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, locataire.getNom());
            stmt.setString(2, locataire.getPrenom());
            stmt.setString(3, locataire.getCin());
            stmt.setString(4, locataire.getTelephone());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction modifier les informations de locataire séléctionné
    public boolean modifier(Locataire locataire) {
        String sql = "UPDATE locataire SET nom = ?, prenom = ?, cin = ?, telephone = ? WHERE id = ?";
        
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, locataire.getNom());
            stmt.setString(2, locataire.getPrenom());
            stmt.setString(3, locataire.getCin());
            stmt.setString(4, locataire.getTelephone());
            stmt.setInt(5, locataire.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction supprimer les informations de locataire séléctionné
    public boolean supprimer(int id) {
        String sql = "DELETE FROM locataire WHERE id = ?";
        
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction lister tous les locataire
    public List<Locataire> lister() {
        List<Locataire> liste = new ArrayList<>();
        String sql = "SELECT * FROM locataire";
        
        try (Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Locataire l = new Locataire ();
                l.setId(rs.getInt("id"));
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setCin(rs.getString("cin"));
                l.setTelephone(rs.getString("telephone"));
                
                liste.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    
    public Locataire findById(int id) {
        Locataire locataire = null;
        String sql = "SELECT * FROM locataire WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                locataire = new Locataire();
                locataire.setId(rs.getInt("id"));
                locataire.setNom(rs.getString("nom"));
                locataire.setPrenom(rs.getString("prenom"));
                // Autres champs...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locataire;
    }
}
