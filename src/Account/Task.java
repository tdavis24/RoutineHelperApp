package Account;

import java.time.*;
// Class used to create and manage tasks of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Task{

    protected String name;
    protected String information;
    protected String deadline;
    protected LocalTime timeOfDay;
    protected LocalDate startdate;
    protected String recurranceInterval;


    public Task(String name, String information, String deadline, LocalTime timeOfDay, LocalDate startdate, String recurranceInterval) {
        this.name = name;
        this.information = information;
        this.deadline = deadline;
        this.timeOfDay = timeOfDay;
        this.startdate = startdate; 
        this.recurranceInterval = recurranceInterval;
    }
}
