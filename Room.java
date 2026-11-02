public class Room {

    private final int roomNumber ;
    private double price ;
    private boolean rented = false ;

    public Room(int roomNumber,double price ,boolean rented) {


        this.roomNumber = roomNumber ;
        this.price = price ;
        this.rented = rented ;
    }

    public int getRoomNumber(){

        return roomNumber ;
    }

    public double getPrice(){

        return  price ;
    }
    public void setPrice(double newPrice) {
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
    
}


    

