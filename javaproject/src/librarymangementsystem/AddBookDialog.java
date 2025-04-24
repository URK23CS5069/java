package librarymangementsystem;

import javax.swing.*;
import java.sql.*;

public class AddBookDialog extends JDialog {
    public AddBookDialog(JFrame parent) {
        super(parent, "Add New Book", true);
        setSize(350, 300);
        setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(30, 30, 80, 25);
        add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(120, 30, 180, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(30, 70, 80, 25);
        add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(120, 70, 180, 25);
        add(authorField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(30, 110, 80, 25);
        add(categoryLabel);

        JTextField categoryField = new JTextField();
        categoryField.setBounds(120, 110, 180, 25);
        add(categoryField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(120, 160, 120, 30);
        add(addButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required!");
                return;
            }
            try (Connection conn = DBConnection.connect()) {
                String sql = "INSERT INTO books (title, author, category) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, category);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                if (parent instanceof StudentPage) {
                    ((StudentPage)parent).refreshBooks();
                }
                if (parent instanceof AdminPage) {
                    ((AdminPage)parent).refreshBooks();
                }
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding book.");
            }
        });

        setVisible(true);
    }
}