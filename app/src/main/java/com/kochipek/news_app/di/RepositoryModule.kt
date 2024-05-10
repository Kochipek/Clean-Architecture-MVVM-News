package com.kochipek.news_app.di

import com.kochipek.news_app.data.api.NewsApiService
import com.kochipek.news_app.data.repository.NewsRepositoryImpl
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
    fun provideRepository(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSourceImpl)
    }

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService) : NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}