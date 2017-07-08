package com.example.android.readeveryday.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by XF on 2017/4/23.
 */

public class DateUtil {

    //    private static Calendar c = Calendar.getInstance();
    private static SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");

    public static String getToday() {
        Calendar c = Calendar.getInstance();
        return sim.format(c.getTime());
    }

    public static String getPreviousDay(String day) {
        Calendar c = Calendar.getInstance();
        try {
            Date date = sim.parse(day);
            c.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -1);
        return sim.format(c.getTime());
    }
}
