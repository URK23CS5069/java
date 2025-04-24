package librarymangementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable {
    private Connection conn;

    // Constructor that creates a new connection
    public DBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/librarydb"; // Database URL
        String user = "root";  // Your DB username
        String password = "rootmysql";  // Your DB password
        conn = DriverManager.getConnection(url, user, password); // Establish the connection
    }

    // This method returns the connection
    public Connection getConnection() {
        return conn;
    }

    // Close the connection when done
    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Static method for easier connection handling
    public static Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/librarydb";  // Database URL
            String user = "root";  // DB username
            String password = "rootmysql";  // DB password
            return DriverManager.getConnection(url, user, password);  // Return the connection
        } catch (SQLException e) {
            e.printStackTrace();  // Print error if connection fails
            return null;  // Return null if connection cannot be established
        }
    }
}

