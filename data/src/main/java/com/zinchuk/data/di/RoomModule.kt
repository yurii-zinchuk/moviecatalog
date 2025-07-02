package com.zinchuk.data.di

import android.content.Context
import com.zinchuk.data.sources.local.room.AppDatabase
import com.zinchuk.data.sources.local.room.dao.MovieCachedDao
import com.zinchuk.data.sources.local.room.dao.MovieFavouriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideCachedMovieDao(database: AppDatabase): MovieCachedDao = database.cachedDao()

    @Provides
    @Singleton
    fun provideFavouriteMovieDao(database: AppDatabase): MovieFavouriteDao = database.favouriteDao()
}
