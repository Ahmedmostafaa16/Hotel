package hotel.model;

public class Customer {
    private static int nextId = 100;
    private final int id;
    private final String name;
    private String telephone;
    private final String email;

    public Customer(String name, String telephone, String email) {
        this.name = required(name, "Name");
        this.email = required(email, "Email");
        this.telephone = required(telephone, "Telephone");
        this.id = nextId++;
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be null or blank.");
        }
        return value;
    }

    public void setTelephone(String telephone) {
        this.telephone = required(telephone, "Telephone");
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
}
