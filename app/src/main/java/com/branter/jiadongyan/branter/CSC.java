package com.branter.jiadongyan.branter;

/**
 * Created by jiadongyan on 11/29/17.
 */

// client server connect
public class CSC {
    // Create user account (email, password)
    public void createUser(String email, String password) {

    }

    // Sign in account(email, password), return empty string for successful sign in or error message
    public String signIn(String email, String password) {
        return "";
    }

    // Update my account (username, gender, etc)
    public void updateAccount(String username, String gender){

    }

    // Create event (email, event params)
    public void createEvent(String email, String[] args){

    }

    // Get all event
    public Event[] getAllEvents(){
        return null;
    }

    // Get filtered event (by email, by text content, by map address)
    public Event[] getFilteredEvents(){
        return null;
    }
}