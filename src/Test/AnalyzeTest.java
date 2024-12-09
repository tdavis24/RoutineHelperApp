package Test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Account.Task;
import Account.Schedule.*;
import Category.Category; 
import java.time.*;

public class AnalyzeTest {
    @Before
    public void setup(){
        //category for all cases
        Category codeCategory = new Category("Software Engineering", "Work");
        Task t1 = new Task("write code", "debug issues", "deadline", LocalTime.of(6, 0), LocalDate.now(), "Daily", LocalTime.of(17, 0), codeCategory);
        Schedule mySched = new Schedule(2);
        mySched.addTask(t1);
        String analysisString = mySched.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(8,0), LocalTime.of(11,0), true, false);
        Category fighting = new Category("Fighting", "Recreation");
        //overlapping situation variables
        Task t2 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(8,0), LocalDate.now(), "No repeat", LocalTime.of(1,0), fighting);
        Task t3 = new Task("practice mma", "knock 'em", "deadline", LocalTime.of(8,15), LocalDate.now(), "No repeat", LocalTime.of(1,0), fighting);
        Schedule mySched2 = new Schedule(1);
        mySched2.addTask(t2);
        mySched2.addTask(t3);
        String analysisString2 = mySched2.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(10,30), LocalTime.of(20,15), true, false);

        //inadequate eating situation variables
        Task t4 = new Task("practice git", "aim for a black belt", "deadline", LocalTime.of(7,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t5 = new Task("practice sql", "aim for a black belt", "deadline", LocalTime.of(10,10), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t6 = new Task("practice street java", "aim for a black belt", "deadline", LocalTime.of(13,20), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t7 = new Task("practice sword cpp", "aim for a black belt", "deadline", LocalTime.of(16,40), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t8 = new Task("practice javascript", "aim for a black belt", "deadline", LocalTime.of(19,50), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t9 = new Task("practice assembly", "aim for a black belt", "deadline", LocalTime.of(23,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Schedule mySched3 = new Schedule(1);
        mySched3.addTask(t4);
        mySched3.addTask(t5);
        mySched3.addTask(t6);
        mySched3.addTask(t7);
        mySched3.addTask(t8);
        mySched3.addTask(t9);
        String analysisString3 = mySched3.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(10,30), LocalTime.of(20,15), true, false);

        //inadequate break no. 2 scenario
        Task t10 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(7,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t11 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(10,15), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t12 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(13,25), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t13 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(15,35), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        Task t14 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(18,45), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting); 
        Schedule mySched4 = new Schedule(1);
        String analysisString4 = mySched4.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(2,15), LocalTime.of(23,30), true, false);
        mySched4.addTask(t10);
        mySched4.addTask(t11);
        mySched4.addTask(t12);
        mySched4.addTask(t13);
        mySched4.addTask(t14);
    }

    @Test
    public void testInadequateSleepSituation();
    {
        Assert.assertEquals(analysisString, 
        "Not enough time in the 24-hour period to sleep.");
    }

    @Test
    public void overlappingSituation()
    {
        Assert.assertEquals(analysisString2, "Task 'practice kung-fu' ends at 9:00, but Task 'practice mma' starts at 8:15");
    }

    @Test 
    public void testInadequateEatingTimeSituation()
    {
        Assert.assertEquals(analysisString3, "There is not enough time in the 24-hour period for you to eat enough food");
    }

    @Test
    public void testInadequateSecondBreakSituation()
    {
        Assert.assertEquals(analysisString4, "There is ample time for you to take your first uninterrupted 15-minute break today, but not enough time for you to take your second.");
    }


}
