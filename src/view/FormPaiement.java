package view;

import controller.ContratLocationController;
import controller.PaiementController;
import model.ContratLocation;
import model.Paiement;
import model.Locataire;
import model.Logement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.time.LocalDate;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class FormPaiement extends JFrame {

    private JComboBox<ContratLocation> comboContrat;
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
        remplirContrats();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Paiement"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboContrat = new JComboBox<>();
        txtDatePaiement = new JTextField(LocalDate.now().toString());
        txtMontant = new JTextField();

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Contrat : "), gbc);
        gbc.gridx = 1;
        panel.add(comboContrat, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Date paiement (yyyy-mm-dd) : "), gbc);
        gbc.gridx = 1;
        panel.add(txtDatePaiement, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Montant : "), gbc);
        gbc.gridx = 1;
        panel.add(txtMontant, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton btnEnregistrer = createStyledButton("ENREGISTRER", new Color(40, 167, 69));
        panel.add(btnEnregistrer, gbc);

        gbc.gridx = 1;
        JButton btnSupprimer = createStyledButton("SUPPRIMER", new Color(220, 53, 69));
        panel.add(btnSupprimer, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton btnRetour = createStyledButton("RETOUR", new Color(108, 117, 125));
        panel.add(btnRetour, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton btnPdf = createStyledButton("GÉNÉRER PDF", new Color(0, 123, 255));
        panel.add(btnPdf, gbc);

        btnEnregistrer.addActionListener(e -> ajouterPaiement());
        btnSupprimer.addActionListener(e -> supprimerPaiement());
        btnRetour.addActionListener(e -> dispose());
        btnPdf.addActionListener(e -> genererPDF());

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

    private void ajouterPaiement() {
        try {
            Paiement p = new Paiement();
            ContratLocation contrat = (ContratLocation) comboContrat.getSelectedItem();
            p.setIdContrat(contrat.getId());
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

    private void viderChamps() {
        txtDatePaiement.setText("");
        txtMontant.setText("");
    }

    private void afficherPaiements() {
        model.setRowCount(0);
        for (Paiement p : controller.getTousPaiements()) {
            model.addRow(new Object[]{
                p.getId(), p.getIdContrat(), p.getDatePaiement(), p.getMontant()
            });
        }
    }

    private void remplirContrats() {
        comboContrat.removeAllItems();
        ContratLocationController lc = new ContratLocationController();
        for (ContratLocation cl : lc.getTousContrats()) {
            comboContrat.addItem(cl);
        }
    }

    private void genererPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("paiements.pdf"));
            document.open();

            document.add(new Paragraph("Liste des Paiements", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espace

            PdfPTable pdfTable = new PdfPTable(7);
            pdfTable.setWidthPercentage(100);
            pdfTable.setWidths(new int[]{2, 2, 3, 2, 3, 4, 2});

            pdfTable.addCell("ID");
            pdfTable.addCell("Contrat");
            pdfTable.addCell("Date");
            pdfTable.addCell("Montant");
            pdfTable.addCell("Locataire");
            pdfTable.addCell("Adresse");
            pdfTable.addCell("Loyer");

            for (Paiement p : controller.getTousPaiements()) {
                ContratLocation contrat = controller.getContratById(p.getIdContrat());
                Locataire loc = contrat.getLocataire();
                Logement log = contrat.getLogement();

                pdfTable.addCell(String.valueOf(p.getId()));
                pdfTable.addCell(String.valueOf(contrat.getId()));
                pdfTable.addCell(p.getDatePaiement().toString());
                pdfTable.addCell(String.valueOf(p.getMontant()));
                pdfTable.addCell(loc.getNom() + " " + loc.getPrenom());
                pdfTable.addCell(log.getAdresse());
                pdfTable.addCell(String.valueOf(log.getLoyer()));
            }

            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(this, "PDF généré avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur génération PDF : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPaiement().setVisible(true));
    }
}
