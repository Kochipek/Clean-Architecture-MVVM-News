package com.kochipek.news_app.data.repository.source.local

import com.kochipek.news_app.data.local.dao.ArticleDao
import com.kochipek.news_app.data.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NewsLocalDataSourceImpl @Inject constructor(private val articleDao: ArticleDao) :
    NewsLocalDataSource {
    override suspend fun saveNewsToDatabase(article: Article) {
        articleDao.insertAll(article)
    }

    override suspend fun deleteNewsFromDatabase(article: Article) {
        articleDao.deleteArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }
}