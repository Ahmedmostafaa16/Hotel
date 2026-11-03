import java.util.Map;
import java.util.HashMap;

public class CustomerService {

    private final Map<Integer, Customers> customers = new HashMap<>();

    public void addCustomer(Customers customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer ID already exists.");
        }
        customers.put(customer.getId(), customer);
    }

    public Customers findCustomerById(int id) {
        return customers.get(id);
    }




    
}
