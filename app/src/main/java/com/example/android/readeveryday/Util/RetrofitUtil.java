package com.example.android.readeveryday.Util;

import com.example.android.readeveryday.model.Article;
import com.example.android.readeveryday.model.ArticleService;
import com.example.android.readeveryday.model.Constant;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XF on 2017/5/4.
 */

public class RetrofitUtil {

    public static Call<Article> initRetrofit(String date) {

        Call<Article> call;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleService service = retrofit.create(ArticleService.class);

        if (date.equals("")) {
            call = service.getRandomArticle();
        } else {
            call = service.getTargetArticle(1, date);
        }

        return call;
    }


}
