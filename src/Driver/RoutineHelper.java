package Driver;

import java.util.*;
import java.util.regex.*;
import Account.*;

// Driver class for the Routine Helper App
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class RoutineHelper{
    // open scanner to receive user input
    private static Scanner scan = new Scanner(System.in);

    // create Pattern & Matcher objects
    private static Pattern pattern;
    private static Matcher matcher;
    
    public static void main(String [] args)
    {
        runApplication();
    }


    // method used to sanitize user input
    private static int sanitizeMenuChoice(String input)
    {
        // create return value
        int retVal;

        // try sanitizing input
        try
        {
            // first, check if input string has more than one character (invalid number/string)
            if(input.length() > 1)
            {
                throw new InputMismatchException();
            }

            // then check if able to convert input to int
            try
            {
                retVal = Integer.parseInt(input);
            }
            catch(NumberFormatException e)
            {
                throw e;
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("Invalid choice");
            return -1;
        }
        catch(InputMismatchException e)
        {
            System.out.println("Invalid choice");
            return -1;
        }
        catch(Exception e)
        {
            System.out.println("Invalid choice");
            return -1;
        }
        return retVal;
    }

    // login screen
    private static void loginScreen()
    {
        // print login screen
        System.out.print("Would you like to sign in or create an account?\n1: Sign in\n2: Create account\n3: Exit application\nEnter your selection here: ");

        // receive user input
        String input = scan.next();

        // sanitize input
        int choice = sanitizeMenuChoice(input);

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

    // method to run application
    private static void runApplication()
    {
        // print welcome message
        System.out.println("ROUTINE HELPER");

        // print login screen
        loginScreen();
    }

    // method to gracefully end application
    private static void exitApplication()
    {
        // close scanner
        scan.close();

        // print goodbye message
        System.out.println("Thank you for using our application!\nPlease come back soon!");

        // end program
        System.exit(0);
    }

    // method to clear console
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

    // screen for user to login
    private static void userLogin()
    {

    }

    // screen for user to create an account
    private static void createAccount()
    {
        // gather user's information
        String firstName, lastName, username, password, email;

        // get first name
        System.out.print("Please enter your first name: ");
        firstName = sanitizeOnlyText(scan.next());
        while(firstName == null)
        {
            System.out.print("Please enter a valid first name: ");
            firstName = sanitizeOnlyText(scan.next());
        }

        // get last name
        System.out.print("Please enter your last name: ");
        lastName = sanitizeOnlyText(scan.next());
        while(lastName == null)
        {
            System.out.print("Please enter a valid last name: ");
            lastName = sanitizeOnlyText(scan.next());
        }

        // get username
    }

    // method to sanitize strictly text input
    private static String sanitizeOnlyText(String input)
    {
        // create return value
        String retVal;

        // try sanitizing input
        try
        {
            // check if input string is alphabetic characters only
            boolean matches = Pattern.matches("^[A-Za-z]*$", input);
            if(matches)
            {
                retVal = input.toLowerCase();
            }
            else
            {
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException e)
        {
            System.out.println("Invalid input");
            return null;
        }
        catch(Exception e)
        {
            System.out.println("Invalid input");
            return null;
        }
        return retVal;
    }

    // method to sanitize input that could contain special characters
    private static String sanitizeInput(String inputType, String input)
    {
        // create return value
        String retVal;

        // try sanitizing input
        try
        {
            // check what we are trying to sanitize
            switch(inputType)
            {
                // text only input
                case "textOnly":
                {
                    retVal = sanitizeOnlyText(input);
                    break;
                }

                // username
                case "username":
                {
                    retVal = verifyUsername(input);
                    break;
                }
            }
        }
        catch(Exception e) {}

        return retVal;
    }

    // method to verify a username is valid
    private static String verifyUsername(String input)
    {
        // create return value
        String retVal;

        // verify username is valid
        try
        {
            boolean matches = Pattern.matches("^[A-Za-z0-9._]${8, 20}", input);
        }
        catch(Exception e)
        {

        }

        return retVal;
    }
}