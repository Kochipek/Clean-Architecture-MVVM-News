package com.kochipek.news_app.data.repository.source.remote

import com.kochipek.news_app.data.api.NewsApiService
import com.kochipek.news_app.data.model.NewsApiResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService,
) : NewsRemoteDataSource {
    override suspend fun getNews(
        country: String,
        page: Int
    ): Response<NewsApiResponse> {
        return newsApiService.getNews(country, page)
    }
}