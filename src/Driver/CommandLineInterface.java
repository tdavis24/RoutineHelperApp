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
    private static List<Task> tasks = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();


    // Use Case 3.1.1: Add a Routine
    public static void addRoutine() {
        System.out.print("Enter the name of the routine: ");
        String name = scan.nextLine();
        System.out.print("Enter the information about the routine: ");
        String information = scan.nextLine();
        System.out.print("Enter the deadline for the routine: ");
        String deadline = scan.nextLine();
        System.out.print("Enter the recurrence interval (Options: Daily, Weekly, Bi-Weekly, Monthly, Yearly): ");
        String recurrenceInterval = scan.nextLine();
        System.out.print("Enter the category for the routine: ");
        String categoryName = scan.nextLine();

        Category category = findOrCreateCategory(categoryName, "general");

        Task newTask = new Task(name, information, deadline, category, recurrenceInterval);

        tasks.add(newTask);

        System.out.println("Routine added successfully: " + name);
    }

    // Helper method to find existing category or create a new one
    private static Category findOrCreateCategory(String categoryName, String type) {
        for (Category category : categories) {
            if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }
        Category newCategory = new Category(categoryName, type);
        categories.add(newCategory);
        System.out.println("New category created: " + categoryName);
        return newCategory;
    }

    // Use Case 3.1.2: Set Reminder for Routine
    // public static void setReminderForRoutine() {
    //     System.out.print("Enter the name of the routine to set a reminder for: ");
    //     String name = scan.next();
    //     for (Routine routine : routines) {
    //         if (routine.name.equalsIgnoreCase(name)) {
    //             System.out.print("Enter reminder time (yyyy-MM-dd HH:mm:ss): ");
    //             String reminderTimeStr = scan.next();
    //             try {
    //                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //                 Date reminderTime = dateFormat.parse(reminderTimeStr);
    //                 routine.setReminder(reminderTime);
    //                 System.out.println("Reminder set successfully for routine: " + name);
    //             } catch (ParseException e) {
    //                 System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm:ss.");
    //             }
    //             return;
    //         }
    //     }
    //     System.out.println("Routine not found: " + name);
    // }

    // Use Case 3.1.3: Retrieve Routine Progress
    // public static void retrieveRoutineProgress() {
    //     System.out.println("Routine Progress:");
    //     for (Routine routine : routines) {
    //         System.out.println("Routine: " + routine.name + " - Completion: [Data to be integrated]");
    //     }
    // }

    // Use Case 3.1.4: Edit a Routine
    public static void editRoutine() {
        System.out.print("Enter the name of the routine to edit: ");
        String name = scan.nextLine();
        Task foundTask = null;
        for (Task task : tasks) {
            if (task.getName() != null && task.getName().equalsIgnoreCase(name)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask == null) {
            System.out.println("Routine not found: " + name);
            return;
        }

        System.out.println("What would you like to edit?");
        System.out.println("1: Update Information and Deadline");
        System.out.println("2: Update Recurrence Interval");
        System.out.println("3: Update Category");
        System.out.println("4: Back to Routine Menu");
        System.out.print("Enter your selection here: ");
        int choice = InputValidation.validateMenuChoice(scan.next());

        switch (choice) {
            case 1:
                System.out.print("Enter new information: ");
                String newInfo = scan.nextLine();
                System.out.print("Enter new deadline (yyyy-MM-dd): ");
                String newDeadline = scan.nextLine();
                foundTask.updateTask(newInfo, newDeadline);
                break;
            case 2:
                System.out.print("Enter new recurrence interval (Daily, Weekly, Bi-Weekly, Monthly, Yearly, None): ");
                String newRecurrence = scan.nextLine();
                foundTask.setRecurrenceInterval(newRecurrence);
                break;
            case 3:
                System.out.print("Enter new category name: ");
                String newCategoryName = scan.nextLine();
                Category newCategory = findOrCreateCategory(newCategoryName, "general");
                foundTask.setCategory(newCategory);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
                editRoutine();
                break;
        }

        System.out.println("Routine (task) updated successfully: " + name);
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

    // Use Case 3.1.6: Create Account
    public static void createAccount() {
        System.out.print("Enter your first name: ");
        String firstName = scan.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scan.nextLine();
        System.out.print("Enter your desired username: ");
        String username = scan.nextLine();
        System.out.print("Enter your email: ");
        String email = scan.nextLine();
        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        AccountHandler newAccount = controller.createAccount(firstName, lastName, username, email, password);
        if (newAccount != null) {
            System.out.println("Account created successfully for: " + firstName + " " + lastName);
        } else {
            System.out.println("Account creation failed. Please try again.");
        }
    }

    // Use Case 3.1.7: Forgot Password
    public static void forgotPassword() {
        System.out.print("Enter your email address to reset password: ");
        String email = scan.nextLine();
        System.out.println("A password reset link has been sent to: " + email);
    }

    // Use Case 3.1.8: Update Account Information
    public static void updateAccountInformation() {
        System.out.print("Enter your username to update account information: ");
        String username = scan.nextLine();
        System.out.print("Enter new email: ");
        String newEmail = scan.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scan.nextLine();
        boolean updated = controller.updateAccount(username, "email", newEmail) &&
                            controller.updateAccount(username, "password", newPassword);
        if (updated) {
            System.out.println("Account information updated for username: " + username);
        } else {
            System.out.println("Failed to update account information. Please try again.");
        }
    }

    // Use Case 3.1.9: Create Routine Category
    public static void createRoutineCategory() {
        System.out.print("Enter the name of the new category: ");
        String categoryName = scan.nextLine();
        Category newCategory = findOrCreateCategory(categoryName, "general");
        System.out.println("Category created successfully: " + categoryName);
    }

    // Use Case 3.1.10: Change Password
    public static void changePassword() {
        System.out.print("Enter your current password: ");
        String currentPassword = scan.nextLine();
        // Assuming AccountHandler has a method to verify password
        boolean correctPassword = controller.verifyPassword(currentPassword);
        if (correctPassword) {
            System.out.print("Enter your new password: ");
            String newPassword = scan.nextLine();
            boolean updated = controller.updateAccount(controller.getCurrentUsername(), "password", newPassword);
            if (updated) {
                System.out.println("Password has been changed successfully.");
            } else {
                System.out.println("Failed to change password. Please try again.");
            }
        } else {
            System.out.println("Incorrect current password.");
        }
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

    // Adding options to handle tasks in the To-Do List
    public static void manageToDoList() {
        System.out.println("What would you like to do?\n1: Add Task\n2: Delete Task\n3: Display To-Do List\n4: Back to Main Menu");
        int choice = InputValidation.validateMenuChoice(scan.next());
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
        if(InputValidation.validateInput("onlyText", scan.next()) == "y")
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
                        firstName = InputValidation.validateInput("textOnly", scan.next());
                        while(firstName == null)
                        {
                            System.out.print("Please enter a valid first name: ");
                            firstName = InputValidation.validateInput("textOnly", scan.next());
                        }
                        System.out.print("Enter your last name here: ");
                        lastName = InputValidation.validateInput("textOnly", scan.next());
                        while(lastName == null)
                        {
                            System.out.print("Please enter a valid last name: ");
                            lastName = InputValidation.validateInput("textOnly", scan.next());
                        }
                        break;
                    }

                    // username is incorrect
                    case 2:
                    {
                        System.out.print("Please enter your desired username: ");
                        username = InputValidation.validateInput("username", scan.next());
                        while(username == null || OracleAccount.availableUsername(username))
                        {
                            if((!(OracleAccount.availableUsername(username))) && username != null)
                            {
                                System.out.print("Username is already taken, please enter a new username: ");
                                username = InputValidation.validateInput("username", scan.next());
                            }
                            else
                            {
                                System.out.print("Please enter a valid username: ");
                                username = InputValidation.validateInput("username", scan.next());
                            }
                        }
                        break;
                    }

                    // email is incorrect
                    case 3:
                    {
                        System.out.print("Please enter your email: ");
                        email = InputValidation.validateInput("email", scan.next());
                        while(email == null)
                        {
                            System.out.print("Please enter a valid email: ");
                            email = InputValidation.validateInput("email", scan.next());
                        }
                        break;
                    }

                    // password is incorrect
                    case 4:
                    {
                        System.out.print("Please enter your desired password: ");
                        password = InputValidation.validateInput("password", scan.next());
                        while(password == null)
                        {
                            System.out.print("Please enter a valid password: ");
                            password = InputValidation.validateInput("password", scan.next());
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
        + "\n4: Schedule"
        + "\n5: Account Statistics"
        + "\n6: Sign Out"
        + "\n7: Exit Application"
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

            case 4:
            {
                printScheduleScreen(curAccount);
                printMainMenuScreen(curAccount);
            }

            // account statistics
            case 5:
            {
                printAccountScreen(curAccount);
                printMainMenuScreen(curAccount);
                break;
            }

            // sign out
            case 6:
            {
                curAccount = null;
                controller.setAccount(null);
                printAccountLoginScreen();
                break;
            }

            // exit application
            case 7:
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
        // print account screen
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
            // view current account
            case 1:
            {
                curAccount.viewAccount();
                break;
            }

            // update account
            case 2:
            {
                CommandLineInterface.clearConsole();
                // print current account information
                curAccount.viewAccount();
                System.out.print("What would you like to change with your account:"
                + "\n1: Change username"
                + "\n2: Change password"
                + "\n3: Change email"
                + "Make your selection here: ");

                int updateChoice = InputValidation.validateMenuChoice(scan.next());
                boolean accountUpdated;

                switch(updateChoice)
                {
                    // change username
                    case 1:
                    {
                        System.out.print("Username Requirements:"
                            + "Length: Between 9-31 characters"
                            + "Valid Characters: Alphanumeric, '.', '_'"
                            + "What would you like your new username to be?: ");
                        String newUsername = InputValidation.validateInput("username", scan.next());
                        while(newUsername == null)
                        {
                            System.out.print("Invalid username. Enter a valid username here: ");
                            newUsername = InputValidation.validateInput("username", scan.next());
                        }

                        accountUpdated = curAccount.updateAccount("username", newUsername);
                    }

                    // change password
                    case 2:
                    {
                        System.out.print("Please enter your current password: ");
                        String curPass = InputValidation.validateInput("password", scan.next());
                        String newPass;
                        boolean correctPassword = curAccount.verifyPassword(curPass);
                        if(correctPassword)
                        {
                            System.out.print("Password Requirements:"
                                + "Length: Between 9-31 characters"
                                + "Valid characters: Alphanumeric, '.', '_', '$', '!', '?'"
                                + "What would you like your new password to be?: ");
                            newPass = InputValidation.validateInput("password", scan.next());
                            while(newPass == null)
                            {
                                System.out.print("Invalid password. Enter a valid password here: ");
                                newPass = InputValidation.validateInput("password", scan.next());
                            }
                        }

                        accountUpdated = curAccount.updateAccount("password", newPass);
                    }

                    // change email
                    case 3:
                    {
                        System.out.print("Please enter your new email here: ");
                        String newEmail = InputValidation.validateInput("email", scan.next());
                        while(newEmail == null)
                        {
                            System.out.print("Invalid email. Enter a valid email here: ");
                            newEmail = InputValidation.validateInput("email", scan.next());
                        }

                        accountUpdated = curAccount.updateAccount("email", newEmail);
                    }

                    // invalid choice
                    default:
                    {
                        CommandLineInterface.clearConsole();
                        System.out.println("Invalid choice.");
                        printAccountScreen(curAccount);
                    }
                }

                if(accountUpdated)
                {
                    System.out.println("Account Updated Successfully.");
                }
                else
                {
                    System.out.println("Account unable to be updated. Please try again");
                }
                break;
            }

            // delete account
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
                printAccountScreen(curAccount);
                break;
            }
        }
    }

    /*
     *  Method to print the schedule screen 
     *  @param curAccount Current account user is "logged into"
     */
    public static void printScheduleScreen(AccountHandler curAccount){
        System.out.println("What would you like to do: "
        + "\n1: Generate Schedule"
        + "\n2: Analyze Schedule"
        + "\n3: Compare Schedule with Another User"
        + "\n4: Return to Main Menu");

        // sanitize user's choice
        int choice = InputValidation.validateMenuChoice(scan.next());

        // clear console
        clearConsole();

        switch(choice)
        {
            case 1:
            {
                //write code for generating schedule
                curAccount.generateSchedule();
            }
            
            case 2:
            {
                //write code for analyzing a schedule
                curAccount.analyzeSchedule();
            }

            case 3:
            {
                //write code for comparing schedule with another user
                //get other schedule object from another account object
                
            }

            case 4:
            {
                
            }

            default:
            {
                //not sure what to do here
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
        firstName = InputValidation.validateInput("textOnly", scan.next());
        while(firstName == null)
        {
            System.out.print("Please enter a valid first name: ");
            firstName = InputValidation.validateInput("textOnly", scan.next());
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
        lastName = InputValidation.validateInput("textOnly", scan.next());
        while(lastName == null)
        {
            System.out.print("Please enter a valid last name: ");
            lastName = InputValidation.validateInput("textOnly", scan.next());
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
        username = InputValidation.validateInput("username", scan.next());
        while(username == null || OracleAccount.availableUsername(username))
        {
            if((!(OracleAccount.availableUsername(username))) && (username != null))
            {
                System.out.print("Username is already taken. Please enter a new username: ");
                username = InputValidation.validateInput("username", scan.next());
            }
            else
            {
                System.out.print("Please enter a valid username: ");
                username = InputValidation.validateInput("username", scan.next());
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
        password = InputValidation.validateInput("password", scan.next());
        while(password == null)
        {
            System.out.print("Please enter a valid password: ");
            password = InputValidation.validateInput("password", scan.next());
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
        email = InputValidation.validateInput("email", scan.next());
        while(email == null)
        {
            System.out.print("Please enter a valid email: ");
            email = InputValidation.validateInput("email", scan.next());
        }

        return email;
    }
}
