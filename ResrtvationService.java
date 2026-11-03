import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ResrtvationService {

    private final Hotel hotel;
    private final PaymentGatway paymentGateway;

    private final Map<Integer, Reservation> reservations = new HashMap<>();

    // Give the service its dependencies when creating it.
    public ResrtvationService(Hotel hotel,PaymentGatway paymentGateway) {
        if (hotel == null || paymentGateway == null) {
            throw new IllegalArgumentException(
                    "Hotel and payment gateway cannot be null."
            );
        }

        this.hotel = hotel;
        this.paymentGateway = paymentGateway;
    }

    public Reservation findReservationById(int id) {
        return reservations.get(id);
    }

    public boolean isRoomAvailable(int roomNumber,StayPeriod requestedStay) {
        if (requestedStay == null) {
            throw new IllegalArgumentException("Stay cannot be null.");
        }

        if (hotel.getRoom(roomNumber) == null) {
            throw new IllegalArgumentException("Room does not exist.");
        }

        for (Reservation reservation : reservations.values()) {

            boolean sameRoom = reservation.getRoom().getRoomNumber() == roomNumber;

            ReservationStatus status = reservation.getStatus();

            boolean blocksAvailability =
                    status == ReservationStatus.PENDING
                    || status == ReservationStatus.CONFIRMED
                    || status == ReservationStatus.CHECKED_IN;

            if (sameRoom&& blocksAvailability && reservation.getStay().checkOverlap(requestedStay)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Room> getAvailableRooms(StayPeriod requestedStay) {
        if(requestedStay == null) {
            throw new IllegalArgumentException("Stay cannot be null.");
        }
        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : hotel.getRooms().values()) {
            if (isRoomAvailable(room.getRoomNumber(), requestedStay)) {
                availableRooms.add(room);
            }
   
        }
        return availableRooms ; 
    }

    public boolean reserve(int roomNumber,Customers customer,StayPeriod stay) {
        if (customer == null || stay == null) {
            throw new IllegalArgumentException("Customer and stay cannot be null.");
        }

        Room room = hotel.getRoom(roomNumber);

        if (room == null) {
            throw new IllegalArgumentException("Room does not exist.");
        }

        if (!isRoomAvailable(roomNumber, stay)) {
            return false;
        }

        double totalCost =
                room.getPrice() * stay.getNumberOfNights();

        if (!paymentGateway.charge(totalCost)) {
            return false;
        }

        Reservation reservation = new Reservation(room,stay,customer,ReservationStatus.CONFIRMED );

        reservations.put(reservation.getId(), reservation);

        return true;
    }
}