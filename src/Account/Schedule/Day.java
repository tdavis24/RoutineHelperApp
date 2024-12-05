package Account.Schedule;

import java.time.*;

public class Day {
    LinkedList daySchedule; 
    LocalDate date; 
    
    Day(LocalDate dateInit){
        this.date = dateInit;
        this.daySchedule = new LinkedList();
    }

    public void addTask(Task t){
        this.daySchedule.add(t);
    }

    public void displayDaySchedule(){
        this.daySchedule.printList();
    }
}