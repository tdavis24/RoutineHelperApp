package Category;

// Interface used to create and manage categories
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 29, 2024
public interface CategoryHandler {
    boolean createCategory(String name, String type);
    boolean deleteCategory(String name);
    boolean updateCategory(String name, String type);
}
