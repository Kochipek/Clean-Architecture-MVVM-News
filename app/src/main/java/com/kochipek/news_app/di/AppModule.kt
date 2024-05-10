package com.kochipek.news_app.di

import android.app.Application
import com.kochipek.news_app.data.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNetworkHelper(application: Application): NetworkHelper {
        return NetworkHelper(application)
    }
}