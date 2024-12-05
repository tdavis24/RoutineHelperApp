package Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import Account.*;
import Category.*;

// UI class for the Routine Helper App
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: November 13, 2024
public class CommandLineInterface {
    // Creation of Scanner, Controller, ToDoList, and Routine List objects
    private static Scanner scan = new Scanner(System.in);
    private static Controller controller = new Controller();
    private static ToDoList toDoList = new ToDoList();
    private static List<Routine> routines = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();

    // Use Case 3.1.1: Create a Routine
    public static void addRoutine() {
        System.out.print("Enter the name of the routine: ");
        String name = scan.next();
        System.out.print("Enter the information about the routine: ");
        String information = scan.next();
        System.out.print("Enter the deadline for the routine: ");
        String deadline = scan.next();
        System.out.print("Enter the recurrence interval (e.g., daily, weekly): ");
        String recurrenceInterval = scan.next();
        System.out.print("Enter the category for the routine: ");
        String categoryName = scan.next();

        Category category = new Category(categoryName);
        Routine newRoutine = new Routine(name, information, deadline, category, recurrenceInterval);
        routines.add(newRoutine);

        System.out.println("Routine added successfully: " + name);
    }

    // Use Case 3.1.2: Set Reminder for Routine
    public static void setReminderForRoutine() {
        System.out.print("Enter the name of the routine to set a reminder for: ");
        String name = scan.next();
        for (Routine routine : routines) {
            if (routine.name.equalsIgnoreCase(name)) {
                System.out.print("Enter reminder time (yyyy-MM-dd HH:mm:ss): ");
                String reminderTimeStr = scan.next();
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date reminderTime = dateFormat.parse(reminderTimeStr);
                    routine.setReminder(reminderTime);
                    System.out.println("Reminder set successfully for routine: " + name);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
                }
                return;
            }
        }
        System.out.println("Routine not found: " + name);
    }

    // Use Case 3.1.3: Retrieve Routine Progress
    public static void retrieveRoutineProgress() {
        System.out.println("Routine Progress:");
        for (Routine routine : routines) {
            System.out.println("Routine: " + routine.name + " - Completion: [Data to be integrated]");
        }
    }

    // Use Case 3.1.4: Edit a Routine
    public static void editRoutine() {
        System.out.print("Enter the name of the routine to edit: ");
        String name = scan.next();
        for (Routine routine : routines) {
            if (routine.getName().equalsIgnoreCase(name)) {
                System.out.println("What would you like to edit?");
                System.out.println("1: Add Task\n2: Delete Task\n3: Adjust Task\n4: Change Routine Details");
                int choice = InputValidation.validateMenuChoice(scan.next());
                switch (choice) {
                    case 1:
                        // Add Task
                        System.out.print("Enter task name: ");
                        String taskName = scan.next();
                        System.out.print("Enter task information: ");
                        String taskInfo = scan.next();
                        System.out.print("Enter task deadline: ");
                        String taskDeadline = scan.next();
                        Task newTask = new Task(taskName, taskInfo, taskDeadline);
                        routine.addTask(newTask);
                        System.out.println("Task added to routine.");
                        break;
                    case 2:
                        // Delete Task
                        System.out.print("Enter the name of the task to delete: ");
                        String delTaskName = scan.next();
                        routine.deleteTask(delTaskName);
                        System.out.println("Task deleted from routine.");
                        break;
                    case 3:
                        // Adjust Task
                        System.out.print("Enter the name of the task to adjust: ");
                        String adjTaskName = scan.next();
                        System.out.print("Enter new task information: ");
                        String newTaskInfo = scan.next();
                        System.out.print("Enter new task deadline: ");
                        String newTaskDeadline = scan.next();
                        Task adjustedTask = new Task(adjTaskName, newTaskInfo, newTaskDeadline);
                        routine.adjustTask(adjTaskName, adjustedTask);
                        System.out.println("Task adjusted in routine.");
                        break;
                    case 4:
                        // Change Routine Details
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
                return;
            }
        }
        System.out.println("Routine not found: " + name);
    }

    // Use Case 3.1.6: Create Account
    public static void createAccount() {
        System.out.print("Enter your first name: ");
        String firstName = scan.next();
        System.out.print("Enter your last name: ");
        String lastName = scan.next();
        System.out.print("Enter your desired username: ");
        String username = scan.next();
        System.out.print("Enter your email: ");
        String email = scan.next();
        System.out.print("Enter your password: ");
        String password = scan.next();

        AccountHandler newAccount = new OracleAccount(username, email, password, firstName, lastName);
        System.out.println("Account created successfully for: " + firstName + " " + lastName);
    }

    // Use Case 3.1.7: Forgot Password
    public static void forgotPassword() {
        System.out.print("Enter your email address to reset password: ");
        String email = scan.next();
        System.out.println("A password reset link has been sent to: " + email);
    }

    // Use Case 3.1.8: Update Account Information
    public static void updateAccountInformation() {
        System.out.print("Enter your username to update account information: ");
        String username = scan.next();
        System.out.print("Enter new email: ");
        String newEmail = scan.next();
        System.out.print("Enter new password: ");
        String newPassword = scan.next();
        System.out.println("Account information updated for username: " + username);
    }

    // Use Case 3.1.9: Create Routine Category
    public static void createRoutineCategory() {
        System.out.print("Enter the name of the new category: ");
        String categoryName = scan.next();
        Category newCategory = new Category(categoryName);
        categories.add(newCategory);
        System.out.println("Category created successfully: " + categoryName);
    }

    // Use Case 3.1.10: Change Password
    public static void changePassword() {
        System.out.print("Enter your current password: ");
        String currentPassword = scan.next();
        System.out.print("Enter your new password: ");
        String newPassword = scan.next();
        System.out.println("Password has been changed successfully.");
    }

    // Use Case 3.1.11: Generate Account Schedule with Given Preferences
    public static void generateAccountSchedule() {
        System.out.println("Generating schedule based on your preferences...");
        System.out.println("[Schedule generated based on user preferences]");
    }

    // Use Case 3.1.12: Generate Profile Schedule Statistics
    public static void generateProfileScheduleStatistics() {
        System.out.println("Generating schedule statistics...");
        System.out.println("[Statistics: Hours studying, hours sleeping, etc.]");
    }

    // Use Case 3.1.13: Generate Profile To-Do List
    public static void generateProfileToDoList() {
        System.out.println("Generating To-Do List for today...");
        toDoList.displayToDoList();
    }

    // Use Case 3.1.14: Analyze Routine
    public static void analyzeRoutine() {
        System.out.println("Analyzing routine for overlaps and improvements...");
        System.out.println("[Suggestions generated for routine optimization]");
    }

    // Use Case 3.1.15: Compare Routine with Another User
    public static void compareRoutineWithAnotherUser() {
        System.out.print("Enter the username of the other user to compare routines: ");
        String otherUsername = scan.next();
        System.out.println("Comparing routines with user: " + otherUsername);
        System.out.println("[Available time slots for collaboration]");
    }

    // Adding options to handle tasks in the To-Do List...?
    public static void manageToDoList() {
        System.out.println("What would you like to do?\n1: Add Task\n2: Delete Task\n3: Display To-Do List\n4: Back to Main Menu");
        int choice = InputValidation.validateMenuChoice(scan.next());
    }

    // Use Case 3.1.5: Delete a Routine
    public static void deleteRoutine() {
        System.out.print("Enter the name of the routine to delete: ");
        String name = scan.next();
        boolean routineFound = false;
        for (Routine routine : routines) {
            if (routine.name.equalsIgnoreCase(name)) {
                routines.remove(routine);
                System.out.println("Routine deleted successfully: " + name);
                routineFound = true;
                break;
            }
        }
        if (!routineFound) {
            System.out.println("Routine not found: " + name);
        }
    }

    // Adding options to handle tasks in the To-Do List
    public static void manageToDoList() {
        System.out.println("What would you like to do?\n1: Add Task\n2: Delete Task\n3: Display To-Do List\n4: Back to Main Menu");
        int choice = InputValidation.sanitizeMenuChoice(scan.next());
        switch (choice) {
            case 1:
                System.out.print("Enter task name: ");
                String taskName = scan.next();
                System.out.print("Enter task information: ");
                String taskInfo = scan.next();
                System.out.print("Enter task deadline: ");
                String taskDeadline = scan.next();
                Task task = new Task(taskName, taskInfo, taskDeadline);
                toDoList.addTask(task);
                break;
            case 2:
                System.out.print("Enter the name of the task to delete: ");
                String deleteTaskName = scan.next();
                toDoList.deleteTask(deleteTaskName);
                break;
            case 3:
                toDoList.displayToDoList();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /*
     *  Method to print login/create account screen
     */
    public static void printLoginScreen()
    {
        // print login screen
        System.out.print("Would you like to sign in or create an account?\n1: Sign in\n2: Create account\n3: Exit application\nEnter your selection here: ");

        // receive user input
        String input = scan.next();

        // sanitize input
        int choice = InputValidation.validateMenuChoice(input);

        // verify user made a valid choice
        switch(choice)
        {
            // user wants to login
            case 1:
            {
                // clear console and print account login screen
                clearConsole();
                printAccountLoginScreen();
                break;
            }

            // user wants to create an account
            case 2:
            {
                // clear console and print account creation screen
                clearConsole();
                printCreateAccountScreen();
                break;
            }

            // user wants to exit application
            case 3:
            {
                exitApplication();
                break;
            }

            // user made invalid choice
            default:
            {
                // clear console
                clearConsole();

                // print error message and reprint login screen
                System.out.println("You have made an invalid choice");
                printLoginScreen();
                break;
            }
        }
    }

    /*  
     *  Method to clear console/terminal
     */
    public static void clearConsole()
    {
        // try clearing console
        try
        {
            // check if System OS is Windows
            if(System.getProperty("os.name").contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else // non-Windows OS
            {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("Unable to clear terminal.");
            System.exit(1);
        }
    }

    /*
     *  Method to print account login screen
     */
    public static void printAccountLoginScreen()
    {
        // clear console
        clearConsole();

        // get username
        System.out.print("Username: ");
        String username = InputValidation.sanitizeInput("username", scan.next());

        // get password
        System.out.print("Password: ");
        String password = InputValidation.sanitizeInput("password", scan.next());

        // try and log user in
        AccountHandler account = controller.loginAccount(username, password);

        // check if login was successful
        while(account == null)
        {
            System.out.println("Incorrect username or password");
            printAccountLoginScreen();
        }

        printMainMenuScreen(account);
    }

    /*
     *  Method to print account creation screen
     */
    public static void printCreateAccountScreen()
    {
        // set up account to be created
        AccountHandler newAccount;

        // gather user's information
        String firstName, lastName, username, password, email;

        firstName = getFirstName();
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
        lastName = getLastName();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
        username = setUsername();
        password = setPassword();
        email = setEmail();

        // verify user's information is correct
        System.out.println("The information provided to us is as follows:"
        + "\nName: " + firstName + " " + lastName
        + "\nEmail: " + email
        + "\nUsername: " + username
        + "\nPassword: " + password);
        System.out.print("Is this information correct?(Y/N): ");
        if(InputValidation.sanitizeInput("onlyText", scan.next()) == "y")
        {
            // create new account with provided information
            newAccount = controller.createAccount(firstName, lastName, username, email, password);
        }
        else
        {
            // clear console
            clearConsole();

            // correct incorrect information
            System.out.print("What information is incorrect?"
            + "\n1: Name"
            + "\n2: Username"
            + "\n3: Email"
            + "\n4: Password"
            + "\n5: All information is correct"
            + "\nMake your selection here(Choose only one): ");

            int choice = InputValidation.validateMenuChoice(scan.next());
            while(choice != 5)
            {
                switch(choice)
                {
                    // name is incorrect
                    case 1:
                    {
                        System.out.print("Enter your first name here: ");
                        firstName = InputValidation.sanitizeInput("textOnly", scan.next());
                        while(firstName == null)
                        {
                            System.out.print("Please enter a valid first name: ");
                            firstName = InputValidation.sanitizeInput("textOnly", scan.next());
                        }
                        System.out.print("Enter your last name here: ");
                        lastName = InputValidation.sanitizeInput("textOnly", scan.next());
                        while(lastName == null)
                        {
                            System.out.print("Please enter a valid last name: ");
                            lastName = InputValidation.sanitizeInput("textOnly", scan.next());
                        }
                        break;
                    }

                    // username is incorrect
                    case 2:
                    {
                        System.out.print("Please enter your desired username: ");
                        username = InputValidation.sanitizeInput("username", scan.next());
                        while(username == null || OracleAccount.availableUsername(username))
                        {
                            if((!(OracleAccount.availableUsername(username))) && username != null)
                            {
                                System.out.print("Username is already taken, please enter a new username: ");
                                username = InputValidation.sanitizeInput("username", scan.next());
                            }
                            else
                            {
                                System.out.print("Please enter a valid username: ");
                                username = InputValidation.sanitizeInput("username", scan.next());
                            }
                        }
                        break;
                    }

                    // email is incorrect
                    case 3:
                    {
                        System.out.print("Please enter your email: ");
                        email = InputValidation.sanitizeInput("email", scan.next());
                        while(email == null)
                        {
                            System.out.print("Please enter a valid email: ");
                            email = InputValidation.sanitizeInput("email", scan.next());
                        }
                        break;
                    }

                    // password is incorrect
                    case 4:
                    {
                        System.out.print("Please enter your desired password: ");
                        password = InputValidation.sanitizeInput("password", scan.next());
                        while(password == null)
                        {
                            System.out.print("Please enter a valid password: ");
                            password = InputValidation.sanitizeInput("password", scan.next());
                        }
                        break;
                    }

                    // everything is correct
                    case 5:
                    {
                        // create new account with provided information
                        newAccount = controller.createAccount(firstName, lastName, username, email, password);
                        break;
                    }

                    // invalid selection
                    default:
                    {
                        System.out.println("Invalid menu choice.");
                        break;
                    }
                }
                if(choice == 5)
                {
                    break;
                }

                // clear console before next loop
                clearConsole();

                System.out.print("What information is incorrect?"
                + "\n1: Name"
                + "\n2: Username"
                + "\n3: Email"
                + "\n4: Password"
                + "\n5: All information is correct"
                + "\nMake your selection here(Choose only one): ");
    
                choice = InputValidation.validateMenuChoice(scan.next());
            }
        }

        // clear console and send user to login screen
        clearConsole();
        printAccountLoginScreen();
    }

    /*
     *  Method to gracefully end the application
     */
    public static void exitApplication()
    {
        // close scanner
        scan.close();

        // print goodbye message
        System.out.println("Thank you for using our application!\nPlease come back soon!");

        // end program
        System.exit(0);
    }

    /*
     *  Method to print the initial screen when first running the application
     */
    public static void printInitialScreen()
    {
        // print welcome message
        System.out.println("ROUTINE HELPER");

        // send user to login screen
        printLoginScreen();
    }

    /*
     *  Method to print main menu screen after a user has logged in
     *  @param curAccount Current account user is "logged into"
     */
    public static void printMainMenuScreen(AccountHandler curAccount)
    {
        // clear console
        clearConsole();

        // print main menu
        System.out.print("Where would you like to go: "
        + "\n1: Routine/Task"
        + "\n2: Category"
        + "\n3: To-Do List"
        + "\n4: Account Statistics"
        + "\n5: Sign Out"
        + "\n6: Exit Application"
        + "\nMake your selection here: ");

        // get choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // perform task user chose
        switch(choice)
        {
            // routines/tasks
            case 1:
            {
                printRoutineScreen(curAccount);
                printMainMenuScreen(curAccount);
                break;
            }

            // category
            case 2:
            {
                printCategoriesScreen(curAccount);
                printMainMenuScreen(curAccount);
                break;
            }

            // to-do list
            case 3:
            {
                printToDoListScreen(curAccount);
                printMainMenuScreen(curAccount);
                break;
            }

            // account statistics
            case 4:
            {
                printAccountScreen(curAccount);
                printMainMenuScreen(curAccount);
                break;
            }

            // sign out
            case 5:
            {
                curAccount = null;
                controller.setAccount(null);
                printAccountLoginScreen();
                break;
            }

            // exit application
            case 6:
            {
                curAccount = null;
                controller.setAccount(null);
                exitApplication();
                break;
            }

            // invalid choice
            default:
            {
                System.out.println("Invalid menu choice.");
                printMainMenuScreen(curAccount);
                break;
            }
        }
    }

    /*
     *  Method to print the category management screen
     *  @param curAccount Current account user is "logged into"
     */
    public static void printCategoriesScreen(AccountHandler curAccount)
    {
        // print category screen
        System.out.print("What would you like to do:"
        + "\n1: View current categories"
        + "\n2: Create new category"
        + "\n3: Delete a category"
        + "\n4: Go back to main menu"
        + "\nEnter your selection here: ");

        // sanitize user's choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // clear console
        clearConsole();

        // perform action based on user's choice
        switch(choice)
        {
            // view current categories
            case 1:
            {
                curAccount.viewCategories();
                break;
            }

            // create category
            case 2:
            {
                curAccount.createCategory();
                break;
            }

            // delete category
            case 3:
            {
                curAccount.deleteCategory();
                break;
            }

            // back to main menu
            case 4:
            {
                return;
                break;
            }

            // invalid option
            default:
            {
                System.out.println("Invalid menu choice");
                printCategoriesScreen(curAccount);
                break;
            }
        }
    }

    /*
     *  Method to print the routine management screen
     *  @param curAccount Current account user is "logged into"
     */
    public static void printRoutineScreen(AccountHandler curAccount)
    {
        // print routine screen
        System.out.print("What would you like to do:"
        + "\n1: View current routines"
        + "\n2: Create new routine"
        + "\n3: Update a routine"
        + "\n4: Delete a routine"
        + "\n5: Go back to main menu"
        + "\nEnter your selection here: ");

        // sanitize user's choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // clear console
        clearConsole();

        // perform action based on user's choice
        switch(choice)
        {
            // view current routines
            case 1:
            {
                curAccount.viewRoutines();
                break;
            }

            // create routine
            case 2:
            {
                curAccount.createRoutine();
                break;
            }
            
            // update a routine
            case 3:
            {
                curAccount.updateRoutine();
                break;
            }

            // delete routine
            case 4:
            {
                curAccount.deleteRoutine();
                break;
            }

            // back to main menu
            case 5:
            {
                return;
                break;
            }

            // invalid option
            default:
            {
                System.out.println("Invalid menu choice");
                printRoutineScreen(curAccount);
                break;
            }
        }
    }

    /*
     *  Method to print the to-do list management screen
     *  @param curAccount Current account user is "logged into"
     */
    public static void printToDoListScreen(AccountHandler curAccount)
    {
        // print to-do list screen
        System.out.print("What would you like to do:"
        + "\n1: View current to-do list"
        + "\n2: Create new to-do list"
        + "\n3: Update the to-do list"
        + "\n4: Delete a to-do list"
        + "\n5: Go back to main menu"
        + "\nEnter your selection here: ");

        // sanitize user's choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // clear console
        clearConsole();

        // perform action based on user's choice
        switch(choice)
        {
            // view current to-do list
            case 1:
            {
                curAccount.viewToDoList();
                break;
            }

            // create category
            case 2:
            {
                curAccount.createToDoList();
                break;
            }

            // update to-do list
            case 3:
            {
                curAccount.updateToDoList();
                break;
            }

            // delete to-do list
            case 4:
            {
                curAccount.deleteToDoList();
                break;
            }

            // back to main menu
            case 5:
            {
                return;
                break;
            }

            // invalid option
            default:
            {
                System.out.println("Invalid menu choice");
                printToDoListScreen(curAccount);
                break;
            }
        }
    }

    /*
     *  Method to print the account management screen
     *  @param curAccount Current account user is "logged into"
     */
    public static void printAccountScreen(AccountHandler curAccount)
    {
        // print category screen
        System.out.print("What would you like to do:"
        + "\n1: View account information"
        + "\n2: Update account"
        + "\n3: Delete account"
        + "\n4: Go back to main menu"
        + "\nEnter your selection here: ");

        // sanitize user's choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // clear console
        clearConsole();

        // perform action based on user's choice
        switch(choice)
        {
            // view current categories
            case 1:
            {
                curAccount.viewAccount();
                break;
            }

            // create category
            case 2:
            {
                curAccount.updateAccount();
                break;
            }

            // delete category
            case 3:
            {
                curAccount.deleteAccount();
                break;
            }

            // back to main menu
            case 4:
            {
                return;
                break;
            }

            // invalid option
            default:
            {
                System.out.println("Invalid menu choice");
                printCategoriesScreen(curAccount);
                break;
            }
        }
    }

    /*  Method to get first name from user
    *
    *   @return Returns string representation of user's first name
    */
    private static String getFirstName()
    {
        // clear console
        clearConsole();

        String firstName;
        // get first name
        System.out.print("Please enter your first name: ");
        firstName = InputValidation.sanitizeInput("textOnly", scan.next());
        while(firstName == null)
        {
            System.out.print("Please enter a valid first name: ");
            firstName = InputValidation.sanitizeInput("textOnly", scan.next());
        }

        return firstName;
    }

    /*  Method to get last name from user
    *
    *   @return Returns string representation of user's last name
    */ 
    private static String getLastName()
    {
        // clear console
        clearConsole();
        
        String lastName;
        // get last name
        System.out.print("Please enter your last name: ");
        lastName = InputValidation.sanitizeInput("textOnly", scan.next());
        while(lastName == null)
        {
            System.out.print("Please enter a valid last name: ");
            lastName = InputValidation.sanitizeInput("textOnly", scan.next());
        }

        return lastName;
    }

    /*  Method to get username from user
    *
    *   @return Returns string representation of user's desired username
    */
    private static String setUsername()
    {
        // clear console
        clearConsole();
        
        String username;
        // get username
        System.out.print("Please enter your desired username: ");
        username = InputValidation.sanitizeInput("username", scan.next());
        while(username == null || OracleAccount.availableUsername(username))
        {
            if((!(OracleAccount.availableUsername(username))) && (username != null))
            {
                System.out.print("Username is already taken. Please enter a new username: ");
                username = InputValidation.sanitizeInput("username", scan.next());
            }
            else
            {
                System.out.print("Please enter a valid username: ");
                username = InputValidation.sanitizeInput("username", scan.next());
            }
        }

        return username;
    }

    /* Method to get intial password form user
    *
    *   @return Returns string representation of user's password
    */
    private static String setPassword()
    {
        // clear console
        clearConsole();
        
        String password;
        // get password
        System.out.print("Please enter your desired password. The requirements for a password are"
        + "\n-Between 8 and 30 characters"
        + "\n-Must contain at least one uppercase letter, lowercase letter, number and/or special character"
        + "\n-Valid special characters are: '.', '_', '?', '!', '$'"
        + "\nEnter your password here: ");
        password = InputValidation.sanitizeInput("password", scan.next());
        while(password == null)
        {
            System.out.print("Please enter a valid password: ");
            password = InputValidation.sanitizeInput("password", scan.next());
        }

        return password;
    }

    /*  Method to get email from user
    *
    *   @return Returns string representation of a user's email
    */   
    private static String setEmail()
    {
        // clear console
        clearConsole();

        String email;
        // get email
        System.out.print("Please enter your email: ");
        email = InputValidation.sanitizeInput("email", scan.next());
        while(email == null)
        {
            System.out.print("Please enter a valid email: ");
            email = InputValidation.sanitizeInput("email", scan.next());
        }

        return email;
    }
}
