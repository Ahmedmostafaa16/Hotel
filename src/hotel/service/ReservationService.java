package hotel.service;

import hotel.model.Customer;
import hotel.model.Hotel;
import hotel.model.Reservation;
import hotel.model.ReservationStatus;
import hotel.model.Room;
import hotel.model.StayPeriod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private final Hotel hotel;
    private final Map<Integer, Reservation> reservations = new LinkedHashMap<>();

    public ReservationService(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        this.hotel = hotel;
    }

    public Reservation findReservationById(int id) { return reservations.get(id); }

    public List<Reservation> getReservationsForCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCustomer().getId() == customer.getId()) {
                result.add(reservation);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public boolean isRoomAvailable(int roomNumber, StayPeriod requestedStay) {
        if (requestedStay == null) {
            throw new IllegalArgumentException("Stay cannot be null.");
        }
        if (hotel.getRoom(roomNumber) == null) {
            throw new IllegalArgumentException("Room does not exist.");
        }
        for (Reservation reservation : reservations.values()) {
            ReservationStatus status = reservation.getStatus();
            boolean active = status == ReservationStatus.CONFIRMED
                    || status == ReservationStatus.CHECKED_IN;
            if (reservation.getRoom().getRoomNumber() == roomNumber && active
                    && reservation.getStay().checkOverlap(requestedStay)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Room> getAvailableRooms(StayPeriod requestedStay) {
        if (requestedStay == null) {
            throw new IllegalArgumentException("Stay cannot be null.");
        }
        ArrayList<Room> available = new ArrayList<>();
        for (Room room : hotel.getRooms().values()) {
            if (isRoomAvailable(room.getRoomNumber(), requestedStay)) {
                available.add(room);
            }
        }
        return available;
    }

    public boolean reserve(int roomNumber, Customer customer, StayPeriod stay) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (!isRoomAvailable(roomNumber, stay)) {
            return false;
        }
        Reservation reservation = new Reservation(hotel.getRoom(roomNumber), stay, customer);
        reservations.put(reservation.getId(), reservation);
        return true;
    }

    private Reservation requireReservation(int id) {
        Reservation reservation = findReservationById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation does not exist: " + id);
        }
        return reservation;
    }

    public void cancel(int id) { requireReservation(id).cancel(); }
    public void checkIn(int id) { requireReservation(id).checkIn(); }
    public void checkOut(int id) { requireReservation(id).checkOut(); }
}
