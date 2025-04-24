package dao;

import model.Entretien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntretienDAO {
    
    // Ajout nouvel entretien dans la base de données
    public boolean ajouter(Entretien e) {
        String sql = "INSERT INTO entretien (id_logement, date_entretien, description) VALUES (?, ?, ?)";
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getIdLogement());
            stmt.setDate(2, java.sql.Date.valueOf(e.getDateEntretien()));
            stmt.setString(3, e.getDescription());
            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // fonction supprimer entretien
    public boolean supprimer(int id) {
        String sql = "DELETE FROM entretien WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // Lister les données de l'entretien
    public List<Entretien> lister() {
        List<Entretien> liste = new ArrayList<>();
        String sql = "SELECT * FROM entretien";
        try (Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Entretien e = new Entretien();
                e.setId(rs.getInt("id"));
                e.setIdLogement(rs.getInt("id_logement"));
                e.setDateEntretien(rs.getDate("date_entretien").toLocalDate());
                e.setDescription(rs.getString("description"));
                
                liste.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }
}
