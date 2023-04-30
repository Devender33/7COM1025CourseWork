package main.views;

import main.modal.Customer;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JFrame {

    private JPanel contentPane;
    private JLabel lblCustomerName;
    private JLabel lblEmail;

    public ProfileView(Customer customer) {
        initComponents(customer);
    }

    private void initComponents(Customer customer) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Profile");
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        // Customer Name
        lblCustomerName = new JLabel("Customer Name: " + customer.getCustomerName());
        lblCustomerName.setFont(new Font("Sans Serif", Font.BOLD, 18));
        GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
        gbc_lblCustomerName.gridx = 0;
        gbc_lblCustomerName.gridy = 0;
        gbc_lblCustomerName.insets = new Insets(10, 10, 5, 10);
        contentPane.add(lblCustomerName, gbc_lblCustomerName);

        // Email
        lblEmail = new JLabel("Email: " + customer.getCustomerEmail());
        lblEmail.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 1;
        gbc_lblEmail.insets = new Insets(5, 10, 5, 10);
        contentPane.add(lblEmail, gbc_lblEmail);

    }
}
