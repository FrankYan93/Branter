package com.branter.jiadongyan.branter;

/**
 * Created by jiadongyan on 11/29/17.
 */

public class User {
    String id;
    String username;
    boolean gender;
    String date;

    User(String id, String username, boolean gender, String date){
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.date = date;
    }
}
