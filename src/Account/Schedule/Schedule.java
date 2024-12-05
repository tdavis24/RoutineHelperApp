package Account.Schedule;

import java.time.*;

public class Schedule {
    Day[] schedule;

    LocalDate startdate;

    int numDays;

    LocalDate enddate;

    Schedule(int numDaysInit){
        this.numDays = numDaysInit;
        this.schedule = new Day[this.numDays];
        
    }

    
}
