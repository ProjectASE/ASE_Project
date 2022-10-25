package com.example.aseproject.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class
DateAndTimeConversions {

    public static String compareDateAndTime(String dateOne, String timeOne, String dateTwo, String timeTwo)
    {
        if (checkDateGreater(dateOne, dateTwo).equals("before"))
        {
            return "before";
        }

        else if (checkDateGreater(dateOne, dateTwo).equals("after"))
        {
            return "after";
        }

        else if (checkDateGreater(dateOne, dateTwo).equals("equals"))
        {
            if (checkTimings(timeOne, timeTwo).equals("before"))
            {
                return "before";
            }
            else if (checkTimings(timeOne, timeTwo).equals("after"))
            {
                return "after";
            }

            else if (checkTimings(timeOne, timeTwo).equals("equals"))
            {
                return "equals";
            }
        }
        return null;
    }

    public static String checkDateGreater(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date strDate1 = null;
        Date strDate2 = null;
        try {
            strDate1 = sdf.parse(date1);
            strDate2 = sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (strDate1.after(strDate2)) {
            return "after";
        }

        else if (strDate1.before(strDate2)) {
            return "before";
        }
        else if (strDate1.equals(strDate2)) {
            return "equals";
        }

        else {
            return "";
        }
    }

    public static String checkTimings(String startTime, String endTime)
    {


        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date timeOne = sdf.parse(startTime);
            Date timeTwo = sdf.parse(endTime);

            if (timeOne.after(timeTwo))
            {
                return "after";
            }
            else if(timeOne.before(timeTwo)) {
                return "before";
            }
            else if (timeOne.equals(timeTwo)){

                return "equals";
            }
            else {
                return "";
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return "";

    }

    public static String getCurrentDate()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentTime()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedTime = df.format(c);

        return formattedTime;
    }
}
