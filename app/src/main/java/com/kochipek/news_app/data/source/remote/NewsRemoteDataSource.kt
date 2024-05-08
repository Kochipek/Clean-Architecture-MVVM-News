package com.kochipek.news_app.data.source.remote

import com.kochipek.news_app.data.model.NewsApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNews(country: String, page: Int): Response<NewsApiResponse>
}