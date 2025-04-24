package dao;

import java.sql.*;
import model.Utilisateur;
public class UtilisateurDAO {
    private Connection conn;
    
    public UtilisateurDAO (Connection conn) {
        this.conn = conn;
    }
    
    public boolean verifierConnexion(String nomUtilisateur, String motDePasse) {
        try {
            String sql = "SELECT * FROM utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomUtilisateur);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean ajouterUtilisateur(Utilisateur u) {
        try {
            String sql = "INSERT INTO utilisateur (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getNomUtilisateur());
            stmt.setString(2, u.getMotDePasse());
            stmt.setString(3, u.getRole());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
