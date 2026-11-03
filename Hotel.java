
import java.util.ArrayList;  // i want to import arratlist 
import java.util.Map;
import java.util.HashMap; 
public class Hotel {

    private Map<Integer, Room> rooms = new HashMap<>() ; // i want to use map to store the rooms with their room number as key
    private String name ;
    private String address ; 
    private String telephone ; 
    private StayPeriod stayPeriod ;

    public Hotel(String name ,String address,String telephone,Room room){

        this.name = name ;
        this.address = address ;
        this.telephone = telephone ;

        addRoom(room) ; // add the room to the map with its room number as key


    }

    public String getName() {
        return name ;
    }

    public String getAddress() {
        return address ; 
    }
    public String getTelephone() {
        return telephone;
    }


    public void addRoom(Room room){

        try {
            if (room == null) {
                throw new IllegalArgumentException("Room cannot be null.");
            }
            if (rooms.containsKey(room.getRoomNumber())) {
                throw new IllegalArgumentException("Room number already exists.");
            }
            rooms.put(room.getRoomNumber(), room);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
    }
    public Room getRoom(int roomNumber){
        return rooms.get(roomNumber) ;
    }
    public void removeRoom(int roomNumber){
        rooms.remove(roomNumber) ;
    }

    public ArrayList<Room> getAvailableRooms(StayPeriod period) {
        ArrayList<Room> availableRooms = new ArrayList<>() ;
        for (Room room : rooms.values()) {
            if (!room.isRented() && !room.getStayPeriod().checkOverlap(period)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
        
    
    


}