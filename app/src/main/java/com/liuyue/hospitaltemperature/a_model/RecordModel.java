package com.liuyue.hospitaltemperature.a_model;

/**
 * Created by kys_31 on 2017/12/18.
 */

public class RecordModel {

private int state;
private String value;
private String date;
private String time;
private String location;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
