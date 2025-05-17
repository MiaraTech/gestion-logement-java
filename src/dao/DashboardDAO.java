package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardDAO {

    private Connection connection;

    public DashboardDAO() {
        connection = Connexion.getConnection();
    }

    public int getTotalLogements() {
        return countQuery("SELECT COUNT(*) FROM logement");
    }

    public int getTotalLocataires() {
        return countQuery("SELECT COUNT(*) FROM locataire");
    }

    public double getPaiementsCeMois() {
        double total = 0;
        try {
            String sql = "SELECT SUM(montant) FROM paiement WHERE MONTH(date_paiement) = MONTH(CURRENT_DATE()) AND YEAR(date_paiement) = YEAR(CURRENT_DATE())";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getLogementsVacants() {
        String sql = """
        SELECT COUNT(*) FROM logement l
        WHERE l.id NOT IN (
            SELECT cl.id_logement FROM contrat_location cl WHERE cl.id_locataire IS NOT NULL
        )
    """;
        return countQuery(sql);
    }

    public int getLogementsOccupes() {
        String sql = "SELECT COUNT(DISTINCT id_logement) FROM contrat_location WHERE id_locataire IS NOT NULL";
        return countQuery(sql);
    }

    public Map<String, Double> getPaiements6DerniersMois() {
        Map<String, Double> map = new LinkedHashMap<>();
        try {
            String sql = """
                SELECT DATE_FORMAT(date_paiement, '%Y-%m') as mois, SUM(montant) as total
                FROM paiement
                WHERE date_paiement >= DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH)
                GROUP BY mois
                ORDER BY mois
            """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("mois"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    private int countQuery(String sql) {
        int count = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
