package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository
import javax.inject.Inject


class GetSearchedNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(searchQuery: String, id: Int): Resource<NewsApiResponse> {
        return newsRepository.getSearchedNews(searchQuery, id)
    }
}