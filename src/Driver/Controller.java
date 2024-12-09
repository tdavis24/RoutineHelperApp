package Driver;

import Account.*;
import Category.Category;
import Database.OracleDatabase;
import Account.Schedule.*;
import java.sql.*;
import java.util.LinkedList;

// Controller class used to pass information between the UI and the database
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: November 15, 2024
public class Controller {
    private AccountHandler curAccount;
    private OracleDatabase db;

    public Controller() {
        db = new OracleDatabase();
        if(!db.connect()) {
            System.out.println("Failed to connect to the database.");
        }
    }

    public String getCurrentUsername(){
        return this.curAccount.getUsername();
    }

    public boolean updateAccount(String username, String infoToUpdate, String newInfo) { 
        // Fetch the account based on the provided username
        AccountHandler accountToUpdate = fetchAccountDetails(username);
        if (accountToUpdate == null) {
            System.out.println("Account with username " + username + " not found.");
            return false;
        }

        // Update the specific field
        boolean updateSuccess = accountToUpdate.updateAccount(infoToUpdate, newInfo);
            if (updateSuccess) {
                // Update the database
                Connection conn = db.getConnection();
                String sql = "";
                switch (infoToUpdate.toLowerCase()) {
                    case "username":
                        sql = "UPDATE accounts SET username = ? WHERE username = ?";
                        break;
                    case "password":
                        sql = "UPDATE accounts SET password = ? WHERE username = ?";
                        break;
                    case "email":
                        sql = "UPDATE accounts SET email = ? WHERE username = ?";
                        break;
                    default:
                        System.out.println("Unknown field to update.");
                        return false;
                }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newInfo);
                pstmt.setString(2, username);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Account information updated successfully in the database.");
                    return true;
                } else {
                    System.out.println("Failed to update account information in the database.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("SQL Error while updating account: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Failed to update account information.");
            return false;
        }
    }

    public boolean verifyPassword(String password) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in.");
            return false;
        }
        return curAccount.verifyPassword(password);
    }

    public boolean isUsernameTaken(String username){
        AccountHandler tempAccount = fetchAccountDetails(username);
        if(tempAccount == null){
            return false;
        } else {
            return true;
        }
    }


    public AccountHandler deleteAccount() {
        if (curAccount != null) {
            AccountHandler deleted = curAccount.deleteAccount();
            if (deleted != null) {
                // Delete the account from the database
                Connection conn = db.getConnection();
                String sql = "DELETE FROM accounts WHERE username = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, curAccount.getUsername());
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Account deleted from the database.");
                        curAccount = null;
                        return deleted;
                    } else {
                        System.out.println("Unable to delete the account from the database.");
                        return null;
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Error while deleting account: " + e.getMessage());
                    return null;
                }
            } else {
                System.out.println("Unable to delete the account.");
            }
        } else {
            System.out.println("No account currently logged in to delete.");
        }
        return null;
    }
    
    public boolean createTask(String name, String information, Category category) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in. Cannot create task.");
            return false;
        }

        // EXAMPLE CASE
        Task newTask = new Task(name, information, "2024-12-31", category, "None");
        
        boolean taskAdded = curAccount.createRoutine(newTask);
        if (taskAdded) {
            Connection conn = db.getConnection();
            String sql = "INSERT INTO tasks (username, name, information, deadline, recurrence_interval, category) VALUES (?, ?, ?, ?, ?, ?)";
        
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, curAccount.getUsername());
                pstmt.setString(2, newTask.getName());
                pstmt.setString(3, newTask.getInformation());
                pstmt.setString(4, newTask.getDeadline());
                pstmt.setString(5, newTask.getRecurrenceInterval());
                pstmt.setString(6, category.getCategoryName());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Task created and saved to the database successfully.");
                    return true;
                } else {
                    System.out.println("Failed to save the task to the database.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("SQL Error while creating task: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Failed to add task to the account.");
            return false;
        }
    }

    /*
     *  Method to change instance variable of Controller class
     * 
     *  @param account Account to change instance variable to
     * 
     *  @return Return true if instance variable was changed
     *  @return Return false if instance variable could not be changed
     */
    public boolean setAccount(AccountHandler account)
    {
        
        try {
            this.curAccount = account;
            return true;
        } catch(Exception e) {
            System.out.println("Error setting account: " + e.getMessage());
            return false;
        }
    }

    /*
     *  Method to create an an account
     * 
     *  @param firstName A user's first name
     *  @param lastName A user's last name
     *  @param username A user's desired username
     *  @param email A user's email
     *  @param password A user's password
     * 
     *  @return Return an AccountHandler object created with the information passed in parameters
     */
    public AccountHandler createAccount(String firstName, String lastName, String username, String email, String password)
    {
        AccountHandler account = new OracleAccount(username, email, password, firstName, lastName);
        boolean accountSet = setAccount(account);
        if(accountSet)
        {
            Connection conn = db.getConnection();
            String sql = "INSERT INTO accounts (username, email, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.setString(4, firstName);
                pstmt.setString(5, lastName);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Account created and saved to the database successfully.");
                    return this.curAccount;
                } else {
                    System.out.println("Failed to save account to the databse");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("SQL Error while creating account: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("Failed to set the account.");
            return null;
        }
    }

    /*
     *  Method to "log into" an account
     * 
     *  @param username Provided username
     *  @param password Provided password
     * 
     *  @return Return an AccountHandler object for account
     *  @return Return null if username or password was invalid
     */
    public AccountHandler loginAccount(String username, String password) {
        try {
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM accounts WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (dbPassword.equals(password)) {
                        String email = rs.getString("email");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        AccountHandler account = new OracleAccount(username, email, dbPassword, firstName, lastName);
                        setAccount(account);
                        System.out.println("Login successful.");
                        return account;
                    } else {
                        System.out.println("Incorrect password.");
                        return null;
                    }
                } else {
                    System.out.println("Username not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while logging in: " + e.getMessage());
            return null;
        }
    }

    /**
     * Signs out the current account
     */
    public void signOut() {
    if (curAccount != null) {
        System.out.println("Signing out account: " + curAccount.getUsername());
        curAccount = null;
        System.out.println("Successfully signed out.");
    } else {
        System.out.println("No account is currently signed in.");
    }
}


    /**
     * Generates a schedule for the current account
     */
    public void generateSchedule(int numDays) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in to generate a schedule.");
            return;
        }
        Schedule schedule = curAccount.generateSchedule(numDays);
        if (schedule != null) {
            schedule.display();
        } else {
            System.out.println("Failed to generate schedule.");
        }
    }

    /**
     * Analyzes the current account's schedule
     */
    public void analyzeSchedule(int numDays) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in to analyze schedule.");
            return;
        }
        curAccount.analyzeSchedule(numDays);
    }

    /**
     * Compare the current account's schedule with another user's schedule.
     */
    public void compareScheduleWithAnotherUser(String otherUsername, int numDays) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in to compare schedules.");
            return;
        }

        // Fetch other user's account details
        AccountHandler otherAccount = fetchAccountDetails(otherUsername);
        if (otherAccount == null) {
            System.out.println("Unable to fetch the other user's account.");
            return;
        }
        Schedule otherSchedule = otherAccount.generateSchedule(numDays);
        if (otherSchedule != null) {
            curAccount.compareSchedule(otherSchedule, numDays);
        } else {
            System.out.println("Unable to generate the other user's schedule.");
        }
    }

    /**
     * Generate schedule statstics for the current account
     */
    public void generateScheduleStatistics(int numDays) {
        if (curAccount == null) {
            System.out.println("No account is currently logged in to generate schedule statistics.");
            return;
        }
        curAccount.generateScheduleStatistics(numDays);
    }

    /**
     * Fetch account details for a given username
     */
    private AccountHandler fetchAccountDetails(String username) {
        try {
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM accounts WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    AccountHandler account = new OracleAccount(username, email, password, firstName, lastName);
                    return account;
                } else {
                    System.out.println("Username not found: " + username);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while fetching account details: " + e.getMessage());
            return null;
        }
    }
    /*
     * Create a todo list based off of task information
     */
    public ToDoList generateTodoList(AccountHandler curAccount, String timeFrame){
        LinkedList<Task> remainingTasks = new LinkedList<Task>();
        
        Schedule mySchedule = curAccount.generateSchedule(1);

        Day currentDay = mySchedule.getSchedule()[0];

        remainingTasks = currentDay.getDaySchedule().getRemainingTasksForTheDay();

        ToDoList todoList = new ToDoList(remainingTasks, timeFrame);

        return todoList;

    }
    /**
     * Close the database connection.
     */
    public void close() {
        if (db != null) {
            if (db.disconnect()) {
                System.out.println("Database connection closed.");
            } else {
                System.out.println("Failed to close the database connection.");
            }
        }
    }
}