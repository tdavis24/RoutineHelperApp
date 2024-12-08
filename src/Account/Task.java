package Account;


import java.time.LocalDate;
import java.time.LocalTime;

import Category.Category;

// Class used to create and manage tasks of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Task{

    protected Category category;
    protected String name;
    protected String information;
    protected String deadline;
    protected LocalTime timeOfDay;
    protected LocalDate startDate;
    protected LocalTime duration;
    protected String recurrenceInterval;
    private boolean deleted = false;

    // Routine
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startDate, String recurrenceInterval, LocalTime duration, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startDate = startDate; 
        this.recurrenceInterval = recurrenceInterval;
        this.duration = duration;
        this.category = category;
    }
    
    // Task no recurrence
    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startDate, LocalTime duration, Category category) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startDate = startDate; 
        this.duration = duration;
        this.recurrenceInterval = null;
        this.category = category;
    }

    public Task(String name, String information, String deadline, Category category, String recurrenceInterval) {
        // Default values for simplicity
        this(name, information, deadline, LocalTime.now(), LocalDate.now(), recurrenceInterval, LocalTime.of(1,0), category);
    }

    public Task updateTask(String newInfo, String newDeadline) {
        if (deleted) {
            System.out.println("Cannot update a deleted task.");
            return this;
        }

        if (newInfo == null || newInfo.trim().isEmpty()) {
            System.out.println("Information cannot be empty. Task not updated.");
            return this;
        }

        if (newDeadline == null || newDeadline.trim().isEmpty()) {
            System.out.println("Deadline cannot be empty. Task not updated.");
            return this;
        }

        this.information = newInfo;
        this.deadline = newDeadline;
        System.out.println("Task updated successfully.");
        return this;
    }
    
    public Task deleteTask() {
        if (deleted) {
            System.out.println("This task is already deleted.");
            return this;
        }

        this.name = null;
        this.information = null;
        this.deadline = null;
        this.timeOfDay = null;
        this.startDate = null;
        this.duration = null;
        this.recurrenceInterval = null;
        this.category = null;

        this.deleted = true;
        System.out.println("Task deleted successfully.");
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
        return this.startDate;
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

    public boolean isDeleted() { 
        return this.deleted; 
    }

    /**
     * Sets the recurrence interval of the task.
     */
    public void setRecurrenceInterval(String recurrenceInterval) {
        if (deleted) {
            System.out.println("Cannot set recurrence for a deleted task.");
            return;
        }
        this.recurrenceInterval = recurrenceInterval;
        System.out.println("Recurrence interval updated to: " + recurrenceInterval);
    }

    /**
     * Sets the category of the task.
     */
    public void setCategory(Category category) {
        if (deleted) {
            System.out.println("Cannot set category for a deleted task.");
            return;
        }
        this.category = category;
        System.out.println("Category updated to: " + category.getCategoryName());
    }

    /**
     * Sets the time of day for the task.
     */
    public void setTimeOfDay(LocalTime timeOfDay) {
        if (deleted) {
            System.out.println("Cannot set time for a deleted task.");
            return;
        }
        this.timeOfDay = timeOfDay;
        System.out.println("Time of day updated to: " + timeOfDay);
    }

    /**
     * Sets the deadline for the task.
     */
    public void setDeadline(String deadline) {
        if (deleted) {
            System.out.println("Cannot set deadline for a deleted task.");
            return;
        }
        this.deadline = deadline;
        System.out.println("Deadline updated to: " + deadline);
    }

    /**
     * Sets the duration of the task.
     */
    public void setDuration(LocalTime duration) {
        if (deleted) {
            System.out.println("Cannot set duration for a deleted task.");
            return;
        }
        this.duration = duration;
        System.out.println("Duration updated to: " + duration);
    }

    /**
     * Sets the start date of the task.
     */
    public void setstartDate(LocalDate startDate) {
        if (deleted) {
            System.out.println("Cannot set start date for a deleted task.");
            return;
        }
        this.startDate = startDate;
        System.out.println("Start date updated to: " + startDate);
    }

    /**
     * Sets the information of the task.
     */
    public void setInformation(String information) {
        if (deleted) {
            System.out.println("Cannot set information for a deleted task.");
            return;
        }
        if (information == null || information.trim().isEmpty()) {
            System.out.println("Information cannot be empty. Task information not updated.");
            return;
        }
        this.information = information;
        System.out.println("Information updated successfully.");
    }

    /**
     * Sets the name of the task.
     */
    public void setName(String name) {
        if (deleted) {
            System.out.println("Cannot set name for a deleted task.");
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty. Task name not updated.");
            return;
        }
        this.name = name;
        System.out.println("Name updated successfully to: " + name);
    }

        @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", deadline=" + deadline +
                ", timeOfDay=" + timeOfDay +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", recurrenceInterval='" + recurrenceInterval + '\'' +
                ", category=" + (category != null ? category.getCategoryName() : "None") +
                ", deleted=" + deleted +
                '}';
    }

}
