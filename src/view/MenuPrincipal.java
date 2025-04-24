package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Application de Gestion de Logement");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 248, 255));
        mainPanel.setLayout(new BorderLayout());

        // Titre
        JLabel title = new JLabel("Tableau de Bord", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // Panel boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(6, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] noms = {
            "Gestion des Logements", "Gestion des Locataires", "Gestion des Contrats",
            "Gestion des Paiements", "Gestion des Entretiens", "Quitter"
        };

        JButton[] boutons = new JButton[noms.length];

        for (int i = 0; i < noms.length; i++) {
            boutons[i] = new JButton(noms[i]);
            boutons[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            boutons[i].setBackground(new Color(0, 123, 255));
            boutons[i].setForeground(Color.WHITE);
            boutons[i].setFocusPainted(false);
            boutons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            boutons[i].setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            final int index = i;
            boutons[i].addActionListener(e -> actionBouton(index));

            buttonPanel.add(boutons[i]);
        }

        // Couleur spécifique pour bouton Quitter
        boutons[5].setBackground(new Color(220, 53, 69));

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private void actionBouton(int index) {
        switch (index) {
            case 0 -> new FormLogement().setVisible(true);
            case 1 -> new FormLocataire().setVisible(true);
            case 2 -> new FormContratLocation().setVisible(true);
            case 3 -> new FormPaiement().setVisible(true);
            case 4 -> new FormEntretien().setVisible(true);
            case 5 -> System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
