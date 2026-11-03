public class Reservation {

    private static int nextId = 500 ;
    private int id ;
    private Room room;
    private ReservationStatus status;
    private StayPeriod stay ;

    private Customers customer ;

    public Reservation(Room room, StayPeriod stay, Customers customer) {

        this.id = nextId++; 
        this.room = room;
        this.status = ReservationStatus.PENDING;
        this.stay = stay ;
        this.customer = customer ;

    }

    public double getTotal(){

        return room.getPrice() * stay.getNumberOfNights() ;
    }

    
    public void chceckIn(){
        status = ReservationStatus.CHECKED_IN ;
    }

    public void checkOut(){

            status=ReservationStatus.CHECKED_OUT ;

    }
    public void cancel(){
        if(status == ReservationStatus.CHECKED_OUT || status == ReservationStatus.CHECKED_IN){
            throw new IllegalStateException("Cannot cancel a reservation that has already been checked in or checked out.");
        }
        status = ReservationStatus.CANCELLED ;
    }

    /* public boolean reserve() {
        if (room.isRented()) {
            return false;
        }

        double deposit = getTotal() / 10;

        boolean succeeded = paymentGateway.charge(deposit);

        if (succeeded) {
            room.rent();
            this.status = ReservationStatus.CONFIRMED;
            return true;
        }

        return false;
    }
 */
    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }


    public ReservationStatus getStatus() {
        return status;
    }

    public StayPeriod getStay() {
        return stay;
    }

    public Customers getCustomer() {
        return customer;
    }




}