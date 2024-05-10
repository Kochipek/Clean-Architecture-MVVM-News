package com.kochipek.news_app.data.repository

import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.repository.source.remote.NewsRemoteDataSource
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource

) : NewsRepository {

    // I converted the Response from retrofit to Resource type  ( which I have created) so that I can handle the response in the view model with the help of Resource class
    private fun responseToResource(response: Response<NewsApiResponse>): Resource<NewsApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNews(country : String, page: Int): Resource<NewsApiResponse> {
        // retrofit response is converted to Resource type and returned to use in viewmodel
        return responseToResource(newsRemoteDataSource.getNews(country, page))
    }

    override suspend fun getSearchedNews(searchQuery: String): Resource<NewsApiResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsDetails(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}