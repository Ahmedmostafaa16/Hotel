package hotel.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Hotel {
    private final Map<Integer, Room> rooms = new LinkedHashMap<>();
    private final String name;
    private final String address;
    private final String telephone;

    public Hotel(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists.");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public Room getRoom(int roomNumber) { return rooms.get(roomNumber); }
    public void removeRoom(int roomNumber) { rooms.remove(roomNumber); }

    public Map<Integer, Room> getRooms() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(rooms));
    }
}
