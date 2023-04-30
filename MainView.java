package main.views;

import main.modal.Customer;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
public class MainView extends JFrame {
    private JPanel contentPane;
    private JLabel lblWelcome;
    private JLabel lblBalance;
    private JButton btnBook;
    private JButton btnManageBookings;
    private JButton btnProfile;
    private JButton btnLogout;
    private Customer customer;
    public MainView(Customer customer) {
        this.customer = customer;
        initComponents();
    }
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel("MyBookings");
        lblLogo.setFont(new Font("Sans Serif", Font.BOLD, 24));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblLogo, BorderLayout.CENTER);

        // Body Panel
        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        contentPane.add(bodyPanel, BorderLayout.CENTER);

        // Welcome Message
        lblWelcome = new JLabel("Welcome, " + customer.getCustomerName() + "!");
        lblWelcome.setFont(new Font("Sans Serif", Font.BOLD, 20));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
        gbc_lblWelcome.gridx = 0;
        gbc_lblWelcome.gridy = 0;
        gbc_lblWelcome.gridwidth = 2;
        gbc_lblWelcome.insets = new Insets(10, 0, 10, 0);
        bodyPanel.add(lblWelcome, gbc_lblWelcome);

        // Balance
        lblBalance = new JLabel("Your Wallet Balance: $" + NumberFormat.getInstance().format(customer.getCustomerWallet()));
        lblBalance.setFont(new Font("Sans Serif", Font.BOLD, 16));
        lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblBalance = new GridBagConstraints();
        gbc_lblBalance.gridx = 0;
        gbc_lblBalance.gridy = 1;
        gbc_lblBalance.gridwidth = 2;
        gbc_lblBalance.insets = new Insets(0, 0, 20, 0);
        bodyPanel.add(lblBalance, gbc_lblBalance);

        // Book a Ticket Button
        btnBook = new JButton("Book a Ticket");
        btnBook.setFont(new Font("Sans Serif", Font.BOLD, 16));
        GridBagConstraints gbc_btnBook = new GridBagConstraints();
        gbc_btnBook.gridx = 0;
        gbc_btnBook.gridy = 2;
        gbc_btnBook.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnBook.insets = new Insets(0, 20, 20, 10);
        bodyPanel.add(btnBook, gbc_btnBook);

        // Manage Bookings Button
        btnManageBookings = new JButton("Manage Bookings");
        btnManageBookings.setFont(new Font("Sans Serif", Font.BOLD, 16));
        GridBagConstraints gbc_btnManageBookings = new GridBagConstraints();
        gbc_btnManageBookings.gridx = 1;
        gbc_btnManageBookings.gridy = 2;
        gbc_btnManageBookings.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnManageBookings.insets = new Insets(0, 10, 20, 20);
        bodyPanel.add(btnManageBookings, gbc_btnManageBookings);

        // Profile Button
        btnProfile = new JButton("Profile");
        btnProfile.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        btnProfile.setPreferredSize(new Dimension(120, 40));

        // Logout Button
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        btnLogout.setPreferredSize(new Dimension(120, 40));

        // create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // add the buttons to the panel
        buttonPanel.add(btnProfile);
        buttonPanel.add(btnLogout);

        // add the button panel to the content pane
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // add action listeners for the buttons
        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // open BookView
                TimetableView timetableView  = new TimetableView(customer,null);
                timetableView.setVisible(true);
            }
        });

        btnManageBookings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // open ManageBookingsView
                ManageBookingsView manageBookingsView = new ManageBookingsView(customer);
                manageBookingsView.setVisible(true);
            }
        });

        btnProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // open ProfileView
                ProfileView profileView = new ProfileView(customer);
                profileView.setVisible(true);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // close the MainView and open the LoginView
                dispose();
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            }
        });
    }
}
