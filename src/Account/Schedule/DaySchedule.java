package Account.Schedule;

import Account.Task;

public class DaySchedule{

    Task head;
    Task tail;
    int count;
    public int numItems = 0;

    public void add(Task t){
        if(this.head == null){
            this.head = new Task(t.name, t.category, t.date, t.timeOfDay, t.duration, t.recurranceInterval);
           
        } else {
            if(t.timeOfDay > this.head.timeOfDay){
                Task curNode = this.head;
                boolean isPlaced = false;
                while(curNode.next != null && !isPlaced){
                    if(curNode.next.timeOfDay > t.timeOfDay){
                        Task temp = curNode.next;
                        curNode.next = new Task(t.title, t.category, t.date, t.timeOfDay, t.duration, t.recurranceInterval);
                        curNode.next.next = temp;
                        temp.prev = curNode.next;
                        curNode.next.prev = curNode;
                        isPlaced = true;
                    }
                    curNode = curNode.next;
                }
                if(!isPlaced){
                    //place at the end of the list
                    curNode.next = new Task(t.title, t.category, t.date, t.timeOfDay, t.duration, t.recurranceInterval);

                    curNode.next.prev = curNode;
                }

                

                //find place along the right side of head
            } else {
                //insert node to the left of the head and make it the new head
                this.head.prev = new Task(t.title, t.category, t.date, t.timeOfDay, t.duration, t.recurranceInterval);
                this.head.prev.next = this.head;
                this.head = this.head.prev;

            }
        }

        this.numItems++;

    }

    public double[] getGapArray(double starttimeOfNextDayTask){
        Task curTask = this.head;
        double gapArray[] = new double[this.numItems];
        int index = 0;
        while(curTask.next != null){
            double endTimeOfCurTask = curTask.timeOfDay + curTask.duration;
            double startTimeOfNextTask = curTask.next.timeOfDay;
            double gap = startTimeOfNextTask - endTimeOfCurTask;
            gapArray[index] = gap;
            curTask = curTask.next;
            index++;
        }
        
        double endTimeOfLastTaskOfDay = curTask.timeOfDay + curTask.duration;
        double timeTilMidnight = 24 - endTimeOfLastTaskOfDay;
        double gap = timeTilMidnight + starttimeOfNextDayTask;
        gapArray[gapArray.length - 1] = gap; 
        for(int i = 0; i < this.numItems; i++){
            if(gapArray[i] < 0){
                gapArray[i] = 0;
            }
        }
        return gapArray;
    }

    public void printList(){
        if(this.head == null){
            System.out.println("This list is empty");
        } else {
            Task curNode = this.head;
            while(curNode.next != null){
                printTask(curNode);
                curNode = curNode.next;
            }
            printTask(curNode);
        }
    }

    public void printTask(Task t){
        System.out.println();
        System.out.println("Title: " + t.title);
        System.out.println("Category: " + t.category);
        System.out.println("Date: " + t.date);
        System.out.println("Time of day: " + t.timeOfDay);
        System.out.println("Duration: " + t.duration);
        System.out.println("Recurrance Interval: " + t.recurranceInterval);
    }


    public static void main(String args[]){

        LinkedList myList = new LinkedList();
        // String titleInit, String categoryInit, LocalDate localDateInit, double timeOfDayInit, double durationInit, String recurranceInterval
        Task t1 = new Task("Brush Teeth", "Health", LocalDate.of(2024, 11, 15), 21, 0.08333, "Daily");
        Task t2 = new Task("Go for a walk", "Health", LocalDate.of(2024, 11, 16), 8, 1, "Weekly");
        Task t3 = new Task("Pack lunch for work", "Chores", LocalDate.of(2024, 11, 15), 8, .33333, "Daily");
        Task t4 = new Task("Give speech at convention", "Events", LocalDate.of(2024, 12, 3), 15, 1.5, "No repeat");

        
    }
}
