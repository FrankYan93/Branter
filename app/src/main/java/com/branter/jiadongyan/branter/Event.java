package com.branter.jiadongyan.branter;

/**
 * Created by jiadongyan on 11/29/17.
 */

public class Event {
    String id;
    String title;
    String from;
    String to;
    String contents;
    String imageUrl;
    double lat;
    double lng;
    Event(String id, String title, String from, String to, String contents,
            String imageUrl, double lat, double lng){
        this.id = id;
        this.title = title;
        this.from = from;
        this.to = to;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
    }
}
