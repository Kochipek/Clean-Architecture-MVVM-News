package com.kochipek.news_app.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kochipek.news_app.data.model.Article
import kotlinx.coroutines.launch

class NewsDetailsViewModel : ViewModel() {

    private val _articleDetails = MutableLiveData<Article>()
    val articleDetails: LiveData<Article> = _articleDetails

    fun fetchNewsDetails(article: Article) {
        viewModelScope.launch {
            _articleDetails.value = article
        }
    }
}
