package view;

import controller.LocataireController;
import model.Locataire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FormLocataire extends JFrame {

    private JTextField txtNom, txtPrenom, txtCin, txtTelephone;
    private JTable table;
    private DefaultTableModel model;
    private LocataireController controller;

    public FormLocataire() {
        controller = new LocataireController();
        setTitle("Gestion des Locataires");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 12, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Locataire"));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtCin = new JTextField();
        txtTelephone = new JTextField();

        formPanel.add(new JLabel("Nom:", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Prénom:", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtPrenom);
        formPanel.add(new JLabel("CIN:", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtCin);
        formPanel.add(new JLabel("Téléphone:", JLabel.RIGHT)).setFont(labelFont);
        formPanel.add(txtTelephone);

        // Boutons principaux
        JButton btnAjouter = createStyledButton("Ajouter", new Color(40, 167, 69));
        JButton btnModifier = createStyledButton("Modifier", new Color(255, 193, 7));
        JPanel topButtons = new JPanel();
        topButtons.setBackground(Color.WHITE);
        topButtons.add(btnAjouter);
        topButtons.add(btnModifier);

        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(Color.WHITE);
        formContainer.add(formPanel, BorderLayout.CENTER);
        formContainer.add(topButtons, BorderLayout.SOUTH);

        // Tableau
        model = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "CIN", "Téléphone"}, 0);
        table = new JTable(model);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        // Boutons secondaires
        JButton btnSupprimer = createStyledButton("Supprimer", new Color(220, 53, 69));
        JButton btnRefraichir = createStyledButton("Rafraîchir", new Color(23, 162, 184));
        JButton btnRetour = createStyledButton("Retour", new Color(108, 117, 125));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRefraichir);
        buttonPanel.add(btnRetour);

        mainPanel.add(formContainer, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        afficherLocataires();

        // Actions
        btnAjouter.addActionListener(e -> ajouterLocataires());
        btnModifier.addActionListener(e -> modifierLocataires());
        btnSupprimer.addActionListener(e -> supprimerLocataires());
        btnRefraichir.addActionListener(e -> afficherLocataires());
        btnRetour.addActionListener(e -> dispose());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtNom.setText(model.getValueAt(row, 1).toString());
                    txtPrenom.setText(model.getValueAt(row, 2).toString());
                    txtCin.setText(model.getValueAt(row, 3).toString());
                    txtTelephone.setText(model.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private void afficherLocataires() {
        model.setRowCount(0);
        List<Locataire> liste = controller.getTousLocataire();
        for (Locataire l : liste) {
            model.addRow(new Object[]{
                    l.getId(),
                    l.getNom(),
                    l.getPrenom(),
                    l.getCin(),
                    l.getTelephone()
            });
        }
    }

    private void ajouterLocataires() {
        Locataire l = new Locataire();
        l.setNom(txtNom.getText());
        l.setPrenom(txtPrenom.getText());
        l.setCin(txtCin.getText());
        l.setTelephone(txtTelephone.getText());

        if (controller.ajouterLocataire(l)) {
            JOptionPane.showMessageDialog(this, "Locataire ajouté avec succès !");
            afficherLocataires();
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout !");
        }
    }

    private void modifierLocataires() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Locataire l = new Locataire();
            l.setId((int) model.getValueAt(row, 0));
            l.setNom(txtNom.getText());
            l.setPrenom(txtPrenom.getText());
            l.setCin(txtCin.getText());
            l.setTelephone(txtTelephone.getText());

            if (controller.modifierLocataire(l)) {
                JOptionPane.showMessageDialog(this, "Locataire modifié !");
                afficherLocataires();
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification !");
            }
        }
    }

    private void supprimerLocataires() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.supprimerLocataire(id)) {
                    JOptionPane.showMessageDialog(this, "Locataire supprimé !");
                    afficherLocataires();
                    viderChamps();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                }
            }
        }
    }

    public void viderChamps() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtCin.setText("");
        txtTelephone.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormLocataire::new);
    }
}
