package com.example.android.readeveryday.Util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by XF on 2017/5/3.
 */

public class ParseHtml {


    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {

        Spanned result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }

        return result;
    }
}
