package Driver;

import java.util.*;

// Driver class for the Routine Helper App
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class RoutineHelper{

    // open scanner to receive user input
    private static Scanner scan = new Scanner(System.in);
    
    public static void main(String [] args)
    {
        runApplication();
    }

    /*  Method to print login screen
    */
    private static void loginScreen()
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
                userLogin();
                break;
            }

            // user wants to create an account
            case 2:
            {
                // clear console and print account creation screen
                clearConsole();
                createAccount();
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
                loginScreen();
                break;
            }
        }
    }

    /*  Method to run application
    */
    private static void runApplication()
    {
        // print welcome message
        System.out.println("ROUTINE HELPER");

        // print login screen
        loginScreen();
    }

    /*  Method to gracefully end application
    */
    private static void exitApplication()
    {
        // close scanner
        scan.close();

        // print goodbye message
        System.out.println("Thank you for using our application!\nPlease come back soon!");

        // end program
        System.exit(0);
    }

    /*  Method to clear console/terminal
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

    /*  Mehtod to print screen for user to login
    */
    private static void userLogin()
    {
        // get username
        System.out.print("Username: ");
        String username = InputValidation.sanitizeInput("username", scan.next());

        // check if username matches any account
        AccountHandler curAccount = Account.retrieveAccount(username);

        // get password
        System.out.print("Password: ");
        String password = InputValidation.sanitizeInput("password", scan.next());

        // check if password matches account
        boolean passwordMatches = (curAccount.getPassword() == password);

        if(username == null || password == null || curAccount == null || !(passwordMatches))
        {
            // clear console and prompt user for new login credentials
            clearConsole();
            System.out.print("Incorrect username or password");
            userLogin();
        }
        else
        {
            // clear console
            clearConsole();

            // user is now "logged in"
            // send user to main application screen
            mainMenu(curAccount);
        }
    }

    /*  Method to print screen for user to create an account
    */
    private static void createAccount()
    {
        // set up account to be created
        AccountHandler newAccount;

        // gather user's information
        String firstName, lastName, username, password, email;

        firstName = getFirstName();
        lastName = getLastName();
        username = getUsername();
        password = getPassword();
        email = getEmail();

        // verify user's information is correct
        System.out.println("The information provided to us is as follows:"
        + "\nName: " + Account.Name.getName()
        + "\nEmail: " + email
        + "\nUsername: " + username
        + "\nPassword: " + password);
        System.out.print("Is this information correct?(Y/N): ");
        if(InputValidation.sanitizeInput("onlyText", scan.next()) == "y")
        {
            // create new account with provided information
            newAccount = new Account(username, email, password, firstName, lastName);
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
                        while(username == null || Account.availableUsername(username))
                        {
                            if((!(Account.availableUsername(username))) && username != null)
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
                        newAccount = new Account(username, email, password, firstName, lastName);
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
        userLogin();
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
    private static String getUsername()
    {
        // clear console
        clearConsole();
        
        String username;
        // get username
        System.out.print("Please enter your desired username: ");
        username = InputValidation.sanitizeInput("username", scan.next());
        while(username == null || Account.availableUsername(username))
        {
            if((!(Account.availableUsername(username))) && (username != null))
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

    /* Method to get password form user
    *
    *   @return Returns string representation of user's password
    */
    private static String getPassword()
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
    private static String getEmail()
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

    /*  Method to print main menu after user successfully logs in
    *
    *   @param curAccount The current account "logged into" by the user
    */
    private static void mainMenu(AccountHandler curAccount)
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
                curAccount.viewRoutines();
                mainMenu(curAccount);
                break;
            }

            // category
            case 2:
            {
                curAccount.viewCategories();
                mainMenu(curAccount);
                break;
            }

            // to-do list
            case 3:
            {
                curAccount.viewToDoList();
                mainMenu(curAccount);
                break;
            }

            // account statistics
            case 4:
            {
                curAccount.viewAccountStatistics();
                mainMenu(curAccount);
                break;
            }

            // sign out
            case 5:
            {
                curAccount = null;
                loginScreen();
                break;
            }

            // exit application
            case 6:
            {
                exitApplication();
                break;
            }

            // invalid choice
            default:
            {
                System.out.println("Invalid menu choice.");
                mainMenu(curAccount);
                break;
            }
        }
    }
}