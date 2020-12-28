package com.picsart.bookstorelibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    public static int getDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static String convertDateToString(Date date) {
        return DATE_FORMATTER.format(date);
    }

    public static Date convertStringToDate(String date) throws ParseException {
        return DATE_FORMATTER.parse(date);
    }
}
