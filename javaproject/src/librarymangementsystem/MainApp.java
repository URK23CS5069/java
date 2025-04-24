package librarymangementsystem;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WelcomePage(); // This creates and displays the WelcomePage window
        });
    }
}
