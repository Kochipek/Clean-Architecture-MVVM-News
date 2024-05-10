package com.kochipek.news_app.domain.usecase

import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(country : String, page : Int) : Resource<NewsApiResponse> = newsRepository.getNews(country,page)
}