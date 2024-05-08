package com.kochipek.news_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kochipek.news_app.domain.usecase.GetNewsUseCase

class NewsViewModelFactory(private val getNewsUseCase: GetNewsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(getNewsUseCase) as T
    }
}