package Account;

import Database.*;

// Interface used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: Ozctober 29, 2024
public interface AccountHandler {
    

    public AccountHandler deleteAccount(String username);
    public AccountHandler getAccount(String username);
    public boolean updateAccount();
    public Schedule generateSchedule(int numDays);
    public void analyzeSchedule(int numDays);
    public void compareSchedule(Schedule otherSchedule, int numDays);
    // public boolean updateAccount();
    // Should what's below be the same as ^ instead?
    AccountHandler updateAccount();
    AccountHandler deleteAccount();
    void viewCategories();
    void createRoutine();
    void updateRoutine();
    void deleteRoutine();
    void viewToDoList();
    void createToDoList();
    void updateToDoList();
    void deleteToDoList();
    AccountHandler getAccount(String username);

    // class to group user's first and last name together
    public class Name
    {
        private static String firstName;
        private static String lastName;

        public Name(String firstName, String lastName)
        {
            Name.firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
            Name.lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        }

        public static String getName()
        {
            return firstName + " " + lastName;
        }
    }
}
