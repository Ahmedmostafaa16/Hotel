import java.util.Map;
import java.util.HashMap ;
import java.util.ArrayList ;


public class ResrtvationService {

    private PaymentGatway paymentGateway ;
    private Hotel hotel ;
    private StayPeriod stayPeriod ;
    
    private Map<Integer, Reservation> reservations = new HashMap() ;

    public Reservation findReservationById(int id) {
        return reservations.get(id) ;
    }

    public ArrayList<Room> getAvailableRooms() {
        return hotel.getAvailableRooms(stayPeriod) ;
    }

    public boolean reserveRoom(Room room, StayPeriod stay, Customers customers,Reservation reservation,paymentGatway paymentGateway) {

        if (room.isAvailable(stay)) {
            double amount = reservation.getTotal() ;

            boolean succeeded = paymentGateway.charge(amount);

            if (succeeded) {
                room.rent();
                reservation.chceckIn();
                reservations.put(reservation.getId(), reservation);
                return true;
            }

        }



        // i need to check if the room is available for the given stay period



    }



    




    
    
}
