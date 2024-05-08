package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.Article
import com.kochipek.news_app.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
        suspend fun execute(article: Article) = newsRepository.saveNews(article)
}