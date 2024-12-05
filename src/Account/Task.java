package Account;


import java.time.*;
import java.util.Locale.Category;

// Class used to create and manage tasks of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Task{

    protected Category category;
    protected String name;
    protected String information;
    protected String deadline;
    protected LocalTime timeOfDay;
    protected LocalDate startdate;
    protected String recurranceInterval;

    // Routine
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startdate, String recurranceInterval, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startdate = startdate; 
        this.recurranceInterval = recurranceInterval;
        this.category = category;
    }
    
    // Task
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startdate, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startdate = startdate; 
        this.recurranceInterval = null;
        this.category = category;
    }

    public Task updateTask(String newInfo, String newDeadline) {
        this.information = newInfo;
        this.deadline = newDeadline;
        System.out.println("Task updated.");
        return this;
    }
    
    public Task deleteTask() {
        
        // Implement logic

        System.out.println("Task deleted.");
        return this;
    }

}
