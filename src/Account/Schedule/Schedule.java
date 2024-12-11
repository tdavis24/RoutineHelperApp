package Account.Schedule;

import java.time.*;
import Account.Task;
import java.util.ArrayList;
import Category.Category;
import Account.Schedule.DaySchedule.TaskNode;

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

    public String display(){
        LocalDate temp = this.startdate;
        String scheduleString = "";
        for(int i = 0; temp.isBefore(this.enddate); i++){
            temp = temp.plusDays(1);
            System.out.println("Printing schedule for " + temp);
            if(this.schedule[i].getDaySchedule().count > 0){
                scheduleString += this.schedule[i].getDaySchedule().printList();
            } else {
                System.out.println("Nothing scheduled for " + temp);
            }
        }
        return scheduleString;
    }

    public Day[] getSchedule(){
        return this.schedule;
    }

    private int search(String categoryName, ArrayList<String> categoryNames){
        for(int i = 0; i < categoryNames.size(); i++){
            if(categoryNames.get(i).equals(categoryName)){
                return i;
                
            }
        }
        return -1;
        
    }

    public String generateScheduleStatistics(){
        ArrayList<String> categoryNames = new ArrayList<String>();

        ArrayList<Integer> minutesSpentInEachCategory = new ArrayList<Integer>();
        int totalMinutes = 0;
        for(int i = 0; i < this.schedule.length; i++){
            TaskNode curNode = this.schedule[i].getDaySchedule().head;
            if(curNode != null){
                while(curNode.next != null){
                    Category curCategory = curNode.containedTask.getCategory();
                    String curCategoryName = curCategory.getCategoryName();
                    LocalTime duration = curNode.containedTask.getDuration();
                    int hoursSpentInTask = duration.getHour();
                    int minuteSpentInTask = duration.getMinute();
                    
                    int found = search(curCategoryName, categoryNames);
    
                    if(search(curCategoryName, categoryNames) == -1){
                        //we need to add the category to the category list and the minute spent in each category
                        categoryNames.add(curCategoryName);
                        minutesSpentInEachCategory.add((hoursSpentInTask * 60) + minuteSpentInTask);
    
                    } else {
                        minutesSpentInEachCategory.set(found, minutesSpentInEachCategory.get(found) + (hoursSpentInTask * 60) + minuteSpentInTask);
                    }
                    curNode = curNode.next;
                }
                categoryNames.add(curNode.containedTask.getCategory().getCategoryName());
                int hoursSpentInCategory = curNode.containedTask.getDuration().getHour();
                int minutesSpentInCategory = curNode.containedTask.getDuration().getMinute();
                minutesSpentInEachCategory.set(0, minutesSpentInEachCategory.get(0) +(hoursSpentInCategory * 60) + minutesSpentInCategory);
                
            }


        }

        //we want the following statistics: amount of time spent in each category, amount of time spent doing any type of task in general, 
        //we want the percentage of time spent doing tasks, percentage of that time in each category 

        //amount of time spent in each category
        String statLine = "";

        int hourComponent;
        int minuteComponent;

        for(int i = 0; i < categoryNames.size(); i++){
            String categoryName = categoryNames.get(i);
            hourComponent = minutesSpentInEachCategory.get(i) / 60;
            minuteComponent = minutesSpentInEachCategory.get(i) % 60;
            statLine += "You spent a total of " + hourComponent + " hours and " + minuteComponent + " minutes doing tasks related to " + categoryName +"\n.";
        }

        //amount of time doing any task in general
        int overallTaskMinutes = 0;
        for(int i = 0; i < minutesSpentInEachCategory.size(); i++){
            overallTaskMinutes += minutesSpentInEachCategory.get(i);
        }

        hourComponent = totalMinutes / 60;
        minuteComponent = totalMinutes % 60;

        statLine += "You spent a total of " + hourComponent + " hours and " + minuteComponent + " minutes doing any sort of task.\n";

        //average amount of time per day doing tasks
        double averageMinutesPerDayDoingTasks = overallTaskMinutes / this.schedule.length; 
        hourComponent = (int)averageMinutesPerDayDoingTasks / 60;
        minuteComponent = (int)averageMinutesPerDayDoingTasks % 60;
        statLine += "You spend an average of " + hourComponent + " hours and " + minuteComponent + " minutes per day doing tasks.\n";

        //percentage of time in each category
        for(int i = 0; i < categoryNames.size(); i++){
            String categoryName = categoryNames.get(i);
            int mins = minutesSpentInEachCategory.get(i); 
            double percentageOfTimeSpent = (mins / overallTaskMinutes) * 100; 
            statLine += "You spend " + percentageOfTimeSpent + "% of your time doing tasks related to " + categoryName + "\n";
        }

        //percentage of time doing tasks in general
        int totalScheduleMinutes = this.schedule.length * 1440;
        int remainingMinutes = totalMinutes - overallTaskMinutes;

        int remainingMinutesHourComponent = remainingMinutes / 60;
        int remainingMinutesMinuteComponent = remainingMinutes % 60;

        statLine += "You have a total of " + remainingMinutesHourComponent +" hours and " + remainingMinutesMinuteComponent + " minutes of free time in this schedule.\n";

        double percentageOfTimeSpent = (overallTaskMinutes / totalScheduleMinutes) * 100; 
        statLine += "You spend " + percentageOfTimeSpent + "% of your time doing any type of task\n.";


        return statLine;
        
    }
}
