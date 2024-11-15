package Account;

import Category.*;

// Class used to create and manage routines of a given user
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Routine extends Task{
    private String recurrenceInterval;
    private Category category;

    public Routine(String name, String category, String recurrenceInterval) {
        this.name = name;
        this.recurrenceInterval = recurrenceInterval;
        // dont forget to write logic for category**
    }
}
