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
    @Before
    public void setup(){
        Category healthCategory = new Category("health", "idk");
        Task t1 = new Task("brush teeth", "brush teeth for 2 minutes", "deadline", LocalTime.of(10, 30), LocalDate.of(2024, 12, 9), "Daily", LocalTime.of(1,0), healthCategory);
        Schedule mySched = new Schedule(1);
        mySched.addTask(t1);
        String scheduleString = mySched.display();


    }

    @Test
    public void testSingularTaskDaily()
    {
        Assert.assertEquals(scheduleString, "Name: brush teeth\nInformation: brush teeth for 2 minutes\n" + 
        "Deadline: deadline\n" +
        "Time of Day: 10:30\n");
    }
}
