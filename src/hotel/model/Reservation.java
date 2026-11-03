package hotel.model;

public class Reservation {
    private static int nextId = 500;
    private final int id;
    private final Room room;
    private final Customer customer;
    private final StayPeriod stay;
    private final double total;
    private ReservationStatus status;

    public Reservation(Room room, StayPeriod stay, Customer customer) {
        if (room == null || stay == null || customer == null) {
            throw new IllegalArgumentException("Room, stay, and customer are required.");
        }
        double agreedTotal = room.getPrice() * stay.getNumberOfNights();
        if (!Double.isFinite(agreedTotal)) {
            throw new IllegalArgumentException("Total must be finite.");
        }
        this.id = nextId++;
        this.room = room;
        this.customer = customer;
        this.stay = stay;
        this.total = agreedTotal;
        this.status = ReservationStatus.CONFIRMED;
    }

    private void requireStatus(ReservationStatus expected) {
        if (status != expected) {
            throw new IllegalStateException("Expected " + expected + " but reservation is " + status + ".");
        }
    }

    public void checkIn() {
        requireStatus(ReservationStatus.CONFIRMED);
        status = ReservationStatus.CHECKED_IN;
    }

    public void checkOut() {
        requireStatus(ReservationStatus.CHECKED_IN);
        status = ReservationStatus.CHECKED_OUT;
    }

    public void cancel() {
        requireStatus(ReservationStatus.CONFIRMED);
        status = ReservationStatus.CANCELLED;
    }

    public int getId() { return id; }
    public Room getRoom() { return room; }
    public Customer getCustomer() { return customer; }
    public StayPeriod getStay() { return stay; }
    public double getTotal() { return total; }
    public ReservationStatus getStatus() { return status; }
}
