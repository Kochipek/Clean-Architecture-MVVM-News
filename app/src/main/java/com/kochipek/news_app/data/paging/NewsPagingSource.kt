package com.kochipek.news_app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.repository.source.remote.NewsRemoteDataSource

class NewsPagingSource(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val country: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsRemoteDataSource.getNews(country, page)
            LoadResult.Page(
                data = response.body()?.articles ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()?.articles.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }
}
