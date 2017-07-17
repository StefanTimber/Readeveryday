package com.example.android.readeveryday;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.readeveryday.UI.FavouriteArticleAdapter;
import com.example.android.readeveryday.Util.SharedprefUtil;
import com.example.android.readeveryday.model.Data;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouriteActivity extends AppCompatActivity {

    private List<Data> favouriteList = new ArrayList<>();
    private FavouriteArticleAdapter adapter;
    private boolean nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        nightMode = MyApplication.getNightMode();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("收藏");
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favourite_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavouriteArticleAdapter(favouriteList);
        recyclerView.setAdapter(adapter);
        Log.d("FavouriteActivity", String.valueOf(adapter.getItemCount()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initFavourite();
        adapter.notifyDataSetChanged();
        if (nightMode != MyApplication.getNightMode()) {
            this.recreate();
        }
        if (!MyApplication.getNightMode()) {
            String color = SharedprefUtil.getBackground(this);
            if (color != null) {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
                coordinatorLayout.setBackgroundColor(Color.parseColor(color));
            }
        }
    }

    public List<Data> initFavourite() {
        favouriteList.clear();
        Map<String, String> jsonDataMap = (Map<String, String>) SharedprefUtil.getAll(this);
        Gson gson = new Gson();
        for (String value : jsonDataMap.values()) {
            favouriteList.add(gson.fromJson(value, Data.class));
        }
        return favouriteList;
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
}
