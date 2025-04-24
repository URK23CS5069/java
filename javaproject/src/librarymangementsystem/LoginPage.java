package librarymangementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    public LoginPage(String role) {
        setTitle(role.substring(0,1).toUpperCase() + role.substring(1) + " Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Update this path to your PNG image
                ImageIcon background = new ImageIcon("C:\\Users\\Shaba Rani\\eclipse-workspace\\javaproject\\javaproject\\library student.jpg");
                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Scale to fit
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Transparent overlay panel for the login form
        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title label
        JLabel titleLabel = new JLabel(role.substring(0,1).toUpperCase() + role.substring(1) + " Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(titleLabel);
        overlayPanel.add(Box.createVerticalStrut(40));

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 22));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField(18);
        userField.setMaximumSize(new Dimension(300, 40));
        userField.setFont(new Font("Arial", Font.PLAIN, 20));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(userLabel);
        overlayPanel.add(Box.createVerticalStrut(10));
        overlayPanel.add(userField);
        overlayPanel.add(Box.createVerticalStrut(20));

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 22));
        passLabel.setForeground(Color.WHITE);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passField = new JPasswordField(18);
        passField.setMaximumSize(new Dimension(300, 40));
        passField.setFont(new Font("Arial", Font.PLAIN, 20));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(passLabel);
        overlayPanel.add(Box.createVerticalStrut(10));
        overlayPanel.add(passField);
        overlayPanel.add(Box.createVerticalStrut(30));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton.setBackground(new Color(50, 150, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(200, 50));

        overlayPanel.add(loginButton);

        // Center overlayPanel in backgroundPanel
        backgroundPanel.add(overlayPanel, new GridBagConstraints());

        // Button action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            try (DBConnection db = new DBConnection()) {
                Connection conn = db.getConnection();
                String sql = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, role);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    dispose();
                    if (role.equals("student")) {
                        new StudentPage(rs.getInt("id"), username);
                    } else {
                        new AdminPage(username);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to database.");
            }
        });

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}

