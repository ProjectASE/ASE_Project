package com.example.aseproject.events.model;

public class Event {
    private String id;
    private String title;
    private String location;
    private String hour;
    private String minute;
    private String date;
    private String month;
    private String year;
    private String description;
    public Event(){}
    public Event(String id, String title, String location, String hour, String minute, String date, String month, String year, String description){
        this.id = id;
        this.title = title;
        this.location = location;
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.month = month;
        this.year = year;
        this.description = description;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
