package com.example.android.readeveryday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.readeveryday.UI.HistoryArticleAdapter;
import com.example.android.readeveryday.Util.DateUtil;
import com.example.android.readeveryday.Util.HttpUtil;
import com.example.android.readeveryday.model.Article;
import com.example.android.readeveryday.model.Data;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<Data> historyList = new ArrayList<>();
    private volatile String date = DateUtil.getToday();
    private RecyclerView recyclerView;
    private HistoryArticleAdapter adapter;
    private volatile static boolean resultFlag;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("历史文章");
        }
        pref = getSharedPreferences("data", MODE_APPEND);
        recyclerView = (RecyclerView) findViewById(R.id.history_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryArticleAdapter(historyList);
        recyclerView.setAdapter(adapter);
        initHistory();
        Log.d("HistoryActivity", String.valueOf(adapter.getItemCount()));
    }

    public List<Data> initHistory() {
        for (int i = 1; i < 21; i++) {
            date = DateUtil.getPreviousDay(date);
            Data item = query(date);
            if (item != null) {
                historyList.add(item);
                adapter.notifyDataSetChanged();
            } else {
                break;
            }
        }
        return historyList;
    }

    public Data query(final String date) {
        Data data;
        resultFlag = false;
        String jsonData = pref.getString(date, "");
        if (!"".equals(jsonData)) {
            Gson gson = new Gson();
            Article art = gson.fromJson(jsonData, Article.class);
            data = art.data;
        } else {
            queryFromServer(date);
            if (resultFlag) {
                data = query(date);
            } else {
                Toast.makeText(this, "请求过于频繁，请稍后再试!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return data;
    }

    public void queryFromServer(final String date) {
        String address = "https://interface.meiriyiwen.com/article/day?dev=1&date=" + date;
        HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    if (!"Too Many Attempts.".equals(responseData)) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(date, responseData);
                        resultFlag = editor.commit();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HistoryActivity.this, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

//    public List<Data> initHistory() {
//        date = DateUtil.getPreviousDay(date);
//        for (int i = 1; i < 21; i++) {
//            Call<Article> call = RetrofitUtil.initRetrofit(date);
//            call.enqueue(new Callback<Article>() {
//                @Override
//                public void onResponse(Call<Article> call, Response<Article> response) {
//                    Article art = response.body();
//                    try {
//                        historyList.add(0, art.data);
//                        adapter.notifyItemInserted(0);
//                        recyclerView.scrollToPosition(0);
//                        Log.d("HistoryActivity", "本条数据获取完毕");
//                    } catch (NullPointerException e) {
//                        flag = false;
//                        Toast.makeText(HistoryActivity.this, "请稍后再试！", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Article> call, Throwable t) {
//                    t.printStackTrace();
//                    Log.d("HistoryActivity", "数据获取出错");
//                }
//            });
//            if (!flag) {
//                break;
//            }
//            date = DateUtil.getPreviousDay(date);
//        }
//        return historyList;
//    }

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
