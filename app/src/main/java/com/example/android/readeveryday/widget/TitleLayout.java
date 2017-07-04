package com.example.android.readeveryday.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.readeveryday.HistoryActivity;
import com.example.android.readeveryday.R;

/**
 * Created by XF on 2017/4/13.
 */

public class TitleLayout extends LinearLayout {

    public TitleLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button history = (Button) findViewById(R.id.history);
        Button random = (Button) findViewById(R.id.random);
        history.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                context.startActivity(intent);
            }
        });
        random.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
