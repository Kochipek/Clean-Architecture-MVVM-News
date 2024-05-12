package com.kochipek.news_app.domain.repository

import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    // Resource class is a wrapper class that will help me to handle the response status before the data is fetched
    suspend fun getNews(country: String, page : Int) : Resource<NewsApiResponse>
    suspend fun getSearchedNews(searchQuery: String, page: Int): Resource<NewsApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    // Using Flow to get the data from the database asynchronously instead of using LiveData because LiveData is lifecycle aware and I'm not using it here.
    // That may cause threading issues
    fun getSavedNews() : Flow<List<Article>>

}