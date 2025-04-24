package view;

import controller.ContratLocationController;
import controller.LocataireController;
import controller.LogementController;
import model.ContratLocation;
import model.Locataire;
import model.Logement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class FormContratLocation extends JFrame {
    private JComboBox<Logement> logementCombo;
    private JComboBox<Locataire> locataireCombo;
    private JTextField txtDateDebut;
    private JTextField txtDateFin;
    private JTextField txtLoyerMensuel;

    private JTable table;
    private DefaultTableModel model;

    private ContratLocationController controller = new ContratLocationController();

    public FormContratLocation() {
        setTitle("Gestion des Contrats de Location");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        initUI(mainPanel);
        chargerContrats();

        add(mainPanel);
        setVisible(true);
    }

    private void initUI(JPanel container) {
    JPanel panelForm = new JPanel(new GridBagLayout());
    panelForm.setBackground(Color.WHITE);
    panelForm.setBorder(BorderFactory.createTitledBorder("Nouveau Contrat"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    logementCombo = new JComboBox<>();
    locataireCombo = new JComboBox<>();
    txtDateDebut = new JTextField();
    txtDateFin = new JTextField();
    txtLoyerMensuel = new JTextField();

    remplirLogements();
    remplirLocataires();

    int y = 0;

    // Ligne 1 - Logement
    gbc.gridx = 0; gbc.gridy = y;
    panelForm.add(new JLabel("Logement:"), gbc);
    gbc.gridx = 1;
    panelForm.add(logementCombo, gbc);

    // Ligne 2 - Locataire
    y++;
    gbc.gridx = 0; gbc.gridy = y;
    panelForm.add(new JLabel("Locataire:"), gbc);
    gbc.gridx = 1;
    panelForm.add(locataireCombo, gbc);

    // Ligne 3 - Date début
    y++;
    gbc.gridx = 0; gbc.gridy = y;
    panelForm.add(new JLabel("Date début (yyyy-mm-dd):"), gbc);
    gbc.gridx = 1;
    panelForm.add(txtDateDebut, gbc);

    // Ligne 4 - Date fin
    y++;
    gbc.gridx = 0; gbc.gridy = y;
    panelForm.add(new JLabel("Date fin (yyyy-mm-dd):"), gbc);
    gbc.gridx = 1;
    panelForm.add(txtDateFin, gbc);

    // Ligne 5 - Loyer mensuel
    y++;
    gbc.gridx = 0; gbc.gridy = y;
    panelForm.add(new JLabel("Loyer mensuel:"), gbc);
    gbc.gridx = 1;
    panelForm.add(txtLoyerMensuel, gbc);

    // Ligne 6 - Boutons
    y++;
    gbc.gridy = y; gbc.gridx = 0;
    JButton btnEnregistrer = createStyledButton("ENREGISTRER", new Color(40, 167, 69));
    panelForm.add(btnEnregistrer, gbc);

    gbc.gridx = 1;
    JButton btnSupprimer = createStyledButton("SUPPRIMER", new Color(220, 53, 69));
    panelForm.add(btnSupprimer, gbc);

    // Ligne 7 - Bouton retour
    y++;
    gbc.gridy = y; gbc.gridx = 1;
    JButton btnRetour = createStyledButton("RETOUR", new Color(108, 117, 125));
    panelForm.add(btnRetour, gbc);

    // Actions
    btnEnregistrer.addActionListener(e -> ajouterContrat());
    btnSupprimer.addActionListener(e -> supprimerContrat());
    btnRetour.addActionListener(e -> dispose());

    model = new DefaultTableModel(new String[]{"ID", "Logement", "Locataire", "Début", "Fin", "Loyer"}, 0);
    table = new JTable(model);
    table.setRowHeight(24);

    container.add(panelForm, BorderLayout.NORTH);
    container.add(new JScrollPane(table), BorderLayout.CENTER);
}

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(130, 35));
        return button;
    }

    private void remplirLogements() {
        logementCombo.removeAllItems();
        LogementController lc = new LogementController();
        for (Logement l : lc.getTousLesLogements()) {
            logementCombo.addItem(l);
        }
    }

    private void remplirLocataires() {
        locataireCombo.removeAllItems();
        LocataireController lc = new LocataireController();
        for (Locataire l : lc.getTousLocataire()) {
            locataireCombo.addItem(l);
        }
    }

    private void ajouterContrat() {
        try {
            if (txtDateDebut.getText().isEmpty() || txtDateFin.getText().isEmpty() || txtLoyerMensuel.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis !");
                return;
            }

            ContratLocation contrat = new ContratLocation();
            contrat.setIdLogement(((Logement) logementCombo.getSelectedItem()).getId());
            contrat.setIdLocataire(((Locataire) locataireCombo.getSelectedItem()).getId());
            contrat.setDateDebut(LocalDate.parse(txtDateDebut.getText()));
            contrat.setDateFin(LocalDate.parse(txtDateFin.getText()));
            contrat.setLoyerMensuel(Double.parseDouble(txtLoyerMensuel.getText()));

            if (controller.ajouterContrat(contrat)) {
                JOptionPane.showMessageDialog(this, "Contrat ajouté !");
                chargerContrats();
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void supprimerContrat() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.supprimerContrat(id)) {
                    JOptionPane.showMessageDialog(this, "Contrat supprimé !");
                    chargerContrats();
                    viderChamps();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur de suppression !");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un contrat !");
        }
    }

    private void chargerContrats() {
        model.setRowCount(0);
        for (ContratLocation c : controller.getTousContrats()) {
            model.addRow(new Object[]{
                    c.getId(),
                    "Logement #" + c.getIdLogement(),
                    "Locataire #" + c.getIdLocataire(),
                    c.getDateDebut(),
                    c.getDateFin(),
                    c.getLoyerMensuel()
            });
        }
    }

    private void viderChamps() {
        txtDateDebut.setText("");
        txtDateFin.setText("");
        txtLoyerMensuel.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormContratLocation::new);
    }
}
