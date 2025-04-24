
package librarymangementsystem;
import java.sql.*;

public class DatabaseConnection {
    public static DBConnection connect() throws SQLException {
        // Update with your DB details
        String url = "jdbc:mysql://localhost:3306/librarydb";
        String user = "root";
        String password = "rootmysql";
        return (DBConnection) DriverManager.getConnection(url, user, password);
    }
}