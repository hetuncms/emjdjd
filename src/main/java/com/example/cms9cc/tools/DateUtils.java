package com.example.cms9cc.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String getTomorrow(){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DATE,1);
        return  new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }

    public static String getYesterday() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DATE,-1);
        return  new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
    }

    public static Date now() {
        return new Date(System.currentTimeMillis());
    }
}
