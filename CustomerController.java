package main.controllers;

import main.modal.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private static CustomerController instance;
    private List<Customer> customers;

    private CustomerController() {
        customers = new ArrayList<>();
    }

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer findCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public Customer findCustomerByName(String customerName) {
        for (Customer customer : customers) {
            if (customer.getCustomerName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public Customer loginCustomer(String email, String password) {
        for (Customer customer : customers) {
            if (customer.getCustomerEmail().equals(email) && customer.checkPassword(password)) {
                return customer;
            }
        }
        return null;
    }
}
