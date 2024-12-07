package Category;

// Class used to create and manage routine categories
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Category implements CategoryHandler{

    private String categoryName;
    private String type;


    public Category(String categoryName, String type) {
        this.categoryName = categoryName;
        this.type = type;
    }

    public boolean createCategory(String name, String type) {
        this.categoryName = name;
        this.type = type;
        System.out.println("Category created: " + name);
        return true;
    }

    public boolean deleteCategory(String name) {
        return true;
    }

    public boolean updateCategory(String name, String type) {
        this.categoryName = name;
        this.type = type;
        System.out.println("Category updated: " + name);
        return true;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public String getCategoryType(){
        return this.type;
    }
    
}
