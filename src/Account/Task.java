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
    protected LocalTime duration;
    protected String recurrenceInterval;

    // Routine
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startdate, String recurrenceInterval, LocalTime duration, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startdate = startdate; 
        this.recurrenceInterval = recurrenceInterval;
        this.duration = duration;
        this.category = category;
    }
    
    // Task
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startdate, LocalTime duration, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startdate = startdate; 
        this.duration = duration;
        this.recurrenceInterval = null;
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

    public String getName(){
        return this.name;
    }

    public String getInformation(){
        return this.information;
    }

    public String getDeadline(){
        return this.deadline;
    }

    public LocalTime getTimeOfDay(){
        return this.timeOfDay;
    }

    public LocalDate getDate(){
        return this.startdate;
    }

    public String getRecurrenceInterval(){
        if(this.recurrenceInterval != null)
        {
            return this.recurrenceInterval;
        }
        else
        {
            return "Not recurring";
        }
    }

    public LocalTime getDuration(){
        return this.duration;
    }

    public Category getCategory(){
        return this.category;
    }
}
