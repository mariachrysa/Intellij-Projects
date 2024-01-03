import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @Author Maria Chrysanthou
 * @Date 28/07/2023
 * GUI project which projects a window to the user in which they either choose to log in or sign up, and saves the signed up data
 * so that it remembers the user when they try to log in next time.
 */
public class LoginGUI implements ActionListener {

    private static JFrame frame;
    private static JPanel panel;
    private static JLabel userLabel, passwordLabel, success;
    private static JButton loginButton, signUpButton;

    private static JTextField userText;
    private static JPasswordField passwordText;
    private static final String DB_URL = "jdbc:sqlite:users.db";


    public void setGUI () {
        frame = new JFrame();
        panel = new JPanel();
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign up");

        userLabel = new JLabel("User: ");
        passwordLabel = new JLabel("Password: ");

        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        userLabel.setBounds(10, 20, 80, 25);
        passwordLabel.setBounds(10, 50, 80, 25);
        loginButton.setBounds(80, 100, 80, 25);
        signUpButton.setBounds(180, 100, 80, 25);
        panel.add(userLabel);
        panel.add(passwordLabel);

        loginButton.addActionListener(new LoginGUI());
        signUpButton.addActionListener(new LoginGUI());
        panel.add(loginButton);
        panel.add(signUpButton);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        success = new JLabel(" ");
        success.setBounds(10, 130, 300, 60);
        panel.add(success);

        // Apply the design to components
        DesignGUI.applyDesign(frame);
        DesignGUI.applyDesign(panel);
        DesignGUI.applyDesign(loginButton);
        DesignGUI.applyDesign(signUpButton);
        DesignGUI.applyDesign(userLabel);
        DesignGUI.applyDesign(passwordLabel);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 153), 2)); // doesn't work ??
        signUpButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 153), 2));

        frame.setVisible(true);
    }

    /**
     * Runs the program in main
     * @param args
     */
    public static void main (String args[]){
        new LoginGUI().setGUI();
    }

    /**
     * Hashes the user's password with a salt.
     *
     * @param password the user's password
     * @return the hashed password
     */
    private String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Checks if the username and password exist in the database and match into a profile
     * @param username the existing username of the user
     * @param password the existing password of the user
     * @return true if the log in has matched into a profile, otherwise false
     */
    private boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword);  // Credentials will have to either match with the salt or return false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Credentials don't match, login failed.
    }

    /**
     * Inserts the user's sign up info into the database
     * @param username the username created by the user
     * @param password the password created by the user
     */
    private boolean signUpUser(String username, String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = hashPassword(password);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();

            return true; // signup successful
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
                // Handle the case where the username is already taken
                return false;
            } else {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean isStrongPassword(String password){

        if (password.length() < 8) {
            return false;
        }

        boolean hasLowercase = false, hasUppercase = false, hasSpecialChar = false, hasDigit = false;
        String specialChars = "!@#$%^&*-_.?";

        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c))
                hasUppercase = true;
            else if (Character.isLowerCase(c))
                hasLowercase = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (specialChars.contains(String.valueOf(c)))
                hasSpecialChar = true;
        }

        return (hasLowercase && hasUppercase && hasSpecialChar && hasDigit);
    }

    /**
     * For the log in button, it checks if the user and password info are correct in the database, and for the sign
     * up button it adds a new user to the existing database, alongside presenting the according message
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();

        if (e.getSource() == loginButton) {
            if (loginUser(user, password)) {
                success.setText("Login successful!");
            } else {
                success.setText("Invalid username or password.");
            }
        } else if (e.getSource() == signUpButton) {
            if ((user.length() < 4) || (user.length() > 16)) {
                success.setText("Username length must be between 4 - 16 characters.");
            } else if (!isStrongPassword(password)) {
                success.setText("<html>Password is not strong enough.<br> It should contain at least 8 characters with a mix of uppercase, lowercase, digits, and special characters.</html>");
            } else {
                boolean signUpSuccess = signUpUser(user, password);
                if (signUpSuccess) {
                    success.setText("Sign up successful! You can now log in.");
                } else {
                    success.setText("<html>Username already taken.<br>Please choose a different username.</html>");
                }
            }
        }
    }


}
