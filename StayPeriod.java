import java.time.LocalDate ;
import java.time.temporal.ChronoUnit ;
public class StayPeriod {

    private LocalDate startTime ;
    private LocalDate endTime ;

    public StayPeriod(LocalDate startTime,LocalDate endTime){

        this.startTime = startTime ;
        this.endTime = endTime ;

    }

    public LocalDate getStartTime(){

        return startTime ;
    }
    public LocalDate getEndTime(){

        return endTime ;
    }


    public int getNumberOfNights(){

        return (int) ChronoUnit.DAYS.between(startTime, endTime) ;
    }

    public boolean checkOverlap(StayPeriod otherPeriod) {
        return !(this.endTime.isBefore(otherPeriod.startTime) || this.startTime.isAfter(otherPeriod.endTime));
    }


    
}
