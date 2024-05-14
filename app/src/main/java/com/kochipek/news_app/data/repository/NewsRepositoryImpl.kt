package com.kochipek.news_app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.paging.NewsPagingSource
import com.kochipek.news_app.data.repository.source.local.NewsLocalDataSource
import com.kochipek.news_app.data.repository.source.remote.NewsRemoteDataSource
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
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

    override suspend fun getSearchedNews(
        searchQuery: String,
        page: Int
    ): Resource<NewsApiResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(searchQuery, page))
    }

    override suspend fun saveNews(article: Article) {
        return newsLocalDataSource.saveNewsToDatabase(article)
    }

    override suspend fun deleteNews(article: Article) {
        return newsLocalDataSource.deleteNewsFromDatabase(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedNews()
    }
    override fun getNewsPaging(country: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(newsRemoteDataSource, country) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}