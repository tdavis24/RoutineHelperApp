package Account;

import java.util.LinkedList;
import java.util.List;

// Class used to create and manage a to-do list for a user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class ToDoList{

    private String timeFrame;
    private LinkedList<Task> tasks;

    public ToDoList() {
        this.tasks = new LinkedList<>();
    }

    public ToDoList(List<Task> tasks, String timeFrame) {
        this.tasks = new LinkedList<>(tasks);
        this.timeFrame = timeFrame;
    }


    /**
     * Creates or resets the to-do list
     * Sets a default timeframe if none is provided and clears existing tasks.
     */
    public void createToDoList(List <Task> tasks) {
        this.timeFrame = (timeFrame != null && !timeFrame.isEmpty()) ? timeFrame : "Default";
        this.tasks.clear();
        this.tasks.addAll(tasks);
        System.out.println("A new To-Do list has been created.");
    }

    /**
     * Update the existing to-do list.
     */
    public void updateToDoList() {
        if (tasks.isEmpty() && (timeFrame == null || timeFrame.isEmpty())) {
            System.out.println("No To-Do list to update. Please create one first.");
        } else {
            System.out.println("To-Do list updated. Current timeframe: " + (this.timeFrame != null ? this.timeFrame : "N/A"));
        }

    }

    public void deleteToDoList() {
        this.tasks.clear();
        this.timeFrame = null;
        System.out.println("To-Do list deleted successfully.");
    }

    /**
     * Add a Task to the To-Do List
     */
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully: " + task.name);
    }

    /**
     * Delete a Task from To-Do List by name
     */
    public void deleteTask(String taskName) {
        boolean taskFound = false;
        for (Task task : tasks) {
            if (task.name.equalsIgnoreCase(taskName)) {
                tasks.remove(task);
                System.out.println("Task deleted successfully: " + task.name);
                taskFound = true;
                break;
            }
        }
        if (!taskFound) {
            System.out.println("Task not found: " + taskName);
        }
    }

    public void displayToDoList() {
        if (tasks.isEmpty()) {
            System.out.println("Your To-Do list is empty.");
        } else {
            System.out.println("Your To-Do list (Timeframe: " + (this.timeFrame != null ? this.timeFrame : "N/A") + "):");
            for (Task task : tasks) {
                System.out.println("- " + task.getName() + " [Priority: " + task.getPriority() + "]" + (task.isCompleted() ? " [Completed]" : ""));
            }
        }
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
        System.out.println("To-Do list timeframe set to: " + timeFrame);
    }

    public List<Task> getTasks() {
        return new LinkedList<>(tasks);
    }
    
}
