import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BankManagementSystem {
    private static HashMap<String, String[]> userDatabase = new HashMap<>();
    private static HashMap<String, ArrayList<String>> transactionHistory = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankManagementSystem::showMainScreen);
    }

    // Show the main screen with login and signup buttons
    private static void showMainScreen() {
        JFrame frame = new JFrame("Bank Management System - Main");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(BankManagementSystem.class.getResource("/icons/banklogo.jpg"));
        Image backgroundImg = backgroundIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.add(panel);

        // Load logo image
        ImageIcon logoIcon = new ImageIcon(BankManagementSystem.class.getResource("/icons/banklogo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        panel.add(logoLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("Welcome to ByteDev0ps Bank", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(215, 255, 205));
        welcomeLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        panel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupScreen();
        });
    }

    private static void showSignupScreen() {
        JFrame signupFrame = new JFrame("ByteDev0ps Bank - Signup");
        signupFrame.setSize(400, 300);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signupFrame.setLocationRelativeTo(null);

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(BankManagementSystem.class.getResource("/icons/banklogo.jpg"));
        Image backgroundImg = backgroundIcon.getImage().getScaledInstance(signupFrame.getWidth(), signupFrame.getHeight(), Image.SCALE_SMOOTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);

        JButton createAccountButton = new JButton("Create Account");
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(createAccountButton);

        signupFrame.add(panel);
        signupFrame.setVisible(true);

        createAccountButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.");
            } else {
                String accountNumber = generateAccountNumber();
                userDatabase.put(username, new String[]{password, accountNumber, "0"});
                transactionHistory.put(username, new ArrayList<>());
                JOptionPane.showMessageDialog(null, "Account created successfully! Your account number is: " + accountNumber);
                signupFrame.dispose();
                showMainScreen();
            }
        });
    }

    private static void showLoginScreen() {
        JFrame frame = new JFrame("ByteDev0ps Bank - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(BankManagementSystem.class.getResource("/icons/banklogo.jpg"));
        Image backgroundImg = backgroundIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(20);
        JLabel accountNumberLabel = new JLabel("Account No:");
        JTextField accountNumberText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        panel.add(userLabel);
        panel.add(userText);
        panel.add(accountNumberLabel);
        panel.add(accountNumberText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String accountNumber = accountNumberText.getText();
            String password = new String(passwordText.getPassword());

            if (userDatabase.containsKey(username) &&
                    userDatabase.get(username)[0].equals(password) &&
                    userDatabase.get(username)[1].equals(accountNumber)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                frame.dispose();
                openDashboard(username);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }
        });
    }

    private static void openDashboard(String username) {
        JFrame dashboardFrame = new JFrame("ByteDev0ps Bank - Dashboard");
        dashboardFrame.setSize(500, 450);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(BankManagementSystem.class.getResource("/icons/banklogo.jpg"));
        Image backgroundImg = backgroundIcon.getImage().getScaledInstance(dashboardFrame.getWidth(), dashboardFrame.getHeight(), Image.SCALE_SMOOTH);

        JPanel dashboardPanel = new JPanel(new GridLayout(6, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(0, 102, 204));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton logoutButton = new JButton("Logout");

        dashboardPanel.add(welcomeLabel);
        dashboardPanel.add(depositButton);
        dashboardPanel.add(withdrawButton);
        dashboardPanel.add(checkBalanceButton);
        dashboardPanel.add(transactionHistoryButton);
        dashboardPanel.add(logoutButton);

        dashboardFrame.add(dashboardPanel);
        dashboardFrame.setVisible(true);

        depositButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(dashboardFrame, "Enter amount to deposit:");
            if (amountStr != null && !amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                double currentBalance = Double.parseDouble(userDatabase.get(username)[2]);
                double newBalance = currentBalance + amount;
                userDatabase.get(username)[2] = String.valueOf(newBalance);
                transactionHistory.get(username).add("Deposited: " + amount);
                JOptionPane.showMessageDialog(dashboardFrame, "Deposit successful! New balance: " + newBalance);
            }
        });

        withdrawButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(dashboardFrame, "Enter amount to withdraw:");
            if (amountStr != null && !amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                double currentBalance = Double.parseDouble(userDatabase.get(username)[2]);
                if (amount <= currentBalance) {
                    double newBalance = currentBalance - amount;
                    userDatabase.get(username)[2] = String.valueOf(newBalance);
                    transactionHistory.get(username).add("Withdrew: " + amount);
                    JOptionPane.showMessageDialog(dashboardFrame, "Withdrawal successful! New balance: " + newBalance);
                } else {
                    JOptionPane.showMessageDialog(dashboardFrame, "Insufficient funds.");
                }
            }
        });

        checkBalanceButton.addActionListener(e -> {
            double balance = Double.parseDouble(userDatabase.get(username)[2]);
            JOptionPane.showMessageDialog(dashboardFrame, "Your current balance is: " + balance);
        });

        transactionHistoryButton.addActionListener(e -> {
            ArrayList<String> history = transactionHistory.get(username);
            if (history.isEmpty()) {
                JOptionPane.showMessageDialog(dashboardFrame, "No transactions yet.");
            } else {
                StringBuilder historyStr = new StringBuilder("Transaction History:\n");
                for (String transaction : history) {
                    historyStr.append(transaction).append("\n");
                }
                JOptionPane.showMessageDialog(dashboardFrame, historyStr.toString());
            }
        });

        logoutButton.addActionListener(e -> {
            dashboardFrame.dispose();
            showMainScreen();
        });
    }

    private static String generateAccountNumber() {
        Random random = new Random();
        return "ACC" + (100000 + random.nextInt(900000));
    }
}
