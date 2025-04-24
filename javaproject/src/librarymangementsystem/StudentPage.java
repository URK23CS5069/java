package librarymangementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentPage extends JFrame {
    private int studentId;
    private String username;
    private JTextArea displayArea;
    private JComboBox<String> bookCombo;
    private int[] bookIds;

    public StudentPage(int studentId, String username) {
        this.studentId = studentId;
        this.username = username;

        // Set window properties
        setTitle("Student Dashboard - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        // Background panel with custom image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon background = new ImageIcon("C:\\Users\\Shaba Rani\\eclipse-workspace\\javaproject\\javaproject\\library student.jpg");
                Image img = background.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Scale to fit
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        
        // Main content panel (transparent)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        titlePanel.add(welcomeLabel);
        
        // Book selection panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setOpaque(false);
        selectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel label = new JLabel("Available Books:");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        selectionPanel.add(label);
        
        bookCombo = new JComboBox<>();
        bookCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        bookCombo.setPreferredSize(new Dimension(400, 40));
        selectionPanel.add(bookCombo);
        
        JButton chooseButton = new JButton("Take Book");
        chooseButton.setFont(new Font("Arial", Font.BOLD, 18));
        chooseButton.setBackground(new Color(50, 150, 255));
        chooseButton.setForeground(Color.WHITE);
        chooseButton.setFocusPainted(false);
        selectionPanel.add(chooseButton);
        
        // Display area panel
        JPanel displayPanel = new JPanel();
        displayPanel.setOpaque(false);
        displayPanel.setLayout(new BorderLayout());
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 18));
        displayArea.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent background
        displayArea.setForeground(Color.BLACK);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add all panels to content panel
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(selectionPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(displayPanel);
        
        // Add content panel to background panel
        backgroundPanel.add(contentPanel, new GridBagConstraints());
        
        // Button action
        chooseButton.addActionListener(e -> issueBook());
        
        // Set content pane and show frame
        setContentPane(backgroundPanel);
        setVisible(true);
        loadBooks();
    }

    private void loadBooks() {
        bookCombo.removeAllItems();
        displayArea.setText("");

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                displayArea.setText("Database connection failed.");
                return;
            }

            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);

            rs.last();  // Move the cursor to the last row
            int count = rs.getRow();  // Get the row count
            rs.beforeFirst();  // Move the cursor back to the beginning

            if (count == 0) {
                displayArea.setText("No books available.");
                return;
            }

            bookIds = new int[count];
            int idx = 0;

            while (rs.next()) {
                bookIds[idx] = rs.getInt("id");
                String bookDesc = rs.getString("title") + " by " + rs.getString("author") +
                        " [" + rs.getString("category") + "]";
                bookCombo.addItem(bookDesc);
                displayArea.append((idx + 1) + ". " + bookDesc + "\n");
                idx++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            displayArea.setText("Error loading books.");
        }
    }

    private void issueBook() {
        int idx = bookCombo.getSelectedIndex();
        if (idx < 0) return;
        int bookId = bookIds[idx];

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String checkSql = "SELECT * FROM issued_books WHERE book_id=? AND student_id=?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, bookId);
            checkPs.setInt(2, studentId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "You have already taken this book.");
                return;
            }

            String sql = "INSERT INTO issued_books (book_id, student_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.setInt(2, studentId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book taken successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error issuing book.");
        }
    }

    public void refreshBooks() {
        loadBooks();
    }
}

