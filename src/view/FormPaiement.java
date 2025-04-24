package view;

import controller.ContratLocationController;
import controller.PaiementController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.ContratLocation;
import model.Paiement;

public class FormPaiement extends JFrame {

    private JComboBox<ContratLocation> comboContrat;  // Modifier la déclaration pour accepter les objets ContratLocation
    private JTextField txtDatePaiement;
    private JTextField txtMontant;
    private JTable table;
    private DefaultTableModel model;
    private PaiementController controller;

    public FormPaiement() {
        controller = new PaiementController();
        setTitle("Gestion des Paiements");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        afficherPaiements();
        remplirContrats();  // Charger les contrats dans la JComboBox
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Paiement"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        comboContrat = new JComboBox<>();
        txtDatePaiement = new JTextField(LocalDate.now().toString());
        txtMontant = new JTextField();

        // Adding the labels and fields to the panel using GridBagLayout
        int y = 0;

        // Ligne 1 - Contrat
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Contrat : "), gbc);
        gbc.gridx = 1;
        panel.add(comboContrat, gbc);

        // Ligne 2 - Date paiement
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Date paiement (yyyy-mm-dd) : "), gbc);
        gbc.gridx = 1;
        panel.add(txtDatePaiement, gbc);

        // Ligne 3 - Montant
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Montant : "), gbc);
        gbc.gridx = 1;
        panel.add(txtMontant, gbc);

        // Ligne 4 - Boutons
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton btnEnregistrer = createStyledButton("ENREGISTRER", new Color(40, 167, 69));
        panel.add(btnEnregistrer, gbc);

        gbc.gridx = 1;
        JButton btnSupprimer = createStyledButton("SUPPRIMER", new Color(220, 53, 69));
        panel.add(btnSupprimer, gbc);

        // Ligne 5 - Bouton retour
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton btnRetour = createStyledButton("RETOUR", new Color(108, 117, 125));
        panel.add(btnRetour, gbc);

        // Actions
        btnEnregistrer.addActionListener(e -> ajouterPaiement());
        btnSupprimer.addActionListener(e -> supprimerPaiement());
        btnRetour.addActionListener(e -> dispose());

        model = new DefaultTableModel(new String[]{"ID", "Contrat", "Date", "Montant"}, 0);
        table = new JTable(model);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    // Fonction bouton ajouter
    private void ajouterPaiement() {
        try {
            Paiement p = new Paiement();
            ContratLocation contrat = (ContratLocation) comboContrat.getSelectedItem();  // Récupérer l'objet ContratLocation
            p.setIdContrat(contrat.getId());  // Utiliser l'ID du contrat sélectionné
            p.setDatePaiement(LocalDate.parse(txtDatePaiement.getText()));
            p.setMontant(Double.parseDouble(txtMontant.getText()));

            if (controller.ajouterPaiement(p)) {
                JOptionPane.showMessageDialog(this, "Paiement enregistré !");
                afficherPaiements();
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur enregistrement !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Données invalides !");
        }
    }

    // Fonction supprimer Paiement
    private void supprimerPaiement() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            if (controller.supprimerPaiement(id)) {
                JOptionPane.showMessageDialog(this, "Paiement Supprimé !");
                afficherPaiements();
                viderChamps();
            }
        }
    }

    // Fonction vider les champs 
    public void viderChamps() {
        txtDatePaiement.setText("");
        txtMontant.setText("");
    }

    // Fonction afficher les données Paiements
    private void afficherPaiements() {
        model.setRowCount(0);
        for (Paiement p : controller.getTousPaiements()) {
            model.addRow(new Object[]{
                p.getId(), p.getIdContrat(), p.getDatePaiement(), p.getMontant()
            });
        }
    }

    // Remplir la JComboBox avec les contrats existants
    private void remplirContrats() {
        comboContrat.removeAllItems();
        ContratLocationController lc = new ContratLocationController();
        for (ContratLocation cl : lc.getTousContrats()) {
            comboContrat.addItem(cl);  // Ajouter chaque contrat dans la JComboBox
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPaiement().setVisible(true));
    }
}
