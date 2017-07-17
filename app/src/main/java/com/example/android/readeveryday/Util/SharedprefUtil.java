package com.example.android.readeveryday.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by XF on 2017/7/5.
 */

public class SharedprefUtil {

    // 以下五个方法用于收藏的文章（获取、保存、删除、判断是否存在）
    public static String get(Context context, String date) {
        SharedPreferences pref = context.getSharedPreferences("favourite", Context.MODE_PRIVATE);
        return pref.getString(date, null);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences pref = context.getSharedPreferences("favourite", Context.MODE_PRIVATE);
        return pref.getAll();
    }

    public static boolean save(Context context, String date, String jsonData) {
        SharedPreferences pref = context.getSharedPreferences("favourite", Context.MODE_APPEND);
        return pref.edit().putString(date, jsonData).commit();
    }

    public static boolean contains(Context context, String date) {
        SharedPreferences pref = context.getSharedPreferences("favourite", Context.MODE_PRIVATE);
        return pref.contains(date);
    }

    public static boolean delete(Context context, String date) {
        SharedPreferences pref = context.getSharedPreferences("favourite", Context.MODE_APPEND);
        return pref.edit().remove(date).commit();
    }

    //以下两个方法用于获取和保存夜间模式状态
    public static boolean getNightMode(Context context) {
        SharedPreferences pref = context.getSharedPreferences("nightmode", Context.MODE_PRIVATE);
        return pref.getBoolean("NightMode", false);
    }

    public static boolean setNightMode(Context context, Boolean isNightMode) {
        SharedPreferences pref = context.getSharedPreferences("nightmode", Context.MODE_PRIVATE);
        return pref.edit().putBoolean("NightMode", isNightMode).commit();
    }

    // 以下两个方法用于获取和保存临时数据
    public static String getTemp(Context context) {
        SharedPreferences pref = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
        return pref.getString("Temp", null);
    }

    public static boolean setTemp(Context context, String jsonData) {
        SharedPreferences pref = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
        return pref.edit().putString("Temp", jsonData).commit();
    }

    // 一下两个方法用于获取和保存字号
    public static int getTextSize(Context context) {
        SharedPreferences pref = context.getSharedPreferences("textsize", Context.MODE_PRIVATE);
        return pref.getInt("textsize", 0);
    }

    public static boolean setTextSize(Context context, int size) {
        SharedPreferences pref = context.getSharedPreferences("textsize", Context.MODE_PRIVATE);
        return pref.edit().putInt("textsize", size).commit();
    }
    // 以下两个方法用于获取和保存背景色
    public static String getBackground(Context context) {
        SharedPreferences pref = context.getSharedPreferences("background", Context.MODE_PRIVATE);
        String color = pref.getString("background", null);
        if (color != null) {
            switch (color) {
                case "white":
                    color = "#FFFFFF";
                    break;
                case "yellow":
                    color = "#EDE7DA";
                    break;
                case "green":
                    color = "#bcd5bf";
                    break;
            }
        }
        return color;
    }

    public static boolean setBackground(Context context, String color) {
        SharedPreferences pref = context.getSharedPreferences("background", Context.MODE_PRIVATE);
        return pref.edit().putString("background", color).commit();
    }
}
