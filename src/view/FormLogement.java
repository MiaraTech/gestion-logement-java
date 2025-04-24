package view;

import controller.LogementController;
import model.Logement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FormLogement extends JFrame {
    private JTextField txtAdresse, txtType, txtSuperficie, txtLoyer, txtId;
    private JTable table;
    private DefaultTableModel model;
    private LogementController controller;

    public FormLogement() {
        controller = new LogementController();
        setTitle("Gestion des logements");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // --- Formulaire ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        txtId = new JTextField(); txtId.setEditable(false);
        txtAdresse = new JTextField();
        txtType = new JTextField();
        txtSuperficie = new JTextField();
        txtLoyer = new JTextField();

        formPanel.add(new JLabel("ID :", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Adresse :", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtAdresse);

        formPanel.add(new JLabel("Type :", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtType);

        formPanel.add(new JLabel("Superficie :", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtSuperficie);

        formPanel.add(new JLabel("Loyer :", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtLoyer);

        // --- Boutons ---
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnRetour = new JButton("Retour");

        JButton[] boutons = {btnAjouter, btnModifier, btnSupprimer, btnRetour};
        for (JButton b : boutons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(0, 123, 255));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        }

        btnRetour.setBackground(new Color(220, 53, 69));

        formPanel.add(btnAjouter);
        formPanel.add(btnModifier);
        formPanel.add(btnSupprimer);
        formPanel.add(btnRetour);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // --- Tableau ---
        model = new DefaultTableModel(new String[]{"ID", "Adresse", "Type", "Superficie", "Loyer"}, 0);
        table = new JTable(model);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Événements ---
        btnAjouter.addActionListener(e -> {
            try {
                boolean success = controller.ajouterLogement(
                        txtAdresse.getText(),
                        txtType.getText(),
                        Double.parseDouble(txtSuperficie.getText()),
                        Double.parseDouble(txtLoyer.getText())
                );
                if (success) {
                    chargerTable();
                    viderChamps();
                    JOptionPane.showMessageDialog(this, "Logement ajouté !");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erreur de format !");
            }
        });

        btnModifier.addActionListener(e -> {
            try {
                boolean success = controller.modifierLogement(
                        Integer.parseInt(txtId.getText()),
                        txtAdresse.getText(),
                        txtType.getText(),
                        Double.parseDouble(txtSuperficie.getText()),
                        Double.parseDouble(txtLoyer.getText())
                );
                if (success) {
                    chargerTable();
                    viderChamps();
                    JOptionPane.showMessageDialog(this, "Logement modifié !");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification !");
            }
        });

        btnSupprimer.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (controller.supprimerLogement(id)) {
                    chargerTable();
                    viderChamps();
                    JOptionPane.showMessageDialog(this, "Logement supprimé !");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un logement.");
            }
        });

        btnRetour.addActionListener(e -> dispose());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int ligne = table.getSelectedRow();
                txtId.setText(model.getValueAt(ligne, 0).toString());
                txtAdresse.setText(model.getValueAt(ligne, 1).toString());
                txtType.setText(model.getValueAt(ligne, 2).toString());
                txtSuperficie.setText(model.getValueAt(ligne, 3).toString());
                txtLoyer.setText(model.getValueAt(ligne, 4).toString());
            }
        });

        chargerTable();
        setContentPane(mainPanel);
        setVisible(true);
    }

    public void chargerTable() {
        model.setRowCount(0);
        List<Logement> logements = controller.getTousLesLogements();
        for (Logement l : logements) {
            model.addRow(new Object[]{l.getId(), l.getAdresse(), l.getType(), l.getSuperficie(), l.getLoyer()});
        }
    }

    public void viderChamps() {
        txtId.setText("");
        txtAdresse.setText("");
        txtType.setText("");
        txtSuperficie.setText("");
        txtLoyer.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormLogement::new);
    }
}
