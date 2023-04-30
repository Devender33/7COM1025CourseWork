package main.views;

import main.controllers.CustomerController;
import main.modal.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private JButton viewReportsButton;
    private JButton signUpButton;
    private CustomerController customerController;

    public LoginView() {
        this.customerController = CustomerController.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to MyFitnessPal");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(emailLabel, constraints);

        emailField = new JTextField(20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            SignupView signupView = new SignupView();
            signupView.setVisible(true);
        });
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(signUpButton, constraints);

        viewReportsButton = new JButton("View Reports");
        viewReportsButton.addActionListener(e -> {
            ReportView reportView = new ReportView();
            reportView.setVisible(true);


        });
        viewReportsButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(viewReportsButton, constraints);

        setContentPane(panel);
        pack();
        setSize(500, 350); // Set window size to 500x350 pixels
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Customer customer = customerController.loginCustomer(email, password);
            if (customer != null) {
                MainView mainView = new MainView(customer);
                mainView.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password");
            }
        }
    }
}