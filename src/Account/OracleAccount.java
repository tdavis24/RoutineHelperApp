package Account;

import java.sql.*;
import Database.*;
import Driver.*;
import java.util.Scanner;

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
                boolean validUsername = InputValidation.sanitizeInput("username", username) != null;
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

    public boolean updateAccount()
    {
        CommandLineInterface.clearConsole();
        // print current account information
        AccountHandler curAccount = getAccount(this.username);
        System.out.println(curAccount.printInformation());
        System.out.print("What would you like to change with your account:"
        + "\n1: Change username"
        + "\n2: Change password"
        + "\n3: Change email"
        + "Make your selection here: ");

        int choice = InputValidation.sanitizeMenuChoice(scan.next());

        switch(choice)
        {
            // change username
            case 1:
            {

            }

            // change password
            case 2:
            {

            }

            // change email
            case 3:
            {
                
            }

            // invalid choice
            default:
            {
                CommandLineInterface.clearConsole();
                System.out.println("Invalid choice.");
                updateAccount();
            }
        }
        boolean accountUpdated = false;

        return accountUpdated;
    }

    public void generateSchedule(int numDays){
        //get tasks associated with account
        int numResults;

        Task[] results = new Task[numResults];

        
    }

    public void analyzeSchedule(int numDays){

    }

    public void compareSchedule(Schedule otherSchedule, int numDays){

    }

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