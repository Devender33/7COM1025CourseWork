package main.views;

import main.controllers.CustomerController;
import main.modal.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupView extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPasswordField walletField;
    private JButton signupButton;
    private CustomerController customerController;

    public SignupView() {
        this.customerController = CustomerController.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Signup");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(250, 30));
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);

        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(250, 30));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 30));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(confirmPasswordLabel, constraints);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(250, 30));
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(confirmPasswordField, constraints);

        JLabel walletLabel = new JLabel("Wallet:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(walletLabel, constraints);

        walletField = new JPasswordField(20);
        walletField.setPreferredSize(new Dimension(250, 30));
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(walletField, constraints);

        signupButton = new JButton("Signup");
        signupButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(signupButton, constraints);

        // Add loginButton to main.view login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            dispose();
        });
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        setContentPane(panel);
        pack();
        setSize(600, 400); // Set window size to 600x400 pixels


        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            Double wallet = Double.valueOf(walletField.getText());
            String password = passwordField.getText();
            Customer newCustomer = new Customer(name, email, wallet,password);
            customerController.addCustomer(newCustomer);
            JOptionPane.showMessageDialog(this, "Signup successful!");
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            dispose();
        }
    }
}
