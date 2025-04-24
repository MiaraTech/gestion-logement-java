package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class SplashScreenForm extends JFrame {

    private JProgressBar progressBar;

    public SplashScreenForm() {
        // Configuration du panneau principal
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Titre de l'écran de démarrage
        JLabel label = new JLabel("GESTION DE LOGEMENT", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 102, 204));

        // Texte de chargement
        JLabel loading = new JLabel("Chargement ...", JLabel.CENTER);
        loading.setFont(new Font("Arial", Font.PLAIN, 16));

        // Barre de progression
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new java.awt.Dimension(300, 30));
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 153, 0));
        progressBar.setBackground(Color.LIGHT_GRAY);

        // Ajouter les composants au panneau
        content.add(label, BorderLayout.NORTH);
        content.add(progressBar, BorderLayout.CENTER);
        content.add(loading, BorderLayout.SOUTH);

        // Configuration de la fenêtre Splash
        setSize(400, 200);
        setLocationRelativeTo(null);
        setUndecorated(true);  // Supprimer les bordures de la fenêtre
        setOpacity(0.9f);  // Rendre la fenêtre légèrement transparente
        setVisible(true);

        // Simuler un délai de chargement
        try {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);  // Mettre à jour la barre de progression
                Thread.sleep(50);  // Attendre 50 ms pour simuler un chargement
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fermer le SplashScreen
        dispose();

        // Ouvrir la fenêtre de connexion après le chargement
        new LoginForm().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SplashScreenForm().setVisible(true);
            }
        });
    }
}
