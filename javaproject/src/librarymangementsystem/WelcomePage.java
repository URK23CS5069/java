package librarymangementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("Karunya Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen window

        // Custom panel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Use double backslashes or a single forward slash in the path
                ImageIcon background = new ImageIcon("C:\\Users\\Shaba Rani\\eclipse-workspace\\javaproject\\javaproject\\welcome library.jpg");
                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Scale to fit window
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // For centering content

        // Transparent overlay panel to center components
        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false); // Transparent
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Karunya Library");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Spacing
        overlayPanel.add(Box.createVerticalStrut(50));
        overlayPanel.add(welcomeLabel);
        overlayPanel.add(Box.createVerticalStrut(40));

        // Next button
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 28));
        nextButton.setBackground(new Color(50, 150, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(180, 60));
        nextButton.setMaximumSize(new Dimension(180, 60));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(nextButton);

        // Center overlayPanel in backgroundPanel
        backgroundPanel.add(overlayPanel, new GridBagConstraints());

        // Button action
        nextButton.addActionListener(e -> {
            dispose();
            new LoginSelectionPage(); // Show next page
        });

        setContentPane(backgroundPanel);
        setVisible(true);
    }
}
