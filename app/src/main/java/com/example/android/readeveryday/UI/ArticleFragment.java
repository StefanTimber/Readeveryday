package com.example.android.readeveryday.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.readeveryday.R;
import com.example.android.readeveryday.Util.ParseHtml;
import com.example.android.readeveryday.Util.RetrofitUtil;
import com.example.android.readeveryday.model.Article;
import com.example.android.readeveryday.model.Data;

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
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setRefreshing(true);
        initView();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    initView();
            }
        });
        return view;
    }

    public void initView() {
        Call<Article> call = RetrofitUtil.initRetrofit(mDate);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Article art = response.body();
                try {
                    Data data = art.data;
                    refresh(data.title, data.author, data.content);
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "请稍后再试！",Toast.LENGTH_SHORT).show();
                }
                finally {
                    swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void refresh(String title, String author, String content) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView authorView = (TextView) view.findViewById(R.id.author);
        TextView contentView = (TextView) view.findViewById(R.id.content);
        titleView.setText((title));
        authorView.setText(author);
        contentView.setText(ParseHtml.fromHtml(content));
    }
}
