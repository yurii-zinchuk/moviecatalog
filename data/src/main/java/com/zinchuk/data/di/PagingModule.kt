package com.zinchuk.data.di

import com.zinchuk.data.sources.remote.RemoteMovieDataSource
import com.zinchuk.data.sources.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class PagingModule {
    @Provides
    fun providePagingSourceFactory(api: MovieApi): () -> RemoteMovieDataSource {
        return { RemoteMovieDataSource(api) }
    }
}
