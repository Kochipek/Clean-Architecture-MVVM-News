package com.kochipek.news_app.data.source.remote

import com.kochipek.news_app.data.api.NewsApiService
import com.kochipek.news_app.data.model.NewsApiResponse
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,
) : NewsRemoteDataSource {
    override suspend fun getNews(
        country: String,
        page: Int
    ): Response<NewsApiResponse> {
        return newsApiService.getNews(country, page)
    }
}