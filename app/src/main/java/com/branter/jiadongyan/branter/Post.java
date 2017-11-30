package com.branter.jiadongyan.branter;

/**
 * Created by jiadongyan on 11/30/17.
 */

public class Post {
    String id;
    String user_id;
    String event_id;
    String content;
    Post(){}
    Post(String id,String user_id, String event_id, String content){
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.content = content;
    }
}
