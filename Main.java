public class Main {

    public static void main(String[] args) {
        Room room1 = new Room(101, 500, false);
        Room room2 = new Room(102, 700, false);
        Room room3 = new Room(103, 900, false);

        Hotel hotel = new Hotel(
                "Ahmed Hotel", "Cairo", "01012345678", room1
        );

        hotel.addRoom(room2);
        hotel.addRoom(room3);

        PaymentGatway gateway = new PaymentGatway();
        Reservation reservation = new Reservation(room1, gateway);

        System.out.println("Reserved: " + reservation.reserve());

        System.out.println("Available rooms:");
        for (Room room : hotel.getAvailableRooms()) {
            System.out.println(
                    "Room " + room.getRoomNumber()
                    + " — Price: " + room.getPrice()
            );
        }
    }
}