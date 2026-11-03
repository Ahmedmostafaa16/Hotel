package hotel.model;

public class Room {
    private final int roomNumber;
    private double price;
    private final RoomType type;

    public Room(int roomNumber, double price, RoomType type) {
        if (roomNumber <= 0 || type == null) {
            throw new IllegalArgumentException("Room number must be positive and type is required.");
        }
        this.roomNumber = roomNumber;
        setPrice(price);
        this.type = type;
    }

    public int getRoomNumber() { return roomNumber; }
    public double getPrice() { return price; }
    public RoomType getType() { return type; }

    public final void setPrice(double newPrice) {
        if (!Double.isFinite(newPrice) || newPrice < 0) {
            throw new IllegalArgumentException("Price must be finite and nonnegative.");
        }
        this.price = newPrice;
    }
}
