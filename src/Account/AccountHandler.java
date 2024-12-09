package Account;

import Account.Schedule.*;
import java.util.List;
import Category.*;

// Interface used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 29, 2024
public interface AccountHandler {
    String getUsername();
    public AccountHandler getAccount(String username);
    public boolean updateAccount(String infoToUpdate, String information);
    public Schedule generateSchedule(int numDays);
    public String analyzeSchedule(int numDays);
    public String compareSchedule(Schedule otherSchedule, int numDays);
    public String generateScheduleStatistics(int numDays);
    public List<Category> viewCategories();
    public void viewRoutines();
    public boolean createRoutine(Task task);
    public void updateRoutine(Task task);
    public void deleteRoutine(String name);
    public void viewToDoList();
    public void createToDoList();
    public void updateToDoList();
    public void deleteToDoList(String name);
    public AccountHandler deleteAccount();
    public String accountInformation();
    public boolean availableUsername(String possibleUsername);
    public boolean verifyPassword(String curPass);
    public String viewAccount();
    public boolean createCategory(String name, String type);
    public boolean deleteCategory(String name);
    public Category getCategory(String categoryName);

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
