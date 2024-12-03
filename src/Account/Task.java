package Account;

// Class used to create and manage tasks of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Task{

    protected String name;
    protected String information;
    protected String deadline;

    public Task() {}

    public Task(String name, String information, String deadline) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
    }
}
