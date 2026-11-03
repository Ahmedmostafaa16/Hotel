public class Customers {

    private static int nextId=100 ;
    private int id ;
    private String name ;
    private String telephone ;
    private String email ;


    public Customers(String name ,String telephone,String email,int id){

        this.id = nextId++ ;
        this.name = name ;
        this.telephone = telephone ;
        this.email = email ;

    }

    public void setTelephone(String newTelephone){

        if(newTelephone == null){
            throw new IllegalArgumentException("telephone can't be null");
        }
        this.telephone = newTelephone ;

    }

    public String getName(){
        return name ;
    }
    public int getId(){

        return id ;
    }



    
}
