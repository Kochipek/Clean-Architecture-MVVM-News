package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country : String, page : Int) : Resource<List<NewsApiResponse>> = newsRepository.getNews(country,page)
}