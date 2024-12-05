package Account;

// Class used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class OracleAccount implements AccountHandler{

    // create class variables
    private String username;
    private String email;
    private String password;
    private Name name;

    public OracleAccount(String username, String email, String password, String firstName, String lastName)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = new Name(firstName, lastName);
    }

    public AccountHandler deleteAccount(String username)
    {
        AccountHandler deletedAccount = null;

        return deletedAccount;
    }

    public AccountHandler getAccount(String username)
    {
        AccountHandler curAccount = null;

        return curAccount;
    }

    public boolean updateAccount()
    {
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
}