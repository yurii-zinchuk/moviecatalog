package com.zinchuk.data.di

import com.zinchuk.data.repositories.MovieRepositoryImpl
import com.zinchuk.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoriesModule {
    @Provides
    @Named("Internal")
    fun provideMovieRepository(impl: MovieRepositoryImpl): MovieRepository = impl
}
