package com.example.riceyrq.personalmoneymanagerment.util;

import com.example.riceyrq.personalmoneymanagerment.define.YMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

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
            day = YMD.daysSpecial[month - 1];
        } else {
            day = YMD.daysNormal[month - 1];
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
            day = YMD.daysSpecial[month - 1];
        } else {
            day = YMD.daysNormal[month - 1];
        }
        stringBuffer.append(String.valueOf(day));
        stringBuffer.append(" 23:59:59");

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
}