public class Reservation {

    private double amount;
    private Room room;
    private PaymentGatway paymentGateway;

    public Reservation(Room room, PaymentGatway paymentGateway) {
        this.amount = room.getPrice();
        this.room = room;
        this.paymentGateway = paymentGateway;
    }

    public boolean reserve() {
        if (room.isRented()) {
            return false;
        }

        boolean succeeded = paymentGateway.charge(amount);

        if (succeeded) {
            room.rent();
            return true;
        }

        return false;
    }
}