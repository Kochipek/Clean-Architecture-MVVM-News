package com.kochipek.news_app.data.repository.source.local

import com.kochipek.news_app.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveNewsToDatabase(article: Article)
    fun getSavedNews(): Flow<List<Article>>
    suspend fun deleteNewsFromDatabase(article: Article)
}