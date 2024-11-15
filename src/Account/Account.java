package Account;

// Class used to create and manage user accounts
// Created by: Ethan Andrews, Tanner Davis, and Michael Rosenwinkel
// Created on: October 8, 2024
public class Account implements AccountHandler{
    private String username;
    private String email;
    private String password;
    private String name;

    public Account(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void createAccount() {

    }

    public void updateAccount(String newEmail, String newPassword, String newName) {
        this.email = newEmail;
        this.password = newPassword;
        this.name = newName;
        System.out.println("Account information updated.");
    }

    public void deleteAccount() {

    }
    
}
