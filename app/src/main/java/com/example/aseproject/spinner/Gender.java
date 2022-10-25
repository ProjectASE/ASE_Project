package com.example.aseproject.spinner;

import java.util.ArrayList;

public class Gender {

    private String gender;
    ArrayList<Object> genderList;



    public Gender()
    {
        genderList = new ArrayList<>();
        genderList.add(new Gender("Select Gender"));
        genderList.add(new Gender("male"));
        genderList.add(new Gender("female"));
        genderList.add(new Gender("other"));

    }

    public Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<Object> getGenderList() {
        return genderList;
    }

    public void setGenderList(ArrayList<Object> genderList) {
        this.genderList = genderList;
    }
}
