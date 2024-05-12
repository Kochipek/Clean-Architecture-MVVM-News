package com.kochipek.news_app.data.api

import com.kochipek.news_app.BuildConfig
import com.kochipek.news_app.data.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //get the news from the API
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsApiResponse>

    // search for news
    @GET("v2/everything")
    suspend fun getSearchedNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsApiResponse>
}