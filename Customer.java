package main.modal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Customer {
    private static int customerCounter = 0;

    private int customerId;
    private String customerName;
    private String customerEmail;
    private Double customerWallet;
    private String passwordHash;

    public Customer(String name, String email, Double wallet, String password) {
        this.customerId = ++customerCounter;
        this.customerName = name;
        this.customerEmail = email;
        this.customerWallet = wallet;
        this.passwordHash = hashPassword(password);
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Double getCustomerWallet() {
        return customerWallet;
    }

    public void setCustomerWallet(Double wallet) {
        this.customerWallet = wallet;
    }

    public void updateCustomerWallet(Double wallet) {
        this.customerWallet += wallet;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public boolean checkPassword(String password) {
        String hashedPassword = hashPassword(password);
        return this.passwordHash.equals(hashedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
