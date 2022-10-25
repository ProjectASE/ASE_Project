package com.example.aseproject.spinner;

import java.util.ArrayList;

public class Year {

    private String year;
    ArrayList<Object> myYearList;

    public Year()
    {
        myYearList = new ArrayList<>();
        myYearList.add(new Year("Select Date"));

        for (int i=1940; i<=2200; i++)
        {
            myYearList.add(new Year(i+""));
        }


    }

    public Year(String year) {
        this.year = year;
    }

    public ArrayList<Object> getMyYearList() {
        return myYearList;
    }

    public void setMyYearList(ArrayList<Object> myYearList) {
        this.myYearList = myYearList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
