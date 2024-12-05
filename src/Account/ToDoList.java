package Account;

import java.util.ArrayList;
import java.util.List;

// Class used to create and manage a to-do list for a user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class ToDoList{

    private String timeFrame;
    private ArrayList<Task> tasks;

    public ToDoList() {
        this.tasks = new ArrayList<>();
    }

    public ToDoList(List<Task> tasks, String timeFrame) {
        this.tasks = new ArrayList<>(tasks);
        this.timeFrame = timeFrame;
    }

    public void createToDoList() {

    }

    public void updateToDoList() {

    }

    public void deleteToDoList() {

    }

    // Add a Task to To-Do List
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully: " + task.name);
    }

    // Delete a Task from To-Do List
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
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("Your To-Do List:");
            for (Task task : tasks) {
                System.out.println("- " + task.name);
            }
        }
    }
    
}
