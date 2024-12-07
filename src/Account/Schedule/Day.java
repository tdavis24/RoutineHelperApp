package Account.Schedule;

import java.time.*;
import Account.Task;
public class Day {
    private DaySchedule daySchedule; 
    private LocalDate date; 
    
    Day(LocalDate dateInit){
        this.date = dateInit;
        this.daySchedule = new DaySchedule();
    }

    public void addTask(Task t){
        this.daySchedule.add(t);
    }

    public void displayDaySchedule(){
        this.daySchedule.printList();
    }

    public DaySchedule getDaySchedule(){
        return this.daySchedule;
    }

    public LocalDate getDate(){
        return this.date;
    }
}