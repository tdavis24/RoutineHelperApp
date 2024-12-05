package Account;

import java.util.Locale.Category;

// Class used to create and manage tasks of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Task{

    protected Category category;
    protected String name;
    protected String information;
    protected String deadline;

    public Task() {}

    public Task(String name, String information, String deadline, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
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
