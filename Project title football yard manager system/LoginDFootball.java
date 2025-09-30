package dfootballmanager;

import javax.swing.*;
import java.awt.*;

public class LoginDFootball extends JFrame {

    private JTextField tfUser;
    private JPasswordField tfPass;
    private JButton btnLogin, btnExit;

    public LoginDFootball() {
        setTitle("Login - Football Yard Manager");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("⚽ Football Yard Manager Login ⚽", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 200, 100));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(40, 40, 40));

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        tfUser = new JTextField();

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        tfPass = new JPasswordField();

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 180, 90));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(200, 50, 50));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));

        formPanel.add(lblUser);
        formPanel.add(tfUser);
        formPanel.add(lblPass);
        formPanel.add(tfPass);
        formPanel.add(btnLogin);
        formPanel.add(btnExit);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        btnLogin.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = new String(tfPass.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "✅ Login successful!");
                dispose();
                SwingUtilities.invokeLater(() -> new DFootballUI().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Invalid username or password!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginDFootball().setVisible(true));
    }
}
