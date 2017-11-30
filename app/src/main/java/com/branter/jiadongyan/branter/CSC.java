package com.branter.jiadongyan.branter;


import org.json.JSONArray;
import org.json.JSONObject;

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
            e.printStackTrace();
        }finally {
            con.disconnect();
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
            user.id = strs[0].split(":")[1];
            user.email = strs[1].split(":")[1];
            user.username = strs[3].split(":")[1];
            user.num_post = Integer.parseInt(strs[10].split(":")[1]);
            user.num_events_host = Integer.parseInt(strs[11].split(":")[1]);
            user.num_event_joined = Integer.parseInt(strs[12].split(":")[1]);
            if (strs[4].equals("true")){
                user.gender = true;
            }else if (strs[4].equals("false")){
                user.gender = false;
            }
            user.birthday = strs[5];
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        System.out.println("got a user");
        return user;
    }

    // Sign in account(email, password), return empty string for successful sign in or error message
    public String signIn(String email, String password) {
        try{
//            url = new URL("http://10.0.2.2:3000/login?email="+email+"&password="+password);
            url = new URL("https://branterapi.herokuapp.com/login?email="+email+"&password="+password);
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
            String[] strs = content.substring(1,content.length()-1).split(",");
            if (strs[0].equals("\"success_flag\":true")){
                return strs[1].split(",")[0].split(":")[2];
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        System.out.println("login a user");
        return "";
    }

    // Update my account (username, gender, etc)
    public void updateAccount(String username, String gender, String birthday){
        String id = SaveSharedPreference.PREF_USER_ID;
        try{
//            url = new URL("http://10.0.2.2:3000/users/4");
            url = new URL("https://branterapi.herokuapp.com/users/"+id);
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
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        System.out.println("update a user");
    }

    // Create event (event params, event params' content), params can be title,from(datetime),to(datetime), lat(double), lng(double)
    public void createEvent(String[] eventParam, String[] args){
        String id = SaveSharedPreference.PREF_USER_ID;
        try{
//            url = new URL("http://10.0.2.2:3000/users");
            url = new URL("https://branterapi.herokuapp.com/users/"+id+"/events");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();
            for (int i = 0; i<args.length; i++){
                parameters.put(eventParam[i], args[i]);
            }

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
        }finally {
            con.disconnect();
        }
        System.out.println("created a user");

    }

    // Get all event
    public Event[] getAllEvents(){
        try{
            url = new URL("https://branterapi.herokuapp.com/events");
            // url = new URL("https://branterapi.herokuapp.com/users");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONArray jo = new JSONArray(content.toString());
            int size=jo.length();
            Event[] eve = new Event[size];
            for (int i=0;i<size;i++){
                JSONObject x = new JSONObject((String) jo.get(i));
                System.out.println(x);
                double lat, lng;
                try{
                    lat = x.getDouble("lat");
                    lng = x.getDouble("lng");
                }catch (Exception e){
                    lat = 0;
                    lng = 0;
                }
                eve[i] = new Event(
                        x.getString("id"),
                        x.getString("title"),
                        x.getString("from"),
                        x.getString("to"),
                        x.getString("contents"),
                        null,
                        lat,
                        lng
                );
            }
            in.close();
            return eve;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
    }

    public Event[] getEventsByUserId(String id){
        try{
            url = new URL("https://branterapi.herokuapp.com/users/"+id+"/events");
            // url = new URL("https://branterapi.herokuapp.com/users");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONArray jo = new JSONArray(content.toString());
            int size=jo.length();
            Event[] eve = new Event[size];
            for (int i=0;i<size;i++){
                JSONObject x = new JSONObject((String) jo.get(i));
                System.out.println(x);
                double lat, lng;
                try{
                    lat = x.getDouble("lat");
                    lng = x.getDouble("lng");
                }catch (Exception e){
                    lat = 0;
                    lng = 0;
                }
                eve[i] = new Event(
                        x.getString("id"),
                        x.getString("title"),
                        x.getString("from"),
                        x.getString("to"),
                        x.getString("contents"),
                        null,
                        lat,
                        lng
                );
            }
            in.close();
            return eve;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
    }

    public void followEvent(String event_id) {
        String id = SaveSharedPreference.PREF_USER_ID;
        try{
            url = new URL("https://branterapi.herokuapp.com/event_followers");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();

            parameters.put("user_id", id);
            parameters.put("event_id", event_id);

            con.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            int status = con.getResponseCode();
            System.out.println(status);
            out.flush();
            out.close();

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
    }

    public User[] eventFollowers(String id){
        String url = "https://branterapi.herokuapp.com/events/"+id+"/followers";
//        String url = "http://10.0.2.2:3000/events/"+id+"/followers";
        String method = "GET";
        String content = request(url,method, new String[] {}, new String[] {});
        User[] users = null;
        try{
            JSONArray jo = new JSONArray(content);
            int size = jo.length();
            users = new User[size];
            for (int i=0;i<size;i++){
                users[i] = new User();
                JSONObject o = (JSONObject) jo.get(i);
                users[i].id = o.getString("id");
                users[i].email = o.getString("email");
                users[i].birthday = o.getString("birthday");
                users[i].gender = o.getString("gender").equals("true");
                users[i].username = o.getString("name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public Event[] joinedEvents(){
        Event[] eve = null;
        String id = SaveSharedPreference.PREF_USER_ID;
        String url = "https://branterapi.herokuapp.com/users/"+id+"/joined_event";
        String method = "GET";
        String content = request(url,method, new String[] {}, new String[] {});
        try{
            JSONArray jo = new JSONArray(content);
            int size=jo.length();
            eve = new Event[size];
            for (int i=0;i<size;i++) {
                JSONObject x = (JSONObject) jo.get(i);
                System.out.println(x);
                double lat, lng;
                try {
                    lat = x.getDouble("lat");
                    lng = x.getDouble("lng");
                } catch (Exception e) {
                    lat = 0;
                    lng = 0;
                }
                eve[i] = new Event(
                        x.getString("id"),
                        x.getString("title"),
                        x.getString("from"),
                        x.getString("to"),
                        x.getString("contents"),
                        null,
                        lat,
                        lng
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return eve;
    }

    // Create new post
    public void createPost(String event_id, String content) {
        String id = SaveSharedPreference.PREF_USER_ID;
        try{
            url = new URL("https://branterapi.herokuapp.com/posts");
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<>();

            parameters.put("user_id", id);
            parameters.put("event_id", event_id);
            parameters.put("content", content);

            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            HttpURLConnection.setFollowRedirects(false);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            int status = con.getResponseCode();
            System.out.println(status);
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
    }

    public Post[] getEventPosts(String event_id){
        Post[] posts = null;
        String url = "https://branterapi.herokuapp.com/events/"+event_id+"/posts";
        String method = "GET";
        String content = request(url,method, new String[] {}, new String[] {});
        try{
            JSONArray jo = new JSONArray(content);
            int size = jo.length();
            posts = new Post[size];
            for (int i=0;i<size;i++){
                JSONObject o = (JSONObject) jo.get(i);
                posts[i] = new Post(
                        o.getString("id"),
                        o.getString("user_id"),
                        o.getString("event_id"),
                        o.getString("content")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return posts;
    }

    public Post[] getUserPost(String id){
        Post[] posts = null;
        String url = "https://branterapi.herokuapp.com/users/"+id+"/posts";
        String method = "GET";
        String content = request(url,method, new String[] {}, new String[] {});
        if (content.charAt(0)=='{'){
            try{
                JSONObject o = new JSONObject(content);
                posts = new Post[1];
                posts[0] = new Post(
                        o.getString("id"),
                        o.getString("user_id"),
                        o.getString("event_id"),
                        o.getString("content")
                );
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                JSONArray jo = new JSONArray(content);
                int size = jo.length();
                posts = new Post[size];
                for (int i = 0; i < size; i++) {
                    JSONObject o = (JSONObject) jo.get(i);
                    posts[i] = new Post(
                            o.getString("id"),
                            o.getString("user_id"),
                            o.getString("event_id"),
                            o.getString("content")
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public String request(String s, String method, String[] params, String[] args){
        try{
//            url = new URL("http://10.0.2.2:3000/users");
            url = new URL(s);
        }catch (MalformedURLException e){
            System.err.println("wrong url");
        }
        try{
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            Map<String, String> parameters = new HashMap<>();
            for (int i = 0; i<args.length; i++){
                parameters.put(params[i], args[i]);
            }
            if (parameters.size()>0) {
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                int status = con.getResponseCode();
                System.out.println(status);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            return content.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
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
