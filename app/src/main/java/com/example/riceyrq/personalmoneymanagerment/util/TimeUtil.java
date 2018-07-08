package com.example.riceyrq.personalmoneymanagerment.util;

import android.util.Log;

import com.example.riceyrq.personalmoneymanagerment.define.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeUtil {

    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return ts;
    }

    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public static long firstDayOfMonth(long timeMillis) throws ParseException {
        String time = stampToDate(timeMillis);
        String firstDay = time.substring(0, 7) + "01 00:00:00";
        return dateToStamp(firstDay);
    }

    public static long lastDayOfMonth(long timeMillis) throws ParseException {
        String time = stampToDate(timeMillis);
        int day;
        int month = Integer.valueOf(time.substring(5, 6));
        int year = Integer.valueOf(time.substring(0, 3));
        if (isSpecial(year)){
            day = Data.daysSpecial[month - 1];
        } else {
            day = Data.daysNormal[month - 1];
        }
        String lastDay = time.substring(0, 7) + String.valueOf(day) + " 23:59:59";
        return dateToStamp(lastDay);
    }

    public static long firstDayOfMonth(int year, int month) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(year));
        stringBuffer.append("-");
        if (month > 9 && month < 13){
            stringBuffer.append(String.valueOf(month));
        } else if (month > 0 && month < 10){
            stringBuffer.append("0");
            stringBuffer.append(month);
        }
        stringBuffer.append("-");
        stringBuffer.append("01");
        stringBuffer.append(" 00:00:00");

        return dateToStamp(stringBuffer.toString());
    }

    public static long lastDayOfMonth(int year, int month) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(year));
        stringBuffer.append("-");
        if (month > 9 && month < 13){
            stringBuffer.append(String.valueOf(month));
        } else if (month > 0 && month < 10){
            stringBuffer.append("0");
            stringBuffer.append(month);
        }
        stringBuffer.append("-");
        int day;
        if (isSpecial(year)){
            day = Data.daysSpecial[month - 1];
        } else {
            day = Data.daysNormal[month - 1];
        }
        stringBuffer.append(String.valueOf(day));
        stringBuffer.append(" 23:59:59");

        return dateToStamp(stringBuffer.toString());
    }

    public static long getDayMillStart(int year, int month, int day) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(year));
        stringBuffer.append("-");
        if (month > 9 && month < 13){
            stringBuffer.append(String.valueOf(month));
        } else if (month > 0 && month < 10){
            stringBuffer.append("0");
            stringBuffer.append(month);
        }
        stringBuffer.append("-");
        if (day > 0 && day < 10){
            stringBuffer.append("0");
            stringBuffer.append(String.valueOf(day));
        } else {
            stringBuffer.append(String.valueOf(day));
        }
        stringBuffer.append(" 00:00:00");
        Log.e("tess", stringBuffer.toString());
        return dateToStamp(stringBuffer.toString());
    }

    public static long getDayMillStop(int year, int month, int day) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(year));
        stringBuffer.append("-");
        if (month > 9 && month < 13){
            stringBuffer.append(String.valueOf(month));
        } else if (month > 0 && month < 10){
            stringBuffer.append("0");
            stringBuffer.append(month);
        }
        stringBuffer.append("-");
        if (day > 0 && day < 10){
            stringBuffer.append("0");
            stringBuffer.append(String.valueOf(day));
        } else {
            stringBuffer.append(String.valueOf(day));
        }
        stringBuffer.append(" 23:59:59");
        Log.e("tess", stringBuffer.toString());
        return dateToStamp(stringBuffer.toString());
    }

    public static long getDayMill(int year, int month, int day, int hour, int min) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(year));
        stringBuffer.append("-");
        if (month > 9 && month < 13){
            stringBuffer.append(String.valueOf(month));
        } else if (month > 0 && month < 10){
            stringBuffer.append("0");
            stringBuffer.append(month);
        }
        stringBuffer.append("-");
        if (day > 0 && day < 10){
            stringBuffer.append("0");
            stringBuffer.append(String.valueOf(day));
        } else {
            stringBuffer.append(String.valueOf(day));
        }
        stringBuffer.append(" ");
        if (hour >= 0 && hour < 10){
            stringBuffer.append("0");
            stringBuffer.append(String.valueOf(hour));
        } else {
            stringBuffer.append(String.valueOf(hour));
        }
        stringBuffer.append(":");
        if (min >= 0 && min < 10) {
            stringBuffer.append("0");
            stringBuffer.append(String.valueOf(min));
        } else {
            stringBuffer.append(String.valueOf(min));
        }
        stringBuffer.append(":00");
        Log.e("tess", stringBuffer.toString());
        return dateToStamp(stringBuffer.toString());
    }


    public static boolean isSpecial(int year){
        if (year % 100 == 0){
            if (year % 400 == 0)
                return true;
            else
                return false;
        } else {
            if (year % 4 == 0)
                return true;
            else
                return false;
        }
    }

    public static List<Integer> getHours(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getMins(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 60; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getYears(){
        List<Integer> list = new ArrayList<>();
        for (int i = 2000; i <= 2030; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getMonths(){
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getDaysNormal(int month){
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= Data.daysNormal[month - 1]; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getDaysSpecial(int month){
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= Data.daysSpecial[month - 1]; i++){
            list.add(i);
        }
        return list;
    }

    public static boolean isOkYMD(int year1, int month1, int day1, int year2, int month2, int day2){
        if (year1 < year2) {
            return true;
        } else if (year1 == year2) {
            if (month1 < month2) {
                return true;
            } else if (month1 == month2) {
                if (day1 <= day2) {
                    return true;
                }
            }
        }
        return false;
    }
}