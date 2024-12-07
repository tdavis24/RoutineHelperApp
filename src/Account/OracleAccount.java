package Account;

import java.sql.*;
import Database.*;
import Driver.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.*;
import Category.*;
import Account.Schedule.*;


// Class used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class OracleAccount implements AccountHandler{

    // create class variables
    private String username;
    private String email;
    private Name name;
    private String password;
    private Scanner scan = new Scanner(System.in);

    public OracleAccount(String username, String email, String password, String firstName, String lastName)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = new Name(firstName, lastName);
    }

    public AccountHandler deleteAccount()
    {
        // get account to be deleted
        AccountHandler deletedAccount = getAccount(this.username);

        String query = "DELETE FROM accounts WHERE username = ?";
        
        // delete account if possible
        if(deletedAccount != null)
        {
            try
            {
                if(OracleDatabase.connect())
                {
                    Connection connection = OracleDatabase.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query);

                    statement.setString(1, this.username);
                    
                    statement.close();
                }
            }
            catch(Exception e)
            {
                return null;
            }
            finally
            {
                OracleDatabase.disconnect();
            }
        }
        else
        {
            return null;
        }

        return deletedAccount;
    }

    public AccountHandler getAccount(String username)
    {
        // create return variable based on username
        AccountHandler curAccount = null;

        String query = "SELECT * FROM UserAccounts WHERE username = ?";

        // try fetching the account
        try
        {
            if(OracleDatabase.connect())
            {
                Connection connection = OracleDatabase.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                // set parameter for the query if possible (acceptable username)
                boolean validUsername = InputValidation.validateInput("username", username) != null;
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next())
                {
                    String curUsername = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    int spaceIndex =  name.indexOf(' ');

                    curAccount = new OracleAccount(curUsername, email, password, name.substring(0, spaceIndex), name.substring(spaceIndex));
                }

                resultSet.close();
                statement.close();
            }
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            OracleDatabase.disconnect();
        }


        return curAccount;
    }

    @Override
    public boolean updateAccount(String infoToUpdate, String information)
    {
        boolean accountUpdated;

        switch(infoToUpdate)
        {
            case "username":
            {
                try
                {
                    this.username = information;
                    accountUpdated = true;
                }
                catch(Exception e)
                {
                    accountUpdated = false;
                }
            }

            case "password":
            {
                try
                {
                    this.password = information;
                    accountUpdated = true;
                }
                catch(Exception e)
                {
                    accountUpdated = false;
                }
            }

            case "email":
            {
                try
                {
                    this.email = information;
                    accountUpdated = true;
                }
                catch(Exception e)
                {
                    accountUpdated = false;
                }
            }

            default:
            {
                accountUpdated = false;
            }
        }
        

        return accountUpdated;
    }

    public Schedule generateSchedule(int numDays){
        CommandLineInterface.clearConsole();
        // print current account information
        AccountHandler curAccount = getAccount(this.username);

        String query = "SELECT * FROM Routine WHERE username = '" + this.username + "'";

        Schedule returnSchedule = new Schedule(0);

        // try fetching the account
        try
        {
            if(OracleDatabase.connect())
            {
                Connection connection = OracleDatabase.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                // set parameter for the query if possible (acceptable username)
                boolean validUsername = InputValidation.sanitizeInput("username", username) != null;
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();
                
                ArrayList<Task> resultingTasks = new ArrayList<Task>();

                while(resultSet.next())
                {


                    String name = resultSet.getString("name");
                    String information = resultSet.getString("information");
                    String deadline = resultSet.getString("deadline");
                    Time routineTime = resultSet.getTime("routine_time");
                    LocalTime localRoutineTimeObj = routineTime.toLocalTime();
                    Date routineDate = resultSet.getDate("routine_day");
                    LocalDate localRoutineDateObj = routineDate.toLocalDate();
                    String recurrenceInterval = resultSet.getString("recurrence");
                    String categoryName = resultSet.getString("category");
                    Time oracleResultingDuration = resultSet.getTime("duration");
                    LocalTime duration = oracleResultingDuration.toLocalTime();
                    query = "SELECT name, type from CATEGORY where name = '" + categoryName + "'";
                    PreparedStatement categoryStatement = connection.prepareStatement(query);
                    ResultSet categoryResult = categoryStatement.executeQuery();
                    if(categoryResult.next()){
                        String categorySubQueryName = resultSet.getString("name");
                        String categoryType = resultSet.getString("type");
                        Category taskCategory = new Category(categorySubQueryName, categoryType);
                        Task addedTask = new Task(name, information, deadline, localRoutineTimeObj, localRoutineDateObj, recurrenceInterval,duration, taskCategory);
                        resultingTasks.add(addedTask);
                    }

                }

                Schedule displaySchedule = new Schedule(numDays);
                
                for(int i = 0; i < resultingTasks.size(); i++){
                    displaySchedule.addTask(resultingTasks.get(i));
                }

                displaySchedule.display();
                
                returnSchedule = displaySchedule;

                resultSet.close();
                statement.close();
            }
        }
        catch(Exception e)
        {
            return returnSchedule;
        }
        finally
        {
            OracleDatabase.disconnect();
        }


        return returnSchedule;
        
    }

    public String analyzeSchedule(Schedule schedule){


         String analysis = "";
        
        for(int i = 0; i < schedule.getSchedule().length; i++){
            if(i == 0){
                //first day of the schedule: start = true, end = false
                LocalTime startTimeOfNextDay = schedule.getSchedule()[i + 1].getDaySchedule().head.containedTask.getTimeOfDay();
                analysis += schedule.getSchedule()[i].getDaySchedule().analyze(startTimeOfNextDay, null, true, false);
            } else if (i == schedule.getSchedule().length - 1){
                //last day of the schedule: start = false, end = true
                LocalTime startTimeOfNextDay = LocalTime.MAX;
                LocalTime endTimeofYesterday = TimeCalculator.add(schedule.getSchedule()[i - 1].getDaySchedule().tail.containedTask.getTimeOfDay(), schedule.getSchedule()[i - 1].getDaySchedule().tail.containedTask.getDuration());
                analysis += schedule.getSchedule()[i].getDaySchedule().analyze(startTimeOfNextDay, endTimeofYesterday, false, true);
            } else {
                //day is in the middle of the schedule, so we need the previous day information as well as the next day information
                LocalTime startTimeOfNextDay = schedule.getSchedule()[i + 1].getDaySchedule().head.containedTask.getTimeOfDay();
                LocalTime endTimeofYesterday = TimeCalculator.add(schedule.getSchedule()[i - 1].getDaySchedule().tail.containedTask.getTimeOfDay(), schedule.getSchedule()[i - 1].getDaySchedule().tail.containedTask.getDuration());
                analysis += schedule.getSchedule()[i].getDaySchedule().analyze(startTimeOfNextDay, endTimeofYesterday, false, true);
            }
        }

        return analysis;
        

    }

    public void compareSchedule(Schedule otherSchedule, int numDays){
        //get schedule from user account
        Schedule curSchedule = generateSchedule(numDays);

        String totalScheduleAvailabilities = "";

        for(int i = 0; i < curSchedule.getSchedule().length; i++){
            DaySchedule otherDaySchedule = otherSchedule.getSchedule()[i].getDaySchedule();
            DaySchedule curDaySchedule = curSchedule.getSchedule()[i].getDaySchedule();
        }
    }

    public 

    @SuppressWarnings("static-access")
    @Override
    public String printInformation()
    {
        return "Account{" +
               "Username: " + username +
               ", email: '" + email + '\'' +
               ", Name: '" + name.getName() + '\'' +
               '}';
    }
}