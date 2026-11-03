package hotel;

import hotel.model.Customer;
import hotel.model.Hotel;
import hotel.model.Reservation;
import hotel.model.ReservationStatus;
import hotel.model.Room;
import hotel.model.RoomType;
import hotel.model.StayPeriod;
import hotel.service.CustomerService;
import hotel.service.ReservationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    private static int checks;

    public static void main(String[] args) {
        Hotel hotel = new Hotel("Nile Hotel", "Cairo", "0200000000");
        Room single = new Room(101, 100, RoomType.SINGLE);
        Room doubleRoom = new Room(102, 150, RoomType.DOUBLE);
        Room suite = new Room(103, 250, RoomType.SUITE);
        hotel.addRoom(single);
        hotel.addRoom(doubleRoom);
        hotel.addRoom(suite);

        CustomerService customers = new CustomerService();
        Customer ahmed = new Customer("Ahmed", "01000000000", "ahmed@example.com");
        Customer sara = new Customer("Sara", "01100000000", "sara@example.com");
        customers.addCustomer(ahmed);
        customers.addCustomer(sara);
        check(customers.findCustomerById(ahmed.getId()) == ahmed
                && customers.findCustomerById(sara.getId()) == sara
                && ahmed.getId() != sara.getId(), "Two customers registered with unique IDs");

        ReservationService reservations = new ReservationService(hotel);
        StayPeriod stay = period(10, 13);
        StayPeriod overlap = period(12, 14);
        StayPeriod adjacent = period(13, 15);
        check(stay.getNumberOfNights() == 3, "Three nights calculated");
        check(reservations.reserve(101, ahmed, stay), "First reservation succeeds");
        Reservation first = reservations.getReservationsForCustomer(ahmed).get(0);
        check(first.getStatus() == ReservationStatus.CONFIRMED
                && reservations.findReservationById(first.getId()) == first,
                "Reservation is immediately confirmed and can be found by ID");
        check(!reservations.reserve(101, sara, overlap), "Overlapping booking rejected");
        check(reservations.reserve(101, sara, adjacent), "Adjacent booking accepted");
        check(reservations.reserve(102, sara, stay), "Same dates accepted for another room");
        check(reservations.getAvailableRooms(stay).equals(List.of(suite)),
                "Only room 103 is available for the original dates");

        single.setPrice(180);
        check(first.getTotal() == 300, "Agreed total remains 300 after price change");
        expect(IllegalStateException.class, () -> reservations.checkOut(first.getId()),
                "Checkout before check-in rejected");
        List<Reservation> snapshot = reservations.getReservationsForCustomer(ahmed);
        reservations.cancel(first.getId());
        check(first.getStatus() == ReservationStatus.CANCELLED
                && reservations.isRoomAvailable(101, stay), "Cancellation releases dates");
        expect(IllegalStateException.class, () -> reservations.cancel(first.getId()),
                "Repeated cancellation rejected");
        expect(IllegalStateException.class, () -> reservations.checkIn(first.getId()),
                "Check-in after cancellation rejected");
        check(reservations.reserve(101, ahmed, stay), "Released dates can be booked again");
        check(snapshot.size() == 1, "Customer reservation snapshot does not grow");
        Reservation replacement = reservations.getReservationsForCustomer(ahmed).get(1);
        check(replacement.getTotal() == 540, "New booking uses updated nightly price");
        reservations.checkIn(replacement.getId());
        check(replacement.getStatus() == ReservationStatus.CHECKED_IN
                && !reservations.isRoomAvailable(101, stay), "Checked-in reservation blocks dates");
        expect(IllegalStateException.class, () -> reservations.checkIn(replacement.getId()),
                "Repeated check-in rejected");
        expect(IllegalStateException.class, () -> reservations.cancel(replacement.getId()),
                "Cancellation after check-in rejected");
        reservations.checkOut(replacement.getId());
        check(replacement.getStatus() == ReservationStatus.CHECKED_OUT
                && reservations.isRoomAvailable(101, stay), "Checkout releases dates");
        expect(IllegalStateException.class, () -> reservations.checkOut(replacement.getId()),
                "Repeated checkout rejected");
        expect(IllegalStateException.class, () -> reservations.checkIn(replacement.getId()),
                "Check-in after checkout rejected");
        expect(IllegalStateException.class, () -> reservations.cancel(replacement.getId()),
                "Cancellation after checkout rejected");
        expect(IllegalStateException.class, () -> reservations.checkOut(first.getId()),
                "Checkout after cancellation rejected");

        expect(IllegalArgumentException.class, () -> period(10, 10), "Equal dates rejected");
        expect(IllegalArgumentException.class, () -> period(13, 10), "Reversed dates rejected");
        expect(IllegalArgumentException.class, () -> new StayPeriod(null, LocalDate.of(2027, 1, 13)),
                "Null check-in rejected");
        expect(IllegalArgumentException.class, () -> new StayPeriod(LocalDate.of(2027, 1, 10), null),
                "Null checkout rejected");
        expect(IllegalArgumentException.class, () -> hotel.addRoom(new Room(101, 1, RoomType.SINGLE)),
                "Duplicate room number rejected");
        expect(IllegalArgumentException.class, () -> hotel.addRoom(null), "Null room rejected");
        expect(IllegalArgumentException.class, () -> new Room(0, 1, RoomType.SINGLE),
                "Nonpositive room number rejected");
        expect(IllegalArgumentException.class, () -> new Room(104, 1, null), "Null room type rejected");
        for (double invalidPrice : new double[] {-1, Double.NaN, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY}) {
            expect(IllegalArgumentException.class, () -> new Room(104, invalidPrice, RoomType.SINGLE),
                    "Invalid constructor price rejected: " + invalidPrice);
            expect(IllegalArgumentException.class, () -> single.setPrice(invalidPrice),
                    "Invalid price update rejected: " + invalidPrice);
        }
        check(new Room(104, 0, RoomType.SINGLE).getPrice() == 0, "Zero nightly price accepted");
        expect(IllegalArgumentException.class, () -> new Customer(null, "123", "a@b"),
                "Null customer name rejected");
        expect(IllegalArgumentException.class, () -> new Customer(" ", "123", "a@b"),
                "Blank customer name rejected");
        expect(IllegalArgumentException.class, () -> new Customer("A", null, "a@b"),
                "Null phone rejected");
        expect(IllegalArgumentException.class, () -> ahmed.setTelephone(" "), "Blank phone rejected");
        expect(IllegalArgumentException.class, () -> new Customer("A", "123", null),
                "Null email rejected");
        expect(IllegalArgumentException.class, () -> new Customer("A", "123", " "),
                "Blank email rejected");
        expect(IllegalArgumentException.class, () -> customers.addCustomer(ahmed),
                "Duplicate customer registration rejected");
        expect(IllegalArgumentException.class, () -> customers.addCustomer(null),
                "Null customer registration rejected");
        expect(IllegalArgumentException.class, () -> new ReservationService(null), "Null hotel rejected");
        expect(IllegalArgumentException.class, () -> reservations.reserve(999, ahmed, stay),
                "Unknown room rejected");
        expect(IllegalArgumentException.class, () -> reservations.reserve(101, null, stay),
                "Null booking customer rejected");
        expect(IllegalArgumentException.class, () -> reservations.reserve(101, ahmed, null),
                "Null booking stay rejected");
        expect(IllegalArgumentException.class, () -> reservations.getAvailableRooms(null),
                "Null availability stay rejected");
        expect(IllegalArgumentException.class, () -> reservations.getReservationsForCustomer(null),
                "Null customer query rejected");
        expect(IllegalArgumentException.class, () -> reservations.cancel(-1), "Unknown cancellation ID rejected");
        expect(IllegalArgumentException.class, () -> reservations.checkIn(-1), "Unknown check-in ID rejected");
        expect(IllegalArgumentException.class, () -> reservations.checkOut(-1), "Unknown checkout ID rejected");
        expect(IllegalArgumentException.class, () -> new Reservation(null, stay, ahmed),
                "Null reservation room rejected");
        expect(IllegalArgumentException.class, () -> new Reservation(single, null, ahmed),
                "Null reservation stay rejected");
        expect(IllegalArgumentException.class, () -> new Reservation(single, stay, null),
                "Null reservation customer rejected");
        expect(IllegalArgumentException.class, () -> new Reservation(
                new Room(104, Double.MAX_VALUE, RoomType.SINGLE), stay, ahmed),
                "Overflowing reservation total rejected");
        expect(IllegalArgumentException.class, () -> stay.checkOverlap(null), "Null overlap stay rejected");
        check(!stay.checkOverlap(adjacent) && !adjacent.checkOverlap(stay),
                "Adjacent stays do not overlap in either direction");
        check(stay.checkOverlap(stay) && stay.checkOverlap(overlap) && overlap.checkOverlap(stay),
                "Identical and partially overlapping stays detected");
        Map<Integer, Room> roomSnapshot = hotel.getRooms();
        expect(UnsupportedOperationException.class, () -> roomSnapshot.clear(), "Room snapshot is read-only");
        expect(UnsupportedOperationException.class, () -> snapshot.clear(), "Customer snapshot is read-only");
        List<Room> available = reservations.getAvailableRooms(stay);
        available.clear();
        check(reservations.getAvailableRooms(stay).equals(List.of(single, suite)),
                "Changing search results does not change hotel rooms");
        hotel.removeRoom(103);
        check(roomSnapshot.size() == 3 && hotel.getRooms().size() == 2,
                "Room snapshot is independent of later collection changes");
        System.out.println("All " + checks + " verification checks passed.");
    }

    private static StayPeriod period(int startDay, int endDay) {
        return new StayPeriod(LocalDate.of(2027, 1, startDay), LocalDate.of(2027, 1, endDay));
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
        checks++;
        System.out.println("PASS: " + description);
    }

    private static void expect(Class<? extends RuntimeException> expected, Runnable action,
            String description) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            if (!expected.isInstance(exception)) {
                throw new AssertionError("FAILED: " + description + " (wrong exception)", exception);
            }
            check(true, description);
            return;
        }
        throw new AssertionError("FAILED: " + description + " (no exception)");
    }
}
