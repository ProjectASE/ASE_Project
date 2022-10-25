package com.example.aseproject.events.model;

public class Event {
    private String id;
    private String title;
    private String location;
    private String time;
    private String date;
    private String description;
    public Event(){}
    public Event(String id,String title,String location,String time,String date,String description){
        this.id = id;
        this.title = title;
        this.location = location;
        this.time = time;
        this.date = date;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
