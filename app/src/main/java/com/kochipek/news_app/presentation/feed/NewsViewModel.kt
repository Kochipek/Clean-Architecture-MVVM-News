package com.kochipek.news_app.presentation.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kochipek.news_app.data.model.NewsApiResponse
import com.kochipek.news_app.data.util.NetworkHelper
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    internal val news: MutableLiveData<Resource<NewsApiResponse>> = MutableLiveData()

    fun getNews(country: String, page: Int) {
        viewModelScope.launch {
            news.postValue(Resource.Loading())
            if (networkHelper.isInternetAvailable()) {
                try {
                    val response = getNewsUseCase(country, page)
                    news.postValue(response)
                } catch (e: Exception) {
                    news.postValue(Resource.Error("Something went wrong"))
                }
            } else {
                news.postValue(Resource.Error("Internet is not available"))
            }
        }
    }
}