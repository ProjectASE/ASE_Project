package com.example.aseproject.spinner;

import java.util.ArrayList;

public class DateClass {

    private String date;
    ArrayList<Object> myDateList;

    public DateClass()
    {
        myDateList = new ArrayList<>();
        myDateList.add(new DateClass("Select Date"));

        for (int i=1; i<=31; i++)
        {
            myDateList.add(new DateClass(i+""));
        }

    }

    public DateClass(String date) {
        this.date = date;
    }

    public ArrayList<Object> getMyDateList() {
        return myDateList;
    }

    public void setMyDateList(ArrayList<Object> myDateList) {
        this.myDateList = myDateList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
