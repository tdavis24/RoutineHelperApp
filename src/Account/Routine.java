package Account;

<<<<<<< Updated upstream
import Category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
=======
import Category.*;
import java.util.ArrayList;
import java.util.List;
>>>>>>> Stashed changes

// Class used to create and manage routines of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Routine extends Task{
    private String recurrenceInterval;
    private Category category;
    private Date reminderTime;
    private Timer reminderTimer;

<<<<<<< Updated upstream
    private List<Task> tasks;

    public Routine(String name, String information, String deadline, Category category, String recurrenceInterval) {
        super(name, information, deadline, category);
        this.recurrenceInterval = recurrenceInterval;
        this.tasks = new ArrayList<>();
=======
    public Routine(String name, String information, String deadline, Category category, String recurrenceInterval) {
        super(name, information, deadline);
        this.category = category;
        this.recurrenceInterval = recurrenceInterval;
    }
    
    // Getters and setters for recurrenceInterval
    public String getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(String recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
>>>>>>> Stashed changes
    }

    // Methods to add, delete, and adjust tasks
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(String taskName) {
        tasks.removeIf(task -> task.getName().equalsIgnoreCase(taskName));
    }

    public void adjustTask(String taskName, Task newTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equalsIgnoreCase(taskName)) {
                tasks.set(i, newTask);
                break;
            }
        }
    }

    // Getters and setters for tasks
    public List<Task> getTasks() {
        return tasks;
    }

    // Getters and setters for recurrenceInterval
    public String getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(String recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Use Case 3.1.2: Set Reminder for Routine
    public void setReminder(Date reminderTime) {
        this.reminderTime = reminderTime;
        reminderTimer = new Timer();
    
        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Reminder: It's time for your routine - " + name);
            }
        };
    
        long period = 0;
        if (recurrenceInterval.equalsIgnoreCase("daily")) {
            period = 24 * 60 * 60 * 1000L; // 24 hours
        } else if (recurrenceInterval.equalsIgnoreCase("weekly")) {
            period = 7 * 24 * 60 * 60 * 1000L; // 7 days
        } else if (recurrenceInterval.equalsIgnoreCase("monthly")) {
            period = 30 * 24 * 60 * 60 * 1000L; // Approximate 30 days
        }
    
        if (period > 0) {
            reminderTimer.scheduleAtFixedRate(reminderTask, reminderTime, period);
        } else {
            reminderTimer.schedule(reminderTask, reminderTime);
        }
    }

    public void cancelReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
            System.out.println("Reminder for routine - " + name + " has been canceled.");
        }
    }

    public Routine updateRoutine(String newInfo, String newDeadline, String newRecurrence) {
        this.information = newInfo;
        this.deadline = newDeadline;
        this.recurrenceInterval = newRecurrence;
        System.out.println("Routine updated.");
        return this;
    }
    
    public Routine deleteRoutine() {
        
    }
    
}
