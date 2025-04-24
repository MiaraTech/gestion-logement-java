package view;

import dao.Connexion;
import dao.UtilisateurDAO;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtRole;

    public RegisterForm() {
        setTitle("Créer un compte utilisateur");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Inscription");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Nom d'utilisateur : "), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        txtUsername.setFont(fieldFont);
        panel.add(txtUsername, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Mot de passe : "), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(fieldFont);
        panel.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Rôle : "), gbc);
        gbc.gridx = 1;
        txtRole = new JTextField(15);
        txtRole.setFont(fieldFont);
        panel.add(txtRole, gbc);

        // Boutons
        gbc.gridy++;
        gbc.gridx = 0;
        JButton btnRegister = new JButton("S'inscrire");
        btnRegister.setBackground(new Color(0, 120, 215));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        panel.add(btnRegister, gbc);

        gbc.gridx = 1;
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBackground(new Color(220, 220, 220));
        btnAnnuler.setFocusPainted(false);
        panel.add(btnAnnuler, gbc);

        add(panel);

        // Actions
        btnRegister.addActionListener(e -> {
            Utilisateur u = new Utilisateur(0, txtUsername.getText(), new String(txtPassword.getPassword()), txtRole.getText());

            UtilisateurDAO dao = new UtilisateurDAO(Connexion.getConnection());

            if (dao.ajouterUtilisateur(u)) {
                JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                dispose();
                new LoginForm().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création du compte.");
            }
        });

        btnAnnuler.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}
