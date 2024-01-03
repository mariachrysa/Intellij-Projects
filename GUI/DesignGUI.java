import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class DesignGUI {

    // Define seafoam and lavender colors
    private static final Color SEAFOAM = new Color(120, 222, 169);
    private static final Color LAVENDER = new Color(200, 162, 200);

    // Apply the design to a JFrame
    public static void applyDesign(JFrame frame) {
        frame.getContentPane().setBackground(SEAFOAM);
    }

    // Apply the design to a JPanel
    public static void applyDesign(JPanel panel) {
        panel.setBackground(LAVENDER);
    }

    // Apply the design to a JLabel
    public static void applyDesign(JLabel label) {
        label.setForeground(Color.WHITE); // Set text color to white
    }

    // Apply the design to a JButton
    public static void applyDesign(JButton button) {
        button.setBackground(SEAFOAM);
        button.setForeground(Color.WHITE); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove button border
    }

    // Apply the design to a JTextComponent (e.g., JTextField, JPasswordField)
    public static void applyDesign(JTextComponent textComponent) {
        textComponent.setBackground(Color.WHITE);
        textComponent.setForeground(Color.BLACK);
        textComponent.setBorder(BorderFactory.createCompoundBorder(
                textComponent.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Add padding
        ));
    }
}
