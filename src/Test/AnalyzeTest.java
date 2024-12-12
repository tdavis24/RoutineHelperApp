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
    Schedule mySched = null;
    Task t1 = null;
    Task t2 = null;
    Task t3 = null;
    Task t4 = null;
    Task t5 = null;
    Task t6 = null;
    Task t7 = null;
    Task t8 = null;
    Task t9 = null;
    Task t10 = null;
    Task t11 = null;
    Task t12 = null;
    Task t13 = null;
    Task t14 = null;
    Category codeCategory = null;
    
    @Before
    public void setup(){
        //category for all cases
        codeCategory = new Category("Software Engineering", "Work");
        t1 = new Task("write code", "debug issues", "deadline", LocalTime.of(6, 0), LocalDate.now(), "Daily", LocalTime.of(17, 0), codeCategory);
        mySched = new Schedule(2);
        mySched.addTask(t1);
        
        Category fighting = new Category("Fighting", "Recreation");
        //overlapping situation variables
        t2 = new Task("practice kung-fu", "aim for a black belt", "deadline", LocalTime.of(8,0), LocalDate.now(), "No repeat", LocalTime.of(1,0), fighting);
        t3 = new Task("practice mma", "knock 'em", "deadline", LocalTime.of(8,15), LocalDate.now(), "No repeat", LocalTime.of(1,0), fighting);
        mySched = new Schedule(1);
        mySched.addTask(t2);
        mySched.addTask(t3);
        

        //inadequate eating situation variables
        t4 = new Task("practice git", "aim for a black belt", "deadline", LocalTime.of(7,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t5 = new Task("practice sql", "aim for a black belt", "deadline", LocalTime.of(10,10), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t6 = new Task("practice street java", "aim for a black belt", "deadline", LocalTime.of(13,20), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t7 = new Task("practice sword cpp", "aim for a black belt", "deadline", LocalTime.of(16,40), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t8 = new Task("practice javascript", "aim for a black belt", "deadline", LocalTime.of(19,50), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t9 = new Task("practice assembly", "aim for a black belt", "deadline", LocalTime.of(23,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        mySched = new Schedule(1);
        mySched.addTask(t4);
        mySched.addTask(t5);
        mySched.addTask(t6);
        mySched.addTask(t7);
        mySched.addTask(t8);
        mySched.addTask(t9);

        //inadequate break no. 2 scenario
        t10 = new Task("practice mathematics", "aim for a black belt", "deadline", LocalTime.of(7,0), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t11 = new Task("chemistry", "aim for a black belt", "deadline", LocalTime.of(10,15), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t12 = new Task("practice anatomy", "aim for a black belt", "deadline", LocalTime.of(13,25), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t13 = new Task("practice soccer", "aim for a black belt", "deadline", LocalTime.of(15,35), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting);
        t14 = new Task("practice physics", "aim for a black belt", "deadline", LocalTime.of(18,45), LocalDate.now(), "No repeat", LocalTime.of(3,0), fighting); 
        mySched = new Schedule(1);
        mySched.addTask(t10);
        mySched.addTask(t11);
        mySched.addTask(t12);
        mySched.addTask(t13);
        mySched.addTask(t14);
    }

    @Test
    public void testInadequateSleepSituation()
    {
        String analysisString = mySched.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(8,0), LocalTime.of(11,0), true, false);
        Assert.assertEquals(analysisString, 
        "Not enough time in the 24-hour period to sleep.");
    }

    @Test
    public void overlappingSituation()
    {
        String analysisString2 = mySched.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(10,30), LocalTime.of(20,15), true, false);
        Assert.assertEquals(analysisString2, "Task 'practice kung-fu' ends at 9:00, but Task 'practice mma' starts at 8:15");
    }

    @Test 
    public void testInadequateEatingTimeSituation()
    {
        String analysisString3 = mySched.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(10,30), LocalTime.of(20,15), true, false);
        Assert.assertEquals(analysisString3, "There is not enough time in the 24-hour period for you to eat enough food");
    }

    @Test
    public void testInadequateSecondBreakSituation()
    {
        String analysisString4 = mySched.getSchedule()[0].getDaySchedule().analyze(LocalTime.of(2,15), LocalTime.of(23,30), true, false);

        Assert.assertEquals(analysisString4, "There is ample time for you to take your first uninterrupted 15-minute break today, but not enough time for you to take your second.");
    }


}
