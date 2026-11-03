package hotel.service;

import hotel.model.Customer;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private final Map<Integer, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer ID already exists.");
        }
        customers.put(customer.getId(), customer);
    }

    public Customer findCustomerById(int id) { return customers.get(id); }
}
