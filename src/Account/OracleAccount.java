package Account;

import java.sql.*;
import Database.OracleDatabase;
import Driver.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeParseException;
import Category.Category;
import Account.Schedule.Schedule;
import Account.Schedule.TimeCalculator;
import Account.Schedule.DaySchedule;


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
    private OracleDatabase db;

    public OracleAccount(String username, String email, String password, String firstName, String lastName)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = new Name(firstName, lastName);
        this.db = new OracleDatabase();
        this.db.connect();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public AccountHandler deleteAccount() {
        String query = "DELETE FROM UserAccounts WHERE username = ?";
        AccountHandler deletedAccount = null;

        // Retrieve account details before deletion
        deletedAccount = getAccount(this.username);

        if (deletedAccount != null) {
            try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
                statement.setString(1, this.username);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Account '" + this.username + "' deleted successfully.");
                } else {
                    System.out.println("Failed to delete account '" + this.username + "'.");
                    deletedAccount = null;
                }
            } catch (SQLException e) {
                System.out.println("Error deleting account: " + e.getMessage());
                deletedAccount = null;
            }
        } else {
            System.out.println("Account '" + this.username + "' does not exist.");
        }

        return deletedAccount;
    }

    public AccountHandler getAccount(String username) {
        AccountHandler curAccount = null;
        String query = "SELECT * FROM UserAccounts WHERE username = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String curUsername = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("name");
                int spaceIndex = fullName.indexOf(' ');

                String firstName = fullName.substring(0, spaceIndex);
                String lastName = fullName.substring(spaceIndex + 1);

                curAccount = new OracleAccount(curUsername, email, password, firstName, lastName);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
        }

        return curAccount;
    }

    @Override
    public boolean updateAccount(String infoToUpdate, String information) {
        boolean accountUpdated = false;
        String query = "";

        switch (infoToUpdate.toLowerCase()) {
            case "username":
                query = "UPDATE UserAccounts SET username = ? WHERE username = ?";
                try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
                    statement.setString(1, information);
                    statement.setString(2, this.username);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        this.username = information; // Update local variable
                        accountUpdated = true;
                        System.out.println("Username updated successfully.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error updating username: " + e.getMessage());
                }
                break;

            case "password":
                query = "UPDATE UserAccounts SET password = ? WHERE username = ?";
                try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
                    statement.setString(1, information);
                    statement.setString(2, this.username);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        this.password = information; // Update local variable
                        accountUpdated = true;
                        System.out.println("Password updated successfully.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error updating password: " + e.getMessage());
                }
                break;

            case "email":
                query = "UPDATE UserAccounts SET email = ? WHERE username = ?";
                try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
                    statement.setString(1, information);
                    statement.setString(2, this.username);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        this.email = information; // Update local variable
                        accountUpdated = true;
                        System.out.println("Email updated successfully.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error updating email: " + e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid information to update.");
                break;
        }

        return accountUpdated;
    }

    public Schedule generateSchedule(int numDays){
        CommandLineInterface.clearConsole();
        // print current account information
        AccountHandler curAccount = getAccount(this.username);

        String query = "SELECT * FROM Routine WHERE username = '" + this.username + "'";

        Schedule returnSchedule = new Schedule(0);

        // try fetching the routines/tasks
        try
        {
            if(db.connect())
            {
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                // set parameter for the query if possible (acceptable username)
                boolean validUsername = InputValidation.validateInput("username", username) != null;
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
            db.disconnect();
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

    public String compareSchedule(Schedule otherSchedule, int numDays){
        //get schedule from user account
        Schedule curSchedule = generateSchedule(numDays);

        String totalScheduleAvailabilities = "";

        for(int i = 0; i < curSchedule.getSchedule().length; i++){
            DaySchedule otherDaySchedule = otherSchedule.getSchedule()[i].getDaySchedule();
            DaySchedule curDaySchedule = curSchedule.getSchedule()[i].getDaySchedule();
            totalScheduleAvailabilities += curSchedule.getSchedule()[i].getDaySchedule().compareDaySchedule( otherDaySchedule);
        }

        return totalScheduleAvailabilities;
    }

    public String generateScheduleStatistics(int numDays){
        String stats = "";
        Schedule curSchedule = generateSchedule(numDays);
        stats = curSchedule.generateScheduleStatistics();
        return stats;

    }

    @SuppressWarnings("static-access")
    @Override
    public String accountInformation()
    {
        return "Account{" +
               "Username: " + username +
               ", email: '" + email + '\'' +
               ", Name: '" + name.getName() + '\'' +
               '}';
    }

    public boolean availableUsername(String possibleUsername)
    {
        AccountHandler account = getAccount(possibleUsername);

        return (account == null);
    }

    public boolean verifyPassword(String curPass)
    {
        return (this.password.equals(curPass));
    }

    public String viewAccount()
    {
        return accountInformation();
    }

    public void viewRoutines() {
        List<Task> routines = new ArrayList<>();
        String query = "SELECT * FROM Routine WHERE username = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String recurrence = resultSet.getString("recurrence");
                String deadline = resultSet.getString("deadline");
                Time routineTime = resultSet.getTime("routine_time");
                LocalTime localRoutineTimeObj = routineTime.toLocalTime();
                Date routineDate = resultSet.getDate("routine_day");
                LocalDate localRoutineDateObj = routineDate.toLocalDate();
                Time routineDuration = resultSet.getTime("duration");
                LocalTime duration = routineDuration.toLocalTime();
                String categoryName = resultSet.getString("category");

                // Retrieve category details
                String categoryQuery = "SELECT type FROM Category WHERE name = ? AND username = ?";
                String categoryType = "";
                try (PreparedStatement categoryStatement = db.getConnection().prepareStatement(categoryQuery)) {
                    categoryStatement.setString(1, categoryName);
                    categoryStatement.setString(2, this.username);
                    ResultSet categoryResult = categoryStatement.executeQuery();
                    if (categoryResult.next()) {
                        categoryType = categoryResult.getString("type");
                    } else {
                        categoryType = "Undefined";
                    }
                    categoryResult.close();
                }

                Category category = new Category(categoryName, categoryType);

                Task routineTask;
                if (recurrence == null || recurrence.isEmpty()) {
                    routineTask = new Task(name, information, deadline, localRoutineTimeObj, localRoutineDateObj, duration, category);
                } else {
                    routineTask = new Task(name, information, deadline, localRoutineTimeObj, localRoutineDateObj, recurrence, duration, category);
                }

                routines.add(routineTask);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving routines: " + e.getMessage());
            return;
        }

        if (routines.isEmpty()) {
            System.out.println("You have no routines.");
        } else {
            System.out.println("===== Your Routines =====");
            for (Task routine : routines) {
                System.out.println(routine);
            }
        }
    }

	public String analyzeSchedule(int numDays) {
		return "";
	}

	public List<Category> viewCategories() {
        String query = "SELECT name, type FROM Category WHERE username = ?";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String categoryName = resultSet.getString("name");
                String categoryType = resultSet.getString("type");
                categories.add(new Category(categoryName, categoryType));
            }

            resultSet.close();
        } catch (SQLException e) {
            return null;
        }

        return categories;

    }
    
	public boolean createRoutine(Task task) {
        String query = "INSERT INTO Routine (username, name, information, deadline, routine_time, routine_day, recurrence, duration, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, task.getName());
            statement.setString(3, task.getInformation());
            statement.setString(4, task.getDeadline());
            statement.setTime(5, Time.valueOf(task.getTimeOfDay()));
            statement.setDate(6, Date.valueOf(task.getDate()));
            statement.setString(7, task.getRecurrenceInterval());
            statement.setTime(8, Time.valueOf(task.getDuration()));
            statement.setString(9, task.getCategory().getCategoryName());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

	public void updateRoutine(Task task) {
        String query = "UPDATE Routine SET information = ?, deadline = ?, routine_time = ?, routine_day = ?, recurrence = ?, duration = ?, category = ? WHERE username = ? AND name = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, task.getInformation());
            statement.setString(2, task.getDeadline());
            statement.setTime(3, Time.valueOf(task.getTimeOfDay()));
            statement.setDate(4, Date.valueOf(task.getDate()));
            statement.setString(5, task.getRecurrenceInterval());
            statement.setTime(6, Time.valueOf(task.getDuration()));
            statement.setString(7, task.getCategory().getCategoryName());
            statement.setString(8, this.username);
            statement.setString(9, task.getName());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Routine '" + task.getName() + "' updated successfully.");
            } else {
                System.out.println("Failed to update routine '" + task.getName() + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating routine: " + e.getMessage());
        }
    }

	public void deleteRoutine(String name) {
        String query = "DELETE FROM Routine WHERE username = ? AND name = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, name);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Routine '" + name + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete routine '" + name + "'. It may not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting routine: " + e.getMessage());
        }
    }

	public void viewToDoList() {
        String query = "SELECT * FROM ToDoList WHERE username = ?";
        List<Task> toDoTasks = new ArrayList<>();

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String deadline = resultSet.getString("deadline");
                Time toDoTime = resultSet.getTime("toDo_time");
                LocalTime localToDoTimeObj = toDoTime.toLocalTime();
                Date toDoDate = resultSet.getDate("toDo_day");
                LocalDate localToDoDateObj = toDoDate.toLocalDate();
                Time toDoDuration = resultSet.getTime("duration");
                LocalTime duration = toDoDuration.toLocalTime();
                String categoryName = resultSet.getString("category");

                // Retrieve category details
                String categoryQuery = "SELECT type FROM Category WHERE name = ? AND username = ?";
                String categoryType = "";
                try (PreparedStatement categoryStatement = db.getConnection().prepareStatement(categoryQuery)) {
                    categoryStatement.setString(1, categoryName);
                    categoryStatement.setString(2, this.username);
                    ResultSet categoryResult = categoryStatement.executeQuery();
                    if (categoryResult.next()) {
                        categoryType = categoryResult.getString("type");
                    } else {
                        categoryType = "Undefined";
                    }
                    categoryResult.close();
                }

                Category taskCategory = new Category(categoryName, categoryType);
                Task task = new Task(name, information, deadline, localToDoTimeObj, localToDoDateObj, duration, taskCategory);
                toDoTasks.add(task);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving to-do list: " + e.getMessage());
            return;
        }

        if (toDoTasks.isEmpty()) {
            System.out.println("Your To-Do List is empty.");
        } else {
            System.out.println("===== Your To-Do List =====");
            for (Task task : toDoTasks) {
                System.out.println(task);
            }
        }
    }
    
    @Override
    public void createToDoList() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Create New To-Do Task =====");
        System.out.print("Enter task name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter task information: ");
        String information = scanner.nextLine().trim();

        System.out.print("Enter deadline (yyyy-MM-dd): ");
        String deadline = scanner.nextLine().trim();

        System.out.print("Enter to-do time (HH:mm): ");
        String timeStr = scanner.nextLine().trim();
        LocalTime toDoTime;
        try {
            toDoTime = LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format.");
            scanner.close();
            return;
        }

        System.out.print("Enter to-do date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate toDoDate;
        try {
            toDoDate = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
            scanner.close();
            return;
        }

        System.out.print("Enter duration (HH:mm): ");
        String durationStr = scanner.nextLine().trim();
        LocalTime duration;
        try {
            duration = LocalTime.parse(durationStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid duration format.");
            scanner.close();
            return;
        }

        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine().trim();

        // Retrieve category type
        String categoryQuery = "SELECT type FROM Category WHERE name = ? AND username = ?";
        String categoryType = "";
        try (PreparedStatement categoryStatement = db.getConnection().prepareStatement(categoryQuery)) {
            categoryStatement.setString(1, categoryName);
            categoryStatement.setString(2, this.username);
            ResultSet categoryResult = categoryStatement.executeQuery();
            if (categoryResult.next()) {
                categoryType = categoryResult.getString("type");
            } else {
                System.out.println("Category '" + categoryName + "' does not exist.");
                return;
            }
            categoryResult.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving category: " + e.getMessage());
            return;
        }

        Category taskCategory = new Category(categoryName, categoryType);
        Task newTask = new Task(name, information, deadline, toDoTime, toDoDate, duration, taskCategory);

        // Insert into ToDoList table
        String insertQuery = "INSERT INTO ToDoList (username, name, information, deadline, toDo_time, toDo_day, duration, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStatement = db.getConnection().prepareStatement(insertQuery)) {
            insertStatement.setString(1, this.username);
            insertStatement.setString(2, newTask.getName());
            insertStatement.setString(3, newTask.getInformation());
            insertStatement.setString(4, newTask.getDeadline());
            insertStatement.setTime(5, Time.valueOf(newTask.getTimeOfDay()));
            insertStatement.setDate(6, Date.valueOf(newTask.getDate()));
            insertStatement.setTime(7, Time.valueOf(newTask.getDuration()));
            insertStatement.setString(8, newTask.getCategory().getCategoryName());

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("To-Do Task '" + newTask.getName() + "' created successfully.");
            } else {
                System.out.println("Failed to create To-Do Task '" + newTask.getName() + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating To-Do Task: " + e.getMessage());
        }
        scanner.close();
    }

	@Override
    public void updateToDoList() {
        System.out.println("===== Update To-Do Task =====");
        System.out.print("Enter the name of the task to update: ");
        String taskName = scan.nextLine().trim();

        // Retrieve existing task
        String selectQuery = "SELECT * FROM ToDoList WHERE username = ? AND name = ?";
        Task existingTask = null;

        try (PreparedStatement selectStatement = db.getConnection().prepareStatement(selectQuery)) {
            selectStatement.setString(1, this.username);
            selectStatement.setString(2, taskName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String information = resultSet.getString("information");
                String deadline = resultSet.getString("deadline");
                Time toDoTime = resultSet.getTime("toDo_time");
                LocalTime localToDoTimeObj = toDoTime.toLocalTime();
                Date toDoDate = resultSet.getDate("toDo_day");
                LocalDate localToDoDateObj = toDoDate.toLocalDate();
                LocalTime duration = resultSet.getTime("duration").toLocalTime();
                String categoryName = resultSet.getString("category");

                // Retrieve category type
                String categoryQuery = "SELECT type FROM Category WHERE name = ? AND username = ?";
                String categoryType = "";
                try (PreparedStatement categoryStatement = db.getConnection().prepareStatement(categoryQuery)) {
                    categoryStatement.setString(1, categoryName);
                    categoryStatement.setString(2, this.username);
                    ResultSet categoryResult = categoryStatement.executeQuery();
                    if (categoryResult.next()) {
                        categoryType = categoryResult.getString("type");
                    } else {
                        System.out.println("Category '" + categoryName + "' does not exist.");
                        return;
                    }
                    categoryResult.close();
                }

                Category taskCategory = new Category(categoryName, categoryType);
                existingTask = new Task(taskName, information, deadline, localToDoTimeObj, localToDoDateObj, duration, taskCategory);
            } else {
                System.out.println("Task '" + taskName + "' does not exist.");
                return;
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving task: " + e.getMessage());
            return;
        }

        // Prompt user for new details (optional: allow skipping fields)
        System.out.print("Enter new information (leave blank to keep unchanged): ");
        String newInformation = scan.nextLine().trim();
        if (!newInformation.isEmpty()) {
            existingTask.setInformation(newInformation);
        }

        System.out.print("Enter new deadline (yyyy-MM-dd) (leave blank to keep unchanged): ");
        String newDeadline = scan.nextLine().trim();
        if (!newDeadline.isEmpty()) {
            existingTask.setDeadline(newDeadline);
        }

        System.out.print("Enter new to-do time (HH:mm) (leave blank to keep unchanged): ");
        String newTimeStr = scan.nextLine().trim();
        if (!newTimeStr.isEmpty()) {
            try {
                LocalTime newToDoTime = LocalTime.parse(newTimeStr);
                existingTask.setTimeOfDay(newToDoTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Update aborted.");
                return;
            }
        }

        System.out.print("Enter new to-do date (yyyy-MM-dd) (leave blank to keep unchanged): ");
        String newDateStr = scan.nextLine().trim();
        if (!newDateStr.isEmpty()) {
            try {
                LocalDate newToDoDate = LocalDate.parse(newDateStr);
                existingTask.setStartDate(newToDoDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Update aborted.");
                return;
            }
        }

        System.out.print("Enter new duration (HH:mm) (leave blank to keep unchanged): ");
        String newDurationStr = scan.nextLine().trim();
        if (!newDurationStr.isEmpty()) {
            try {
                LocalTime newDuration = LocalTime.parse(newDurationStr);
                existingTask.setDuration(newDuration);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid duration format. Update aborted.");
                return;
            }
        }

        System.out.print("Enter new category name (leave blank to keep unchanged): ");
        String newCategoryName = scan.nextLine().trim();
        if (!newCategoryName.isEmpty()) {
            // Retrieve category type
            String categoryQuery = "SELECT type FROM Category WHERE name = ? AND username = ?";
            String categoryType = "";
            try (PreparedStatement categoryStatement = db.getConnection().prepareStatement(categoryQuery)) {
                categoryStatement.setString(1, newCategoryName);
                categoryStatement.setString(2, this.username);
                ResultSet categoryResult = categoryStatement.executeQuery();
                if (categoryResult.next()) {
                    categoryType = categoryResult.getString("type");
                } else {
                    System.out.println("Category '" + newCategoryName + "' does not exist.");
                    return;
                }
                categoryResult.close();
            } catch (SQLException e) {
                System.out.println("Error retrieving category: " + e.getMessage());
                return;
            }

            existingTask.setCategory(new Category(newCategoryName, categoryType));
        }

        // Update the task in the database
        updateRoutine(existingTask);
    }

	public void deleteToDoList(String name) {
        String query = "DELETE FROM ToDoList WHERE username = ? AND name = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, name);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("To-Do Task '" + name + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete To-Do Task '" + name + "'. It may not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting To-Do Task: " + e.getMessage());
        }
    }

    public boolean createCategory(String name, String type) {
        String query = "INSERT INTO Category (username, name, type) VALUES (?, ?, ?)";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, name);
            statement.setString(3, type);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category '" + name + "' created successfully.");
                return true;
            } else {
                System.out.println("Failed to create category '" + name + "'. It may already exist.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error creating category: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCategory(String name) {
        String query = "DELETE FROM Category WHERE username = ? AND name = ?";

        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, name);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category '" + name + "' deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete category '" + name + "'. It may not exist or is in use.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }

    public Category getCategory(String categoryName)
    {
        Category retVal = null;
        String query = "SELECT * from Category where name = ? AND username = ?";
        
        try(PreparedStatement statement = db.getConnection().prepareStatement(query))
        {
            statement.setString(1, categoryName);
            statement.setString(2, this.username);

            ResultSet resultSet = statement.executeQuery();

            String categoryNameFull = resultSet.getString("name");
            String categoryType = resultSet.getString("type");

            retVal = new Category(categoryNameFull, categoryType);
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            db.disconnect();
        }

        return retVal;
    }
}