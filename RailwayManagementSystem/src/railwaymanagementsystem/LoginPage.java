package railwaymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Indian Railway Admin Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
 
        ImageIcon bgImage = new ImageIcon(getClass().getResource("/railwaymanagementsystem/images/Passenger_bg.jpg"));
        JLabel backgroundLabel = new JLabel(bgImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        // Header
        JLabel headerLabel = new JLabel("Welcome to Indian Railways");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, Color.DARK_GRAY, Color.BLACK);
        
        JButton exitButton = new JButton("Exit");
        styleButton(exitButton, Color.DARK_GRAY, Color.BLACK);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            if (authenticate(username, password)) {
                this.dispose();
                new RailwayManagementSystem().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        exitButton.addActionListener(e -> System.exit(0));
        
        add(mainPanel);
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }
    
    private boolean authenticate(String username, String password) {
        // Simple authentication - replace with database check
        return "admin".equals(username) && "admin123".equals(password);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}