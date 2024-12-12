package Test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Account.Task;
import Account.Schedule.*;
import Category.Category; 
import java.time.*;


public class ScheduleTest {
    Category healthCategory = null;
    Task t1 = null;
    Task t2 = null;
    Task t3 = null;
    Schedule mySched = null;
    Schedule mySched1 = null;
    Schedule mySched2 = null;
    Schedule mySched3 = null;
    @Before
    public void setup(){
        healthCategory = new Category("health", "idk");
        t1 = new Task("brush teeth", "brush teeth for 2 minutes", "deadline", LocalTime.of(10, 30), LocalDate.now(), "Daily", LocalTime.of(1,0), healthCategory);
        t2 = new Task("brush teeth", "brush teeth for 2 minutes", "deadline", LocalTime.of(10, 30), LocalDate.now(), "Daily", LocalTime.of(1,0), healthCategory);
        t3 = new Task("brush teeth", "brush teeth for 2 minutes", "deadline", LocalTime.of(10, 30), LocalDate.now(), "Weekly", LocalTime.of(1,0), healthCategory);

        mySched = new Schedule(1);
        mySched2 = new Schedule(3);
        mySched3 = new Schedule(8);
        mySched.addTask(t1);
        mySched2.addTask(t2);
        mySched3.addTask(t3);
    }

    @Test
    public void testSingularTaskDaily()
    {
        String scheduleString = mySched.display();

        Assert.assertEquals(scheduleString, 
        "\nPrinting schedule for " + LocalDate.now() + "\n" + 
        "Name: brush teeth\n" +
        "Information: brush teeth for 2 minutes\n" + 
        "Deadline: deadline\n" +
        "Time of Day: 10:30\n" + 
        "Duration: 1:00\n" + 
        "Recurrance Interval: Daily\n" +
        "Category Name: health\n"
        );
    }

    @Test 
    public void testDailyTaskThreeDaysDaily()
    {   
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = tomorrow.plusDays(1);
        String scheduleString2 = mySched2.display();

        Assert.assertEquals(scheduleString2, 
            "\nPrinting schedule for " + today + "\n" + 
            "Name: brush teeth\n" +
            "Information: brush teeth for 2 minutes\n" + 
            "Deadline: deadline\n" +
            "Time of Day: 10:30\n" + 
            "Duration: 1:00\n" + 
            "Recurrance Interval: Daily\n" +
            "Category Name: health\n" + 
            "\nPrinting schedule for " + tomorrow + 
            "Name: brush teeth\n" +
            "Information: brush teeth for 2 minutes\n" + 
            "Deadline: deadline\n" +
            "Time of Day: 10:30\n" + 
            "Duration: 1:00\n" + 
            "Recurrance Interval: Daily\n" +
            "Category Name: health\n" + 
            "\nPrinting schedule for " + dayAfterTomorrow + 
            "Name: brush teeth\n" +
            "Information: brush teeth for 2 minutes\n" + 
            "Deadline: deadline\n" +
            "Time of Day: 10:30\n" + 
            "Duration: 1:00\n" + 
            "Recurrance Interval: Daily\n" +
            "Category Name: health\n"
        );
    }

    @Test
    public void testWeeklyTask()
    {
        LocalDate today = LocalDate.now();
        String scheduleString = mySched.display();
        LocalDate[] weekArray = {today, today.plusDays(1), today.plusDays(2), today.plusDays(3), today.plusDays(4), today.plusDays(5), today.plusDays(6), today.plusDays(7)};
        Assert.assertEquals(scheduleString, 
            "\nPrinting schedule for " + today + "\n" + 
            "Name: brush teeth\n" +
            "Information: brush teeth for 2 minutes\n" + 
            "Deadline: deadline\n" +
            "Time of Day: 10:30\n" + 
            "Duration: 1:00\n" + 
            "Recurrance Interval: Daily\n" +
            "Category Name: health\n" +
            "Nothing scheduled for " + today.plusDays(1) + "\n" + 
            "Nothing scheduled for " + today.plusDays(2) + "\n" + 
            "Nothing scheduled for " + today.plusDays(3) + "\n" + 
            "Nothing scheduled for " + today.plusDays(4) + "\n" + 
            "Nothing scheduled for " + today.plusDays(5) + "\n" + 
            "Nothing scheduled for " + today.plusDays(6) + "\n" + 
            "\nPrinting schedule for " + today.plusDays(7) + "\n" + 
            "Name: brush teeth\n" +
            "Information: brush teeth for 2 minutes\n" + 
            "Deadline: deadline\n" +
            "Time of Day: 10:30\n" + 
            "Duration: 1:00\n" + 
            "Recurrance Interval: Daily\n" +
            "Category Name: health\n"
        );
    }

    @Test
    public void testSetReminderForRoutine()
    {
        // Create a new category
        Category exerciseCategory = new Category("Exercise", "Health");

        // Create a new task
        Task workoutTask = new Task("Morning Workout", "Run 5 kilometers", "2024-12-31", 
                                    LocalTime.of(7, 0), LocalDate.now(), "Daily", 
                                    LocalTime.of(1,0), exerciseCategory);

        // Add task to the schedule
        mySched.addTask(workoutTask);

        // Set a reminder for the task
        LocalDate reminderDate = LocalDate.now();
        LocalTime reminderTime = LocalTime.of(6, 30);
        workoutTask.setReminder(reminderTime, reminderDate);

        // Verify that the reminder is set correctly
        Assert.assertEquals("Reminder date should match.", reminderDate, workoutTask.getReminderDate());
        Assert.assertEquals("Reminder time should match.", reminderTime, workoutTask.getReminderTime());
    }
}