package view;

import dao.DashboardDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DashboardView extends JFrame {
    private JLabel totalLogementsLabel;
    private JLabel totalLocatairesLabel;
    private JLabel paiementsMoisLabel;
    private JLabel logementsVacantsLabel;

    private DashboardDAO dashboardDAO;

    public DashboardView() {
        setTitle("Tableau de Bord");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        dashboardDAO = new DashboardDAO();

        initUI();
        loadDashboardData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistiques Globales"));
        statsPanel.setBackground(Color.WHITE);

        totalLogementsLabel = createStyledLabel("Total logements : ...");
        totalLocatairesLabel = createStyledLabel("Total locataires : ...");
        paiementsMoisLabel = createStyledLabel("Paiements ce mois : ...");
        logementsVacantsLabel = createStyledLabel("Logements vacants : ...");

        statsPanel.add(totalLogementsLabel);
        statsPanel.add(totalLocatairesLabel);
        statsPanel.add(paiementsMoisLabel);
        statsPanel.add(logementsVacantsLabel);

        // Charts Panel
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        chartsPanel.setBorder(BorderFactory.createTitledBorder("Graphiques"));
        chartsPanel.setBackground(Color.WHITE);
        chartsPanel.add(createPieChartPanel());
        chartsPanel.add(createBarChartPanel());

        // Final Layout
        add(statsPanel, BorderLayout.NORTH);
        add(chartsPanel, BorderLayout.CENTER);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(new Color(230, 240, 255));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        return label;
    }

    private void loadDashboardData() {
        totalLogementsLabel.setText("Total logements : " + dashboardDAO.getTotalLogements());
        totalLocatairesLabel.setText("Total locataires : " + dashboardDAO.getTotalLocataires());
        paiementsMoisLabel.setText("Paiements ce mois : " + dashboardDAO.getPaiementsCeMois() + " €");
        logementsVacantsLabel.setText("Logements vacants : " + dashboardDAO.getLogementsVacants());
    }

    private ChartPanel createPieChartPanel() {
    int occupes = dashboardDAO.getLogementsOccupes();
    int vacants = dashboardDAO.getLogementsVacants();

    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Occupés", occupes);
    dataset.setValue("Vacants", vacants);

    JFreeChart chart = ChartFactory.createPieChart(
            "Répartition des Logements",
            dataset, true, true, false
    );

    return new ChartPanel(chart);
}

    private ChartPanel createBarChartPanel() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    Map<String, Double> paiements = dashboardDAO.getPaiements6DerniersMois();
    for (Map.Entry<String, Double> entry : paiements.entrySet()) {
        dataset.addValue(entry.getValue(), "Montant (€)", entry.getKey());
    }

    JFreeChart chart = ChartFactory.createBarChart(
            "Paiements des 6 Derniers Mois",
            "Mois", "Montant (€)",
            dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL,
            true, true, false
    );

    return new ChartPanel(chart);
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new view.DashboardView().setVisible(true));
    }
}
