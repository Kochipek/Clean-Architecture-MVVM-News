package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(searchQuery: String) : Resource<NewsApiResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }
}