package Account.Schedule;

import java.time.*;
import Account.*;
import java.util.LinkedList;
import Category.*;

public class DaySchedule{

    public class TaskNode{
        TaskNode next;
        TaskNode prev;
        public Task containedTask;

        TaskNode(Task t){
            this.containedTask = t;
        }
    }

    public TaskNode head;
    public TaskNode tail;
    int count = 0;

    public void add(Task t){
        if(this.head == null){
            this.head = new TaskNode(new Task(t.getName(), t.getInformation(), t.getDeadline(), t.getTimeOfDay(), t.getDate(), t.getRecurrenceInterval(),t.getDuration(), t.getCategory()));
            this.tail = this.head;
        } else {
            if(t.getTimeOfDay().isAfter(this.head.containedTask.getTimeOfDay())){
                TaskNode curNode = this.head;
                boolean isPlaced = false;
                while(curNode.next != null && !isPlaced){
                    if(curNode.next.containedTask.getTimeOfDay().isAfter(t.getTimeOfDay())){
                        TaskNode temp = curNode.next;
                        curNode.next = new TaskNode(new Task(t.getName(), t.getInformation(), t.getDeadline(), t.getTimeOfDay(), t.getDate(), t.getRecurrenceInterval(),t.getDuration(), t.getCategory()));
                        curNode.next.next = temp;
                        temp.prev = curNode.next;
                        curNode.next.prev = curNode;
                        isPlaced = true;
                    }
                    curNode = curNode.next;
                }
                if(!isPlaced){
                    //place at the end of the list
                    curNode.next = new TaskNode(new Task(t.getName(), t.getInformation(), t.getDeadline(), t.getTimeOfDay(), t.getDate(), t.getRecurrenceInterval(),t.getDuration(), t.getCategory()));

                    curNode.next.prev = curNode;
                    this.tail = curNode.next;
                }

                

                //find place along the right side of head
            } else {
                //insert node to the left of the head and make it the new head
                this.head.prev = new TaskNode(new Task(t.getName(), t.getInformation(), t.getDeadline(), t.getTimeOfDay(), t.getDate(), t.getRecurrenceInterval(),t.getDuration(), t.getCategory()));
                this.head.prev.next = this.head;
                this.head = this.head.prev;

            }
        }

        this.count++;

    }

    public LocalTime[] getGapArray(LocalTime tomorrowStartTime, LocalTime yesterDayEndTime, boolean start, boolean end){
        LocalTime gapArray[] = new LocalTime[this.count + 1];
        
        
        //find first gap in schedule
        if(start){
            gapArray[0] = LocalTime.MAX;
        } else if (!start && !end){
            //find first gap in schedule using yesterday's end time
            LocalTime timeUntilMidnight = TimeCalculator.subtract(LocalTime.of(23, 59), yesterDayEndTime);
            LocalTime timeUntilFirstTask = this.head.containedTask.getTimeOfDay();
            LocalTime firstGap = TimeCalculator.add(timeUntilFirstTask, timeUntilMidnight);
            gapArray[0] = firstGap;
            //find intermediate gaps
            TaskNode curTaskNode = this.head;
            int index = 1;
            while(this.head.next != null){
                LocalTime startTimeOfNextTask = this.head.next.containedTask.getTimeOfDay();
                LocalTime endTimeOfCurTask = TimeCalculator.add(curTaskNode.containedTask.getTimeOfDay(), curTaskNode.containedTask.getDuration());
                LocalTime gap = TimeCalculator.subtract(startTimeOfNextTask, endTimeOfCurTask);
                gapArray[index] = gap;
            }

            //find end gap
            timeUntilMidnight = TimeCalculator.subtract(LocalTime.of(23, 59), TimeCalculator.add(curTaskNode.containedTask.getTimeOfDay(), curTaskNode.containedTask.getDuration()));
            timeUntilFirstTask = tomorrowStartTime;
            LocalTime endGap = TimeCalculator.add(timeUntilMidnight, timeUntilFirstTask);
            gapArray[gapArray.length - 1] = endGap;
            
        } else {
            //find first gap in schedule using yesterday's end time
            //find start, and then all the intermediates, with plenty of end time
            //find first gap in schedule using yesterday's end time
            LocalTime timeUntilMidnight = TimeCalculator.subtract(LocalTime.of(23, 59), yesterDayEndTime);
            LocalTime timeUntilFirstTask = this.head.containedTask.getTimeOfDay();
            LocalTime firstGap = TimeCalculator.add(timeUntilFirstTask, timeUntilMidnight);
            gapArray[0] = firstGap;
            //find intermediate gaps
            TaskNode curTaskNode = this.head;
            int index = 1;
            while(this.head.next != null){
                LocalTime startTimeOfNextTask = this.head.next.containedTask.getTimeOfDay();
                LocalTime endTimeOfCurTask = TimeCalculator.add(curTaskNode.containedTask.getTimeOfDay(), curTaskNode.containedTask.getDuration());
                LocalTime gap = TimeCalculator.subtract(startTimeOfNextTask, endTimeOfCurTask);
                gapArray[index] = gap;
                index++;
            }

            //find end gap
            LocalTime endGap = LocalTime.MAX;
            gapArray[gapArray.length + 1] = endGap;
        }
        

        
        

        return gapArray;
    }

    public void printList(){
        if(this.head == null){
            System.out.println("This list is empty");
        } else {
            TaskNode curNode = this.head;
            while(curNode.next != null){
                printTask(curNode);
                curNode = curNode.next;
            }
            printTask(curNode);
        }
    }

    public void printTask(TaskNode t){
        System.out.println();
        System.out.println("Name: " + t.containedTask.getName());
        System.out.println("Information: " + t.containedTask.getInformation());
        System.out.println("Deadline: " + t.containedTask.getDeadline());
        System.out.println("Time of day: " + t.containedTask.getTimeOfDay());
        System.out.println("Duration: " + t.containedTask.getDuration());
        System.out.println("Recurrance Interval: " + t.containedTask.getRecurrenceInterval());
        System.out.println("Category: Name: " + t.containedTask.getCategory().getCategoryName());
    }

    public String compareDaySchedule(DaySchedule otherSchedule){
        String result = "";
        TaskNode curNode = this.head;
        if(this.head == null){
            return "All Day Compatibility";
        }
        while(curNode.next != null){
            otherSchedule.add(curNode.containedTask);
            curNode = curNode.next;
        }
        otherSchedule.add(curNode.containedTask);



        curNode = otherSchedule.head;
        if(curNode.containedTask.getTimeOfDay().isAfter(LocalTime.MIN)){
            LocalTime starttime = LocalTime.MIN;
            LocalTime endtime = curNode.containedTask.getTimeOfDay();
            result += starttime + " to " + endtime + "\n";
        }

        while(curNode.next != null){
            LocalTime endTimeOfCurTask = TimeCalculator.add(curNode.containedTask.getTimeOfDay(), curNode.containedTask.getDuration());
            if(endTimeOfCurTask.isBefore(curNode.next.containedTask.getTimeOfDay())){
                result += endTimeOfCurTask + " to " + curNode.next.containedTask.getTimeOfDay() + "\n";
            }
        }

        LocalTime endOfDay = TimeCalculator.add(curNode.containedTask.getTimeOfDay(), curNode.containedTask.getDuration());
        if(endOfDay.isBefore(LocalTime.MAX)){
            result += endOfDay + " to " + LocalTime.MAX + "\n";
        }


        return result;
    }

    public LinkedList<Task> getRemainingTasksForTheDay(){
        LinkedList<Task> tasks = new LinkedList<Task>();
        TaskNode curNode = this.head;
        
        while(curNode.next != null){
            if(curNode.containedTask.getTimeOfDay().isAfter(LocalTime.now())){
                tasks.add(curNode.containedTask);
                curNode = curNode.next;
            }

        }
        if(curNode.containedTask.getTimeOfDay().isAfter(LocalTime.now())){
            tasks.add(curNode.containedTask);
        }

        return tasks;

    }

    public String analyze(LocalTime starttimeOfNextDayTask, LocalTime endTimeOfLastDay, boolean start, boolean end){

        String overlappingAnalysis = "";

        TaskNode curTaskNode = this.head;

        while(this.head.next != null){
            Task curTask = curTaskNode.containedTask;
            Task nextTask = curTaskNode.next.containedTask;

            LocalTime endTimeOfCurTask = curTask.getTimeOfDay().plusHours(curTask.getDuration().getHour()).plusMinutes(curTask.getDuration().getMinute());

            if(endTimeOfCurTask.isAfter(nextTask.getTimeOfDay())){
                overlappingAnalysis += "Task '" + curTask.getName() + "' ends at " + endTimeOfCurTask  + ", but Task '" + nextTask.getName() + "' starts at " + nextTask.getTimeOfDay();
            }

        }
        //perform overlapping analysis
        if(overlappingAnalysis.equals("")){
            
            // public LocalTime[] getGapArray(LocalTime tomorrowStartTime, LocalTime yesterDayEndTime, boolean start, boolean end, boolean findGapForComparison){

            LocalTime gapArray[] = getGapArray(starttimeOfNextDayTask, endTimeOfLastDay, start, end);

            boolean sleepingWindowFound = false;
            for(int i = 0; i < gapArray.length; i++){
                LocalTime adequateSleepDuration = LocalTime.of(7,30);
                if(gapArray[i].isAfter(adequateSleepDuration) || gapArray[i].equals(adequateSleepDuration)){
                    sleepingWindowFound = true;
                    gapArray[i] = TimeCalculator.subtract(gapArray[i], adequateSleepDuration);
                    break;
                }
            }

            if(!sleepingWindowFound){
                //this means that the eating test has failed
                return "Not enough time in the 24-hour period to sleep.";
            } else {
                //perform eating analysis
                LocalTime totalFreeTime = LocalTime.of(0,0);
                for(int i = 0; i < gapArray.length; i++){
                    totalFreeTime = TimeCalculator.add(totalFreeTime, gapArray[i]);
                }
                LocalTime adequateAllDayEatingTime = LocalTime.of(0,30);
                if(totalFreeTime.isAfter(adequateAllDayEatingTime) || totalFreeTime.equals(adequateAllDayEatingTime)){
                    //eating analysis passed
                    //perform breaks analysis

                    LocalTime adequateBreakTime = LocalTime.of(0,15);

                    int adequateTimeCount = 0;
                    for(int i = 0; i < gapArray.length; i++){
                        if(gapArray[i].isAfter(adequateBreakTime) || gapArray[i].equals(adequateBreakTime)){
                            gapArray[i] = TimeCalculator.subtract(gapArray[i], adequateBreakTime);
                            adequateTimeCount++;
                        }
                    }

                    if(adequateTimeCount > 1){
                        return "There is ample time for you to take two uninterrupted 15-minute breaks today. Enjoy!";
                    } else if (adequateTimeCount == 1){
                        return "There is ample time for you to take your first uninterrupted 15-minute break today, but not enough time for you to take your second.";
                    } else {
                        return "There is not enough time throughout the day for you to take any uninterrupted 15 minute breaks. Be sure to allocate time for your two uninterrupted 15-minute breaks.";
                    }

                } else {
                    return "There is not enough time in the 24-hour period for you to eat enough food";
                }
            }

        } else {
            return overlappingAnalysis;
        }
        
    }

}
