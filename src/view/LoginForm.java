package view;

import dao.Connexion;
import dao.UtilisateurDAO;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Connexion Utilisateur");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Police personnalisée
        Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
        Font fontField = new Font("SansSerif", Font.PLAIN, 14);

        // Panel principal centré
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE); // fond clair

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement

        JLabel title = new JLabel("Authentification");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nom d'utilisateur : "), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        txtUsername.setFont(fontField);
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Mot de passe : "), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(fontField);
        panel.add(txtPassword, gbc);

        // Boutons
        JButton btnLogin = new JButton("Connexion");
        JButton btnRegister = new JButton("Créer un compte");

        btnLogin.setFocusPainted(false);
        btnRegister.setFocusPainted(false);
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(220, 220, 220));

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        panel.add(btnRegister, gbc);

        add(panel); // Ajouter le panel à la frame

        // Actions
        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            UtilisateurDAO dao = new UtilisateurDAO(Connexion.getConnection());

            if (dao.verifierConnexion(user, pass)) {
                dispose();
                new MenuPrincipal().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
            }
        });

        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterForm().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
