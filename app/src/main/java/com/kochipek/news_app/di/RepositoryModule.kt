package com.kochipek.news_app.di

import com.kochipek.news_app.data.api.NewsApiService
import com.kochipek.news_app.data.local.dao.ArticleDao
import com.kochipek.news_app.data.repository.NewsRepositoryImpl
import com.kochipek.news_app.data.repository.source.local.NewsLocalDataSource
import com.kochipek.news_app.data.repository.source.local.NewsLocalDataSourceImpl
import com.kochipek.news_app.data.repository.source.remote.NewsRemoteDataSource
import com.kochipek.news_app.data.repository.source.remote.NewsRemoteDataSourceImpl
import com.kochipek.news_app.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }

    @Singleton
    @Provides
    fun provideNewsLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)
    }
}
