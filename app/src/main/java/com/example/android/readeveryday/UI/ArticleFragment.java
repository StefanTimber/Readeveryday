package com.example.android.readeveryday.UI;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.readeveryday.MyApplication;
import com.example.android.readeveryday.R;
import com.example.android.readeveryday.Util.ParseHtml;
import com.example.android.readeveryday.Util.RetrofitUtil;
import com.example.android.readeveryday.Util.SharedprefUtil;
import com.example.android.readeveryday.model.Article;
import com.example.android.readeveryday.model.Data;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by XF on 2017/4/24.
 */

public class ArticleFragment extends Fragment {

    private View view;
    public static final String DATE = "DATE";
    private String mDate;
    private SwipeRefreshLayout swipeRefresh;
    private ToggleButton like;
    private Data data;

    public static ArticleFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString(DATE, date);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = getArguments().getString(DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.article_fragment, container, false);
        like = (ToggleButton) view.findViewById(R.id.like);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setRefreshing(true);
        initView();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
            }
        });
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                like.setChecked(isChecked);
                if (data != null) {
                    if (isChecked) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(data);
                        SharedprefUtil.save(view.getContext(), data.dateChain.curr, jsonData);
                    } else {
                        SharedprefUtil.delete(view.getContext(), data.dateChain.curr);

                    }
                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.isChecked()) {
                    Snackbar.make(v, "已收藏", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "已取消收藏", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (data != null) {
            updateLikeState(data.dateChain.curr);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (data != null && "".equals(mDate)) {
            SharedprefUtil.setTemp(view.getContext(), new Gson().toJson(data));
        }
    }

    // 若是因夜间模式重建的，从缓存中读取上次destroy保存的数据，保证数据不变，否则从服务器获取新数据
    public void initView() {
        if (MyApplication.getRecreate() && "".equals(mDate)) {
            String jsonData = SharedprefUtil.getTemp(view.getContext());
            data = new Gson().fromJson(jsonData, Data.class);
            refresh(data);
            swipeRefresh.setRefreshing(false);
            MyApplication.setRecreate(false);
        }
        else {
            Call<Article> call = RetrofitUtil.initRetrofit(mDate);
            call.enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    Article art = response.body();
                    try {
                        data = art.data;
                        refresh(data);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "请稍后再试！", Toast.LENGTH_SHORT).show();
                    } finally {
                        swipeRefresh.setRefreshing(false);
                    }
                }


                @Override
                public void onFailure(Call<Article> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void refresh(Data data) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView authorView = (TextView) view.findViewById(R.id.author);
        TextView contentView = (TextView) view.findViewById(R.id.content);
        titleView.setText((data.title));
        authorView.setText(data.author);
        contentView.setText(ParseHtml.fromHtml(data.content));
        updateLikeState(data.dateChain.curr);
    }

    // 判断是否在收藏中，并更新like按钮状态
    public void updateLikeState(String date) {
        if (SharedprefUtil.contains(view.getContext(), date)) {
            like.setChecked(true);
        } else {
            like.setChecked(false);
        }
    }
}
