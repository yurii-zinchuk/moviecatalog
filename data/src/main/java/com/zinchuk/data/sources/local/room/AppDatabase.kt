package com.zinchuk.data.sources.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zinchuk.data.sources.local.room.dao.MovieCachedDao
import com.zinchuk.data.sources.local.room.dao.MovieFavouriteDao
import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import javax.inject.Singleton

private const val DATABASE_NAME = "MovieCatalogAppDB"

@Database(entities = [MovieEntity::class, MovieFavouriteEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedDao(): MovieCachedDao
    abstract fun favouriteDao(): MovieFavouriteDao

    internal companion object {
        @Singleton
        fun getInstance(context: Context): AppDatabase =
            Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME,
                ).addMigrations()
                .build()
    }
}
