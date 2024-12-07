package Account.Schedule;

import java.time.*;

public class TimeCalculator {

    //preconditions: t1 occurs at a later time than t2
    public static LocalTime subtract(LocalTime t1, LocalTime t2){
        LocalTime difference = t1.minusHours(t2.getHour()).minusMinutes(t2.getMinute());
        return difference;
    }

    public static LocalTime add(LocalTime t1, LocalTime t2){
        LocalTime sum = t1.plusHours(t2.getHour()).plusMinutes(t2.getMinute());
        return sum;
    }
}
