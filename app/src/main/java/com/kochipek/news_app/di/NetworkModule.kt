package com.kochipek.news_app.di

import com.kochipek.news_app.BuildConfig
import com.kochipek.news_app.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule{

    // provides retrofit instance to the app
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    // provides news api service to the app
    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit) : NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}