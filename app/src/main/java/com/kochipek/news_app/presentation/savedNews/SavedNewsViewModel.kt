package com.kochipek.news_app.presentation.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.domain.usecase.GetSavedNewsUseCase
import com.kochipek.news_app.domain.usecase.SaveNewsUseCase
import com.kochipek.news_app.domain.usecase.DeleteSavedNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModel() {

    fun getSavedNews() = liveData {
        getSavedNewsUseCase().collect {
            emit(it)
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase(article)
    }
}
