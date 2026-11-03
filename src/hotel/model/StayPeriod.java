package hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StayPeriod {
    private final LocalDate startTime;
    private final LocalDate endTime;

    public StayPeriod(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null || !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("Checkout must be strictly after check-in; dates are required.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getStartTime() { return startTime; }
    public LocalDate getEndTime() { return endTime; }
    public long getNumberOfNights() { return ChronoUnit.DAYS.between(startTime, endTime); }

    public boolean checkOverlap(StayPeriod otherPeriod) {
        if (otherPeriod == null) {
            throw new IllegalArgumentException("Other stay cannot be null.");
        }
        return startTime.isBefore(otherPeriod.endTime)
                && endTime.isAfter(otherPeriod.startTime);
    }
}
