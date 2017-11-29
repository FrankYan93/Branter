package com.branter.jiadongyan.branter;

import java.io.Serializable;

/**
 * Created by Jerry on 11/28/17.
 */

/* Serializable */
public class GridTest implements Serializable {
    private String eventTitle;
    private String headphoto;
    private String content;
    private String time;
    private String image;



    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String username) {
        this.eventTitle = eventTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}