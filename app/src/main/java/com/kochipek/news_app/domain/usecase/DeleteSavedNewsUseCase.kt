package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.Article
import com.kochipek.news_app.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)

}