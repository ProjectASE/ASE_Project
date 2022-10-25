package com.example.aseproject.spinner;

import java.util.ArrayList;

public class Month {

    private String month;
    ArrayList<Object> myMonthList;

    public Month()
    {
        myMonthList = new ArrayList<>();

        myMonthList.add(new Month("Select Month"));
        myMonthList.add(new Month("01"));
        myMonthList.add(new Month("02"));
        myMonthList.add(new Month("03"));
        myMonthList.add(new Month("04"));
        myMonthList.add(new Month("05"));
        myMonthList.add(new Month("06"));
        myMonthList.add(new Month("07"));
        myMonthList.add(new Month("08"));
        myMonthList.add(new Month("09"));
        myMonthList.add(new Month("10"));
        myMonthList.add(new Month("11"));
        myMonthList.add(new Month("12"));


    }

    public Month(String month) {
        this.month = month;
    }

    public ArrayList<Object> getMyMonthList() {
        return myMonthList;
    }

    public void setMyMonthList(ArrayList<Object> myMonthList) {
        this.myMonthList = myMonthList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
