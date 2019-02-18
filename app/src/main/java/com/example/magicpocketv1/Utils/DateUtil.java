package com.example.magicpocketv1.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    public static String getFormattedDate(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());

    }

    private static Date strtoDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getWeekDay(String date){
        String[] weekdays = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strtoDate(date));
        int weekday = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return weekdays[weekday];

    }

    public static String getDataTitle(String date){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strtoDate(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return months[monthIndex]+" "+String.valueOf(day);
    }

    public static String getXTitle(String date){
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strtoDate(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return months[monthIndex]+" "+String.valueOf(day);
    }
}
