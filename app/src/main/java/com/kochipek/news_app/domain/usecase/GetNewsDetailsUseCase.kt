package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.domain.repository.NewsRepository

class GetNewsDetailsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(article: Article) = newsRepository.getNewsDetails(article)
}
