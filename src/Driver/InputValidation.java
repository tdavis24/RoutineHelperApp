package Driver;

import java.util.InputMismatchException;
import java.util.regex.*;
import Account.*;

// Class used to sanitize user input and pass objects between UserInterface and Account
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: November 5, 2024
public class InputValidation {

    // create Pattern & Matcher objects
    private static Pattern usernamePattern = Pattern.compile("^(([A-Za-z0-9._]{1})([A-Za-z0-9._]*)){8,30}$");
    private static Pattern passwordPattern = Pattern.compile("^(([A-Za-z0-9._!?$]{1})([A-Za-z0-9._!?$]*)){8,30}$");
    private static Pattern textOnlyPattern = Pattern.compile("^[A-Za-z]*$");
    private static Pattern emailPattern = Pattern.compile("^[\\\\w!#$%&'*+/=?`{|}~^.-]+@[\\\\w.-]+\\\\.[a-zA-Z]{2,}$");
    private static Matcher usernameMatcher;
    private static Matcher passwordMatcher;
    private static Matcher textMatcher;
    private static Matcher emailMatcher;

    /*  Method used to sanitize user menu choice input
    *
    *   @param input A string representation of a user's menu choice
    *   @return Returns the integer representation of a menu choice
    *   @return Returns -1 if paramenter is not a valid integer or cannot be converted to an integer
    */
    public static int validateMenuChoice(String input)
    {
        // create return value
        int retVal;

        // try sanitizing input
        try
        {
            // first, check if input string has zero or more than two characters (invalid number/string)
            if(input.length() == 0 || input.length() > 2)
            {
                throw new InputMismatchException();
            }

            // then check if able to convert input to int
            try
            {
                retVal = Integer.parseInt(input);
            }
            catch(Exception e)
            {
                throw e;
            }
        }
        catch(Exception e)
        {
            return -1;
        }
        return retVal;
    }

    /*  Method to sanitize strictly text input
    *
    *   @param input String representation of user input
    *   @return Return parameter in all lower-case characters if string is text-only (only alphabetic characters)
    *   @return Return null if string is invalid (not only alphabetic characters)
    */
    private static String sanitizeOnlyText(String input)
    {
        // create return value
        String retVal;

        // try sanitizing input
        try
        {
            // check if input string is alphabetic characters only
            textMatcher = textOnlyPattern.matcher(input);
            if(textMatcher.matches())
            {
                retVal = input.toLowerCase();
            }
            else
            {
                throw new InputMismatchException();
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return retVal;
    }

    /*  Method to sanitize input that could contain special characters
    *
    *   @param inputType String representing type of input to be validated
    *   @param input String representation of user input
    *   @return Returns input parameter if string is valid for inputType parameter
    *   @return Return null if input is invalid for inputType
    */
    public static String validateInput(String inputType, String input)
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

                // password
                case "password":
                {
                    retVal = verifyPassword(input);
                    break;
                }

                // email
                case "email":
                {
                    retVal = verifyEmail(input);
                    break;
                }

                // invalid input type
                default:
                {
                    throw new InputMismatchException();
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }

        return retVal;
    }

    /*  Method to verify a username is valid
    *
    *   @param input String representation of user input
    *   @return Return input if string is valid
    *   @return Return null if input is invalid
    */
    private static String verifyUsername(String input)
    {
        // create return value
        String retVal;

        // verify username is valid
        try
        {
            // check if username is valid
            usernameMatcher = usernamePattern.matcher(input);
            if(usernameMatcher.matches())
            {
                retVal = input;
            }
            else
            {
                throw new InputMismatchException();
            }   
        }
        catch(Exception e)
        {
            return null;
        }

        return retVal;
    }

    /*  Method to verify a password is valid
    *
    *   @param input String representation of user input
    *   @return Return input if string is valid
    *   @return Return null if input is invalid
    */
    private static String verifyPassword(String input)
    {
        // create return value
        String retVal;

        // verify password is valid
        try
        {
            // check if input is valid
            passwordMatcher = passwordPattern.matcher(input);
            if(passwordMatcher.matches())
            {
                retVal = input;
            }
            else
            {
                throw new InputMismatchException();
            }
        }
        catch(Exception e)
        {
            return null;
        }

        return retVal;
    }

    /*  Method to verify an email is valid
    *
    *   @param input String representation of user input
    *   @return Return input if string is valid
    *   @return Return null if input is invalid
    */
    private static String verifyEmail(String input)
    {
        // create return value
        String retVal;

        // verify email is valid
        try
        {
            // check if email is valid
            emailMatcher = emailPattern.matcher(input);
            if(emailMatcher.matches())
            {
                retVal = input;
            }
            else
            {
                throw new InputMismatchException();
            }
        }
        catch(Exception e)
        {
            return null;
        }

        return retVal;
    }
}
