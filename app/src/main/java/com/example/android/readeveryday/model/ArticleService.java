package com.example.android.readeveryday.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XF on 2017/4/24.
 */

public interface ArticleService {

    @GET("day")
    Call<Article> getTargetArticle(@Query("dev") int dev, @Query("date") String date);

    @GET("random")
    Call<Article> getRandomArticle();
}
