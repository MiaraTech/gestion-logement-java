package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.ContratLocation;
import java.sql.*;
import model.Locataire;
import model.Logement;

public class ContratLocationDAO {

    // fonction ajouter nouvel contrat
    public boolean ajouter(ContratLocation contrat) {
        String sql = "INSERT INTO contrat_location(id_logement, id_locataire, date_debut, date_fin, loyer_mensuel) VALUES (?, "
                + "?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contrat.getIdLogement());
            stmt.setInt(2, contrat.getIdLocataire());
            stmt.setDate(3, java.sql.Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(4, java.sql.Date.valueOf(contrat.getDateFin()));
            stmt.setDouble(5, contrat.getLoyerMensuel());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // fonction modifier le contrat
    public boolean modifier(ContratLocation contrat) {
        String sql = "UPDATE contrat_location SET id_logement = ?, id_locataire = ?, date_debut = ?,"
                + "date_fin = ?, loyer_mensuel = ? WHERE id = ?";

        try (Connection conn = Connexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contrat.getIdLogement());
            stmt.setInt(2, contrat.getIdLocataire());
            stmt.setDate(3, java.sql.Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(4, java.sql.Date.valueOf(contrat.getDateFin()));
            stmt.setDouble(5, contrat.getLoyerMensuel());
            stmt.setInt(6, contrat.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // fonction supprimer le contrat
    public boolean supprimer(int id) {
        String sql = "DELETE FROM contrat_location WHERE id = ?";
        try (Connection conn = Connexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // fonction lister les données de contrat location dans la base de données
    public List<ContratLocation> lister() {
        List<ContratLocation> liste = new ArrayList<>();
        String sql = "SELECT * FROM contrat_location";
        try (Connection conn = Connexion.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ContratLocation contrat = new ContratLocation();
                contrat.setId(rs.getInt("id"));
                contrat.setIdLogement(rs.getInt("id_logement"));
                contrat.setIdLocataire(rs.getInt("id_locataire"));
                contrat.setDateDebut(rs.getDate("date_debut").toLocalDate());
                contrat.setDateFin(rs.getDate("date_fin").toLocalDate());
                contrat.setLoyerMensuel(rs.getDouble("loyer_mensuel"));

                liste.add(contrat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ContratLocation findById(int id) {
        ContratLocation contrat = null;
        String sql = "SELECT * FROM contrat_location WHERE id = ?";
        try (Connection conn = Connexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contrat = new ContratLocation();
                contrat.setId(rs.getInt("id"));
                // Récupérer les autres champs du contrat ici...

                // Supposons que tu as les IDs du locataire et du logement dans cette table
                int locataireId = rs.getInt("id_locataire");
                int logementId = rs.getInt("id_logement");

                // Récupérer les objets associés Locataire et Logement
                LocataireDAO locataireDAO = new LocataireDAO();
                LogementDAO logementDAO = new LogementDAO();

                Locataire locataire = locataireDAO.findById(locataireId);
                Logement logement = logementDAO.findById(logementId);

                contrat.setLocataire(locataire);
                contrat.setLogement(logement);

                // autres champs ...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrat;
    }
}
