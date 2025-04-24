package dao;

import model.Paiement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO {
    
    // fonction ajouter nouvel paiement
    public boolean ajouter(Paiement paiement){
        String sql = "INSERT INTO paiement(id_contrat, date_paiement, montant) VALUES (?, ?, ?)";
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paiement.getIdContrat());
            stmt.setDate(2, java.sql.Date.valueOf(paiement.getDatePaiement()));
            stmt.setDouble(3, paiement.getMontant());
            
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction supprimer paiement 
    public boolean supprimer(int id) {
        String sql = "DELETE FROM paiement WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // fonction lister tous les données de paiement
    public List<Paiement> lister() {
        List<Paiement> liste = new ArrayList<>();
        String sql = "SELECT * FROM paiement";
        try (Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Paiement paiement = new Paiement();
                paiement.setId(rs.getInt("id"));
                paiement.setIdContrat(rs.getInt("id_contrat"));
                paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                paiement.setMontant(rs.getDouble("montant"));
                
                liste.add(paiement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
}
