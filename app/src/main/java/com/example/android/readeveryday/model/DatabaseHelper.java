package com.example.android.readeveryday.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by XF on 2017/6/13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_ARTICLE = "create table if not exists Article ("
            + "curr text primary key,"
            + "author text,"
            + "title text,"
            + "content text)";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Article";

    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE);
        Log.i("DatabaseHelper", "db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
