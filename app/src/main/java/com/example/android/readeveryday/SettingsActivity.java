package com.example.android.readeveryday;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.readeveryday.Util.SharedprefUtil;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton background_white;
    private ImageButton background_yellow;
    private ImageButton background_green;
    private Button textSize_small;
    private Button textSize_medium;
    private Button textSize_large;
    private SwitchCompat nightModeSwitch;
    private TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        rating = (TextView)findViewById(R.id.rating);
        rating.setOnClickListener(this);

        background_white = (ImageButton) findViewById(R.id.background_white);
        background_white.setOnClickListener(this);
        background_yellow = (ImageButton) findViewById(R.id.background_yellow);
        background_yellow.setOnClickListener(this);
        background_green = (ImageButton) findViewById(R.id.background_green);
        background_green.setOnClickListener(this);
        initBackground();

        textSize_small = (Button) findViewById(R.id.textsize_small);
        textSize_small.setOnClickListener(this);
        textSize_medium = (Button) findViewById(R.id.textsize_medium);
        textSize_medium.setOnClickListener(this);
        textSize_large = (Button) findViewById(R.id.textsize_large);
        textSize_large.setOnClickListener(this);
        initTextSize();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("设置");

        }

        nightModeSwitch = (SwitchCompat) findViewById(R.id.nightmode_switch);
        if (SharedprefUtil.getNightMode(this)) {
            nightModeSwitch.setChecked(true);
        }
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                SharedprefUtil.setNightMode(buttonView.getContext(), isChecked);
                MyApplication.setNightMode(isChecked);
                Activity activity = (Activity)buttonView.getContext();
                activity.recreate();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background_white:
                backgroundChange("white");
                background_white.setSelected(true);
                background_yellow.setSelected(false);
                background_green.setSelected(false);
                break;
            case R.id.background_yellow:
                backgroundChange("yellow");
                background_yellow.setSelected(true);
                background_white.setSelected(false);
                background_green.setSelected(false);
                break;
            case R.id.background_green:
                backgroundChange("green");
                background_green.setSelected(true);
                background_white.setSelected(false);
                background_yellow.setSelected(false);
                break;
            case R.id.textsize_small:
                SharedprefUtil.setTextSize(this,16);
                textSize_small.setSelected(true);
                textSize_medium.setSelected(false);
                textSize_large.setSelected(false);
                break;
            case R.id.textsize_medium:
                SharedprefUtil.setTextSize(this, 18);
                textSize_medium.setSelected(true);
                textSize_small.setSelected(false);
                textSize_large.setSelected(false);
                break;
            case R.id.textsize_large:
                SharedprefUtil.setTextSize(this, 20);
                textSize_large.setSelected(true);
                textSize_small.setSelected(false);
                textSize_medium.setSelected(false);
                break;
            case R.id.rating:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "未安装应用市场", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
        }
    }

    // 选择背景色时先取消夜间模式，然后保存背景色
    private void backgroundChange(String color) {
        if (nightModeSwitch.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedprefUtil.setNightMode(this, false);
            MyApplication.setNightMode(false);
            nightModeSwitch.setChecked(false);
        }
        SharedprefUtil.setBackground(this, color);
    }

    // 设置初始背景按钮状态
    private void initBackground() {
        String color = SharedprefUtil.getBackground(this);
        if (color != null) {
            switch (color) {
                case "#FFFFFF":
                    background_white.setSelected(true);
                    break;
                case "#EDE7DA":
                    background_yellow.setSelected(true);
                    break;
                case "#bcd5bf":
                    background_green.setSelected(true);
                    break;
            }
        }
    }

    // 设置初始字号按钮状态
    private void initTextSize() {
        int size = SharedprefUtil.getTextSize(this);
        if (size != 0) {
            switch (size) {
                case 16:
                    textSize_small.setSelected(true);
                    break;
                case 18:
                    textSize_medium.setSelected(true);
                    break;
                case 20:
                    textSize_large.setSelected(true);
                    break;
            }
        }
    }
}
