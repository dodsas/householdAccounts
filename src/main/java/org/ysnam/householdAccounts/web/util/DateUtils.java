package org.ysnam.householdAccounts.web.util;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    public static String[] getAllMonth(){
        return new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
    }

    public static String getEndOfDateAsString(String startDate_yyyy_mm_dd){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(startDate_yyyy_mm_dd));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.HOUR, 24);
        calendar.set(Calendar.MINUTE, 60);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date convertToDate(String yyyy_mm_dd){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(yyyy_mm_dd);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("invalid date");
        }
    }

    public static String getCurrentMonth(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return Integer.toString(1 + calendar.get(Calendar.MONTH));
    }

    public static Date getEndOfDate(Date startDate){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return calendar.getTime();
    }
}
