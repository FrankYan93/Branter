package com.branter.jiadongyan.branter;

import android.app.Activity;
import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiadongyan on 11/29/17.
 */

// client server connect
public class CSC {
    private URL url;
    private HttpURLConnection con;

    CSC(){
    }
    // Create user account (email, password)
    public String createUser(String email, String password) {
        try{
//            url = new URL("http://10.0.2.2:3000/users");
             url = new URL("https://branterapi.herokuapp.com/users");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("email", email);
            parameters.put("password", password);
            parameters.put("password_confirmation", password);

            con.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            int status = con.getResponseCode();
            System.out.println(status);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            return content.substring(1,content.length()-1).split(",")[0].split(":")[1];
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("created a user");
        return "";
    }

    // GetUserInformation
    public User getUserInformation(String id){
        User user = new User();
        try{
            url = new URL("https://branterapi.herokuapp.com/users/"+id);
            // url = new URL("https://branterapi.herokuapp.com/users");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String[] strs = content.substring(1,content.length()-1).split(",");
            user.id = strs[0];
            user.email = strs[1];
            user.username = strs[3];
            if (strs[4].equals("true")){
                user.gender = true;
            }else if (strs[4].equals("false")){
                user.gender = false;
            }
            user.birthday = strs[5];
            System.out.println();
            con.disconnect();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("got a user");
        return user;
    }

    // Sign in account(email, password), return empty string for successful sign in or error message
    public boolean signIn(String email, String password) {
        try{
            url = new URL("http://10.0.2.2:3000/login?email="+email+"&password="+password);
//            url = new URL("https://branterapi.herokuapp.com/users/"+id);
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            if (content.substring(1,content.length()-1).split(",")[0].equals("\"success_flag\":true")){
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("login a user");
        return false;
    }

    // Update my account (username, gender, etc)
    public void updateAccount(String username, String gender, String birthday){
        String id = SaveSharedPreference.PREF_USER_ID;
        try{
            url = new URL("http://10.0.2.2:3000/users/4");
//            url = new URL("https://branterapi.herokuapp.com/users/"+id);
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("name", username);
            parameters.put("gender", gender);
            parameters.put("birthday", birthday);

            con.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            int status = con.getResponseCode();
            System.out.println(status);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("update a user");
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

class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}