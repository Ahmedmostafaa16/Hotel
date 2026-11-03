public class Room {

    private final int roomNumber ;
    private double price ;
    private boolean rented = false ;
    private RoomType type ;
    private StayPeriod stayPeriod = new StayPeriod(null, null);


    public Room(int roomNumber,double price ,boolean rented, RoomType type, StayPeriod stayPeriod){ 
        this.roomNumber = roomNumber ;
        this.price = price ;
        this.rented = rented ;
        this.type = type ;
        this.stayPeriod = stayPeriod ;
    }

    public int getRoomNumber(){

        return roomNumber ;
    }

    public double getPrice(){

        return  price ;
    }
    public void setPrice(double newPrice) {
        if (newPrice < 0 || Double.isNaN(newPrice) || Double.isInfinite(newPrice)) {
            throw new IllegalArgumentException("Price cannot be negative or empty.");
        }
        this.price = newPrice ;

    } 

    public boolean isRented(){
        return rented ;
    }
    public void rent(){
        if(!rented){
            rented = true ;
        }
    }

    public void end(){

        if(rented){
            rented = false ;

        }
    }
    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }
    public void setStayPeriod(StayPeriod stayPeriod) {
        //stay period can not be past date 
        if(stayPeriod.getStartTime().isBefore(java.time.LocalDate.now()) || stayPeriod.getEndTime().isBefore(java.time.LocalDate.now())){
            throw new IllegalArgumentException("Stay period cannot be in the past.");
        }
    
        this.stayPeriod = stayPeriod;
    }

    public boolean isAvailable(StayPeriod period) {
        return !rented && !stayPeriod.checkOverlap(period);
    }
    
    
    
}


    

