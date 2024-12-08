package Account;

import Account.Schedule.*;
import Database.*;

// Interface used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: Ozctober 29, 2024
public interface AccountHandler {

    public AccountHandler getAccount(String username);
    public boolean updateAccount(String infoToUpdate, String information);
    public Schedule generateSchedule(int numDays);
    public void analyzeSchedule(int numDays);
    public void compareSchedule(Schedule otherSchedule, int numDays);
    public void generateScheduleStatistics(int numDays);
    // Should what's below be the same as ^ instead?
    public void viewCategories();
    public void createRoutine();
    public void updateRoutine();
    public void deleteRoutine();
    public void viewToDoList();
    public void createToDoList();
    public void updateToDoList();
    public void deleteToDoList();
    public AccountHandler deleteAccount();
    public String printInformation();

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
