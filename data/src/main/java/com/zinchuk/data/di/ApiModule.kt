package com.zinchuk.data.di

import com.zinchuk.data.sources.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {
    @Provides
    fun provideProcessingApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}
