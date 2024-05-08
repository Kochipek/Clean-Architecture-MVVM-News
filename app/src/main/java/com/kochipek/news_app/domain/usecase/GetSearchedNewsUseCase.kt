package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(searchQuery: String) : Resource<NewsApiResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }
}