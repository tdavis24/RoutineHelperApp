package Driver;

import java.util.Scanner;
import Account.*;

// UI class for the Routine Helper App
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: November 13, 2024
public class CommandLineInterface {
    // open new scanner
    private static Scanner scan = new Scanner(System.in);
    
    // create Controller object
    private static Controller controller = new Controller();

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
        int choice = InputValidation.sanitizeMenuChoice(input);

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
    private static void clearConsole()
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

            int choice = InputValidation.sanitizeMenuChoice(scan.next());
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
    
                choice = InputValidation.sanitizeMenuChoice(scan.next());
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
        int choice = InputValidation.sanitizeMenuChoice(scan.next());

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
        int choice = InputValidation.sanitizeMenuChoice(scan.next());

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
        int choice = InputValidation.sanitizeMenuChoice(scan.next());

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
        int choice = InputValidation.sanitizeMenuChoice(scan.next());

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
        int choice = InputValidation.sanitizeMenuChoice(scan.next());

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
