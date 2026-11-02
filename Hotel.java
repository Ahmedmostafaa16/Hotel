
import java.util.ArrayList;  // i want to import arratlist 
public class Hotel {

    private ArrayList<Room> rooms = new ArrayList<>() ;
    private String name ;
    private String address ; 
    private String telephone ; 

    public Hotel(String name ,String address,String telephone,Room room){

        this.name = name ;
        this.address = address ;
        this.telephone = telephone ;
        rooms.add(room) ;


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
    public ArrayList<Room> getAvailableRooms(){
        ArrayList<Room> availableRooms = new ArrayList<>() ;

        for(Room room : rooms){
            if(!room.isRented()){

                availableRooms.add(room);           
            }
        }

        return availableRooms ;



    }
    public void addRoom(Room room){
        rooms.add(room) ;
    }
    


}