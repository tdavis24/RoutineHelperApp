package Account.Schedule;

import java.time.*;

import Account.Task;

public class Schedule {
    private Day[] schedule;

    private LocalDate startdate;

    private int numDays;

    private LocalDate enddate;

    public Schedule(int numDaysInit){
        this.numDays = numDaysInit;
        this.schedule = new Day[this.numDays];
    }

    public void addTask(Task t){
        String noRecurrence = "Not recurring";
        String dailyRecurrence = "Daily";
        String weeklyRecurrence = "Weekly";
        String monthlyRecurrence = "Monthly";
        String yearlyRecurrence = "Yearly";
        if(t.getRecurrenceInterval().equals(noRecurrence) ){
            //check if the task is within time frame of the schedule
            if(t.getDate().isAfter(this.startdate) && t.getDate().isBefore(this.enddate)){
                //find the index of the day in the schedule
                int scheduleIndex = getIndexFromDate(t.getDate());
                this.schedule[scheduleIndex].getDaySchedule().add(t);
            }
        } else if(t.getRecurrenceInterval().equals(dailyRecurrence)) {
            
            //if date is between ranges, calculate the index, and then loop through until the end of the schedule
            if(t.getDate().isAfter(this.startdate) && t.getDate().isBefore(this.enddate)){
                int index = getIndexFromDate(t.getDate());
                for(int i = index; i < this.numDays; i++){
                    this.schedule[i].addTask(t);
                }
            } else if (t.getDate().isBefore(this.startdate)){
                for(int i = 0; i < this.numDays; i++){
                    this.schedule[i].addTask(t);
                }
            }
           
        } else if (t.getRecurrenceInterval().equals(weeklyRecurrence)){
            //if task occurs before the startdate, use temp date to loop until date is in the schedule
            if(t.getDate().isBefore(this.startdate)){
                LocalDate temp = t.getDate();
                while(temp.isBefore(this.enddate)){
                    temp = temp.plusWeeks(1);
                    if(temp.isAfter(this.startdate) && temp.isBefore(this.enddate)){
                        int index = getIndexFromDate(temp);
                        this.schedule[index].addTask(t);
                    }
                }
                
            } else if (t.getDate().isAfter(this.startdate) && t.getDate().isBefore(this.enddate)){
                int index = getIndexFromDate(t.getDate());

                this.schedule[index].addTask(t);

                for(int i = index + 7; index < this.numDays; i+=7){
                    this.schedule[i].addTask(t);
                }
            }
        } else if (t.getRecurrenceInterval().equals(monthlyRecurrence)){
            //if task occurs before the startdate, use temp date to loop until date is in the schedule
            if(t.getDate().isBefore(this.startdate)){
                LocalDate temp = t.getDate();
                while(temp.isBefore(this.enddate)){
                    temp = temp.plusMonths(1);
                    if(temp.isAfter(this.startdate) && temp.isBefore(this.enddate)){
                        int index = getIndexFromDate(temp);
                        this.schedule[index].addTask(t);
                    }
                }
                
            } else if (t.getDate().isAfter(this.startdate) && t.getDate().isBefore(this.enddate)){

                LocalDate temp = t.getDate();
                int index = getIndexFromDate(temp);
                while(temp.isBefore(this.enddate)){ 
                    index = getIndexFromDate(temp);
                    this.schedule[index].addTask(t);
                    temp = temp.plusMonths(1);
                }


            }
        } else if (t.getRecurrenceInterval().equals(yearlyRecurrence)){
            //if task occurs before the startdate, use temp date to loop until date is in the schedule
            if(t.getDate().isBefore(this.startdate)){
                LocalDate temp = t.getDate();
                while(temp.isBefore(this.enddate)){
                    temp = temp.plusYears(1);
                    if(temp.isAfter(this.startdate) && temp.isBefore(this.enddate)){
                        int index = getIndexFromDate(temp);
                        this.schedule[index].addTask(t);
                    }
                }
                
            } else if (t.getDate().isAfter(this.startdate) && t.getDate().isBefore(this.enddate)){

                LocalDate temp = t.getDate();
                while(temp.isBefore(this.enddate)){ 
                    int index = getIndexFromDate(temp);
                    this.schedule[index].addTask(t);
                    temp = temp.plusYears(1);
                }


            }
        }
    }

    private int getIndexFromDate(LocalDate date){
        LocalDate temp = this.startdate;
        int index = 0;
        boolean indexFound = false;
        while(temp.isBefore(this.enddate)){
            if(temp.equals(date)){
                indexFound = true;
                return index;
            }
            temp = temp.plusDays(1);
            
            index++;
        }

        if(!indexFound){
            return -1;
        } else {
            return index;
        }
        
    }

    

    public void display(){
        LocalDate temp = this.startdate;
        for(int i = 0; temp.isBefore(this.enddate); i++){
            temp = temp.plusDays(1);
            System.out.println("Printing schedule for " + temp);
            if(this.schedule[i].getDaySchedule().count > 0){
                this.schedule[i].getDaySchedule().printList();
            } else {
                System.out.println("Nothing scheduled for " + temp);
            }
        }
    }

    public Day[] getSchedule(){
        return this.schedule;
    }
    
}
