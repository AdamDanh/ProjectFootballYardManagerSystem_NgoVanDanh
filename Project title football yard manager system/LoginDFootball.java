package dfootballmanager;

import javax.swing.*;
import java.awt.*;

public class LoginDFootball extends JFrame {

    private JTextField tfUser;
    private JPasswordField tfPass;
    private JButton btnLogin, btnExit;

    public LoginDFootball() {
        setTitle("Login - Football Yard Manager");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        tfUser = new JTextField();
        panel.add(tfUser);

        panel.add(new JLabel("Password:"));
        tfPass = new JPasswordField();
        panel.add(tfPass);

        btnLogin = new JButton("Login");
        btnExit = new JButton("Exit");

        panel.add(btnLogin);
        panel.add(btnExit);

        add(panel);

        btnLogin.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = new String(tfPass.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                SwingUtilities.invokeLater(() -> new DFootballUI().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginDFootball().setVisible(true));
    }
}
