package com.zinchuk.data.di

import com.zinchuk.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class PublicModule {
    @Provides
    fun provideMovieRepository(
        @Named("Internal") internal: MovieRepository,
    ): MovieRepository = internal
}
