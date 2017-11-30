package com.branter.jiadongyan.branter;

/**
 * Created by jiadongyan on 11/29/17.
 */

public class User {
    String id;
    String email;
    String username;
    boolean gender;
    String birthday;

    User(String id, String email, String username, boolean gender, String birthday){
        this.id = id;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
    }

    User(){

    }
}
