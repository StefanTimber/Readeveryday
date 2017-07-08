package com.example.android.readeveryday;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.android.readeveryday.Util.SharedprefUtil;

/**
 * Created by XF on 2017/7/6.
 */

public class MyApplication extends Application {

    public static boolean isNightMode;

    public static boolean isRecreate;

    public static boolean getNightMode() {
        return isNightMode;
    }

    public static void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }

    public static boolean getRecreate() {
        return isRecreate;
    }

    public static void setRecreate(boolean recreate) {
        isRecreate = recreate;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isNightMode = SharedprefUtil.getNightMode(this);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
