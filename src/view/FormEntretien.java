package view;

import controller.EntretienController;
import controller.LogementController;
import model.Entretien;
import model.Logement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FormEntretien extends JFrame {
    private JTextField txtDescription;
    private JComboBox<Logement> cbLogement;
    private JTable table;
    private DefaultTableModel tableModel;

    private EntretienController controller = new EntretienController();

    public FormEntretien() {
        setTitle("Gestion des Entretiens");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Formulaire en haut
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 12, 12));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nouveau Entretien"));
        panelForm.setBackground(Color.WHITE);

        txtDescription = new JTextField();
        cbLogement = new JComboBox<>();
        remplirLogements();

        JButton btnEnregistrer = createStyledButton("Enregistrer", new Color(40, 167, 69));
        btnEnregistrer.addActionListener(e -> ajouterEntretien());

        panelForm.add(new JLabel("Logement:", JLabel.RIGHT));
        panelForm.add(cbLogement);
        panelForm.add(new JLabel("Description:", JLabel.RIGHT));
        panelForm.add(txtDescription);
        panelForm.add(new JLabel(""));
        panelForm.add(btnEnregistrer);

        // Tableau au centre
        tableModel = new DefaultTableModel(new String[]{"ID", "Logement", "Date", "Description"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        // Boutons bas
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBas.setBackground(Color.WHITE);

        JButton btnSupprimer = createStyledButton("Supprimer", new Color(220, 53, 69));
        btnSupprimer.addActionListener(e -> supprimerEntretien());

        JButton btnRetour = createStyledButton("Retour", new Color(108, 117, 125));
        btnRetour.addActionListener(e -> dispose());

        panelBas.add(btnRetour);
        panelBas.add(btnSupprimer);

        mainPanel.add(panelForm, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelBas, BorderLayout.SOUTH);

        add(mainPanel);
        chargerEntretiens();
        setVisible(true);
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

    private void ajouterEntretien() {
        try {
            if (txtDescription.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La description ne peut pas être vide !");
                return;
            }

            Entretien e = new Entretien();
            e.setIdLogement(((Logement) cbLogement.getSelectedItem()).getId());
            e.setDateEntretien(LocalDate.now());
            e.setDescription(txtDescription.getText().trim());

            if (controller.ajouterEntretien(e)) {
                chargerEntretiens();
                txtDescription.setText("");
                JOptionPane.showMessageDialog(this, "Entretien enregistré !");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void supprimerEntretien() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.supprimerEntretien(id)) {
                    chargerEntretiens();
                    txtDescription.setText("");
                    JOptionPane.showMessageDialog(this, "Entretien supprimé !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
                }
            }
        }
    }

    private void chargerEntretiens() {
        tableModel.setRowCount(0);
        List<Entretien> entretiens = controller.getTousEntretiens();
        for (Entretien e : entretiens) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    "Logement #" + e.getIdLogement(),
                    e.getDateEntretien(),
                    e.getDescription()
            });
        }
    }

    private void remplirLogements() {
        cbLogement.removeAllItems();
        LogementController lc = new LogementController();
        for (Logement l : lc.getTousLesLogements()) {
            cbLogement.addItem(l);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormEntretien::new);
    }
}
