package Driver;

import Account.*;
import Database.OracleDatabase;

// Controller class used to pass information between the UI and the database
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: November 15, 2024
public class Controller {
    private AccountHandler curAccount;

    public boolean updateAccount() {
        
    }
    
    public AccountHandler deleteAccount() {
        
    }
    
    public boolean createTask(String name, String information, Category category) {
        
    }

    /*
     *  Method to change instance variable of Controller class
     * 
     *  @param account Account to change instance variable to
     * 
     *  @return Return true if instance variable was changed
     *  @retrun Return false if instance variable could not be changed
     */
    public boolean setAccount(AccountHandler account)
    {
        try
        {
            this.curAccount = account;
            return true;
        }
        catch(Exception e)
        {
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
            return this.curAccount;
        }
        else
        {
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
    public AccountHandler loginAccount(String username, String password)
    {
        // try logging into account
        try
        {
            AccountHandler account = OracleDatabase.getAccount(username);
            if(account == null)
            {
                throw new Exception();
            }
            else
            {
                boolean PWMatches = account.getPassword().equals(password);
                if(!PWMatches)
                {
                    throw new Exception();
                }
                else
                {
                    setAccount(account);
                }
            }

            return account;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
