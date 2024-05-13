package com.kochipek.news_app.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.domain.usecase.DeleteSavedNewsUseCase
import com.kochipek.news_app.domain.usecase.GetSavedNewsUseCase
import com.kochipek.news_app.domain.usecase.SaveNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteNewsUseCase: DeleteSavedNewsUseCase
) : ViewModel() {

    private val _articleDetails = MutableLiveData<Article>()
    val articleDetails: LiveData<Article> = _articleDetails

    fun fetchNewsDetails(article: Article) {
        viewModelScope.launch {
            _articleDetails.value = article
        }
    }
    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase(article)
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase().collect {
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteNewsUseCase(article)
    }
}
