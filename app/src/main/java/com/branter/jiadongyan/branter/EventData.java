package com.branter.jiadongyan.branter;

import android.app.usage.UsageEvents;

import java.util.Date;

/**
 * Created by spenc on 11/29/2017.
 */

public class EventData {

    public String name;
    public double Lat,Lng;
    public Integer  people_number;
    public Date startdate;

    public EventData(String s, double i1, double i2, Integer i3){
        name = s;
        Lat = i1;
        Lng = i2;
        people_number = i3;
        startdate = new Date();

    }
}
