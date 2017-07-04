package com.example.android.readeveryday.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XF on 2017/4/23.
 */

public class Data {

    public String author;

    public String title;

    public String digest;

    public String content;

    @SerializedName("date")
    public DateChain dateChain;

    public class DateChain {
        public String curr;
        public String prev;
        public String next;
    }
}
