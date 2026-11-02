public class PaymentGatway {

    public boolean charge(double amount){

        if(!Double.isFinite(amount) || amount <= 0){
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        // logic to charge the amount
        return true ; // assuming the payment is successful
    }
    
}
