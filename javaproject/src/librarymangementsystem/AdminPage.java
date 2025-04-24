package librarymangementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminPage extends JFrame {
    private JTextArea displayArea;

    public AdminPage(String username) {
        setTitle("Admin Dashboard - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Replace with your PNG image path
                ImageIcon background = new ImageIcon("C:\\Users\\Shaba Rani\\eclipse-workspace\\javaproject\\javaproject\\library admin.jpg");
                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Scale to fit
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Overlay panel for content
        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Admin Dashboard - " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlayPanel.add(titleLabel);
        overlayPanel.add(Box.createVerticalStrut(30));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

        JButton viewIssued = new JButton("View Issued Books");
        viewIssued.setFont(new Font("Arial", Font.BOLD, 20));
        viewIssued.setBackground(new Color(50, 150, 255));
        viewIssued.setForeground(Color.WHITE);
        viewIssued.setFocusPainted(false);
        viewIssued.setPreferredSize(new Dimension(220, 50));

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setFont(new Font("Arial", Font.BOLD, 20));
        addBookButton.setBackground(new Color(100, 200, 100));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setFocusPainted(false);
        addBookButton.setPreferredSize(new Dimension(160, 50));

        buttonPanel.add(viewIssued);
        buttonPanel.add(addBookButton);

        overlayPanel.add(buttonPanel);
        overlayPanel.add(Box.createVerticalStrut(30));

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 18));
        displayArea.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent
        displayArea.setForeground(Color.BLACK);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        overlayPanel.add(scrollPane);

        // Add overlay to background
        backgroundPanel.add(overlayPanel, new GridBagConstraints());

        // Button actions
        viewIssued.addActionListener(e -> loadIssuedBooks());
        addBookButton.addActionListener(e -> new AddBookDialog(this));

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void loadIssuedBooks() {
        displayArea.setText("");

        try (DBConnection db = new DBConnection()) {
            Connection conn = db.getConnection();

            String sql = "SELECT ib.id, b.title, u.username, ib.issue_date FROM issued_books ib "
                       + "JOIN books b ON ib.book_id = b.id "
                       + "JOIN users u ON ib.student_id = u.id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                count++;
                displayArea.append(count + ". " + rs.getString("title") + " issued by " +
                        rs.getString("username") + " on " + rs.getTimestamp("issue_date") + "\n");
            }

            if (count == 0) {
                displayArea.setText("No books issued.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            displayArea.setText("Error loading issued books.");
        }
    }

    // For reloading book list after adding new book
    public void refreshBooks() {}
}
