package librarymangementsystem;

import javax.swing.*;
import java.awt.*;

public class LoginSelectionPage extends JFrame {
    public LoginSelectionPage() {
        setTitle("Login Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Replace with your PNG image path
                ImageIcon background = new ImageIcon("C:\\Users\\Shaba Rani\\Pictures\\Screenshots\\library login.jpg");
                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Scale to fit
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Transparent overlay panel for content
        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title label
        JLabel titleLabel = new JLabel("Select Login Type");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(titleLabel);
        overlayPanel.add(Box.createVerticalStrut(40));

        // Student Login Button
        JButton studentLogin = new JButton("Student Login");
        studentLogin.setFont(new Font("Arial", Font.BOLD, 24));
        studentLogin.setBackground(new Color(50, 150, 255));
        studentLogin.setForeground(Color.WHITE);
        studentLogin.setFocusPainted(false);
        studentLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentLogin.setMaximumSize(new Dimension(260, 60));

        overlayPanel.add(studentLogin);
        overlayPanel.add(Box.createVerticalStrut(30));

        // Admin Login Button
        JButton adminLogin = new JButton("Admin Login");
        adminLogin.setFont(new Font("Arial", Font.BOLD, 24));
        adminLogin.setBackground(new Color(255, 100, 100));
        adminLogin.setForeground(Color.WHITE);
        adminLogin.setFocusPainted(false);
        adminLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminLogin.setMaximumSize(new Dimension(260, 60));

        overlayPanel.add(adminLogin);

        // Center overlayPanel in backgroundPanel
        backgroundPanel.add(overlayPanel, new GridBagConstraints());

        // Button actions
        studentLogin.addActionListener(e -> {
            dispose();
            new LoginPage("student");
        });

        adminLogin.addActionListener(e -> {
            dispose();
            new LoginPage("admin");
        });

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}
