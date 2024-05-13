package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke() : Flow<List<Article>> = newsRepository.getSavedNews()
}