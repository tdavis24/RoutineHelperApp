package Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import Category.*;


// Class used to create and manage routines of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Routine extends Task{
    private String recurrenceInterval;
    private Category category;
    private Date reminderTime;
    private Timer reminderTimer;
    private List<Task> tasks;

    public Routine(String name, String information, String deadline, Category category, String recurrenceInterval) {
        super(name, information, deadline, category, recurranceInterval);
        this.recurrenceInterval = recurrenceInterval;
        this.tasks = new ArrayList<>();
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