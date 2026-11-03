public class Room {

    private final int roomNumber ;
    private double price ;
    private RoomType type ;


    public Room(int roomNumber,double price , RoomType type){ 
        this.roomNumber = roomNumber ;
        setPrice(price) ;
        this.type = type ;
    }

    public int getRoomNumber(){

        return roomNumber ;
    }

    public double getPrice(){

        return  price ;
    }
    public RoomType getType(){

        return type ;
    }
    public void setPrice(double newPrice) {
        if (newPrice < 0 || Double.isNaN(newPrice) || Double.isInfinite(newPrice)) {
            throw new IllegalArgumentException("Price cannot be negative or empty.");
        }
        this.price = newPrice ;

    } 




    
    
    
}


    

