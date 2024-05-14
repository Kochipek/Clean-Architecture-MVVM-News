package com.kochipek.news_app.domain.usecase

import androidx.paging.PagingData
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsPagingUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun invoke(country: String): Flow<PagingData<Article>> {
        return newsRepository.getNewsPaging(country)
    }
}