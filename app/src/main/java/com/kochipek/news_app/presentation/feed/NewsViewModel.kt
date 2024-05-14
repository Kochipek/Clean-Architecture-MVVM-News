package com.kochipek.news_app.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.NetworkHelper
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.repository.NewsRepository
import com.kochipek.news_app.domain.usecase.GetNewsPagingUseCase
import com.kochipek.news_app.domain.usecase.GetNewsUseCase
import com.kochipek.news_app.domain.usecase.GetSearchedNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val networkHelper: NetworkHelper,
    private val getNewsPagingUseCase: GetNewsPagingUseCase,
) : ViewModel() {

    private val _news: MutableLiveData<Resource<NewsApiResponse>> = MutableLiveData()
    val news: LiveData<Resource<NewsApiResponse>> = _news
    fun getNewsPaging(country: String): Flow<PagingData<Article>> {
        return getNewsPagingUseCase.invoke(country)
            .cachedIn(viewModelScope)
    }
    fun getNews(country: String, page: Int) {
        viewModelScope.launch {
            _news.postValue(Resource.Loading())
            if (networkHelper.isInternetAvailable()) {
                try {
                    val response = getNewsUseCase(country, page)
                    _news.postValue(response)
                } catch (e: Exception) {
                    _news.postValue(Resource.Error("Something went wrong"))
                }
            } else {
                _news.postValue(Resource.Error("Internet is not available"))
            }
        }
    }

    fun getSearchedNews(searchQuery: String, page: Int) {
        viewModelScope.launch {
            _news.postValue(Resource.Loading())
            if (networkHelper.isInternetAvailable()) {
                try {
                    val response = getSearchedNewsUseCase(searchQuery, page)
                    _news.postValue(response)
                } catch (e: Exception) {
                    _news.postValue(Resource.Error("Something went wrong"))
                }
            } else {
                _news.postValue(Resource.Error("Internet is not available"))
            }
        }
    }

}