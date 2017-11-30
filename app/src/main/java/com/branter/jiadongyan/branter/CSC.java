package com.branter.jiadongyan.branter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by jiadongyan on 11/29/17.
 */

// client server connect
public class CSC {

    // Create user account (email, password)
    public void createUser(String email, String password) {

    }

    // GetUserInformation
    public User getUserInformation(String id){
        return null;
    }

    // Sign in account(email, password), return empty string for successful sign in or error message
    public String signIn(String email, String password) {
        return "";
    }

    // Update my account (username, gender, etc)
    public void updateAccount(String username, String gender, String birthday){

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

/*
HttpClient httpclient = new DefaultHttpClient();
HttpGet httpget= new HttpGet(URL);

HttpResponse response = httpclient.execute(httpget);

if(response.getStatusLine().getStatusCode()==200){
   String server_response = EntityUtils.toString(response.getEntity());
   Log.i("Server response", server_response );
} else {
   Log.i("Server response", "Failed to get server response" );
}
 */
}