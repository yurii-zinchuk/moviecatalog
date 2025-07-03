package com.zinchuk.data.sources.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MovieFavouriteDao {
    @Query("SELECT * FROM favorite_movies ORDER BY releaseDate DESC")
    fun getAll(): Flow<List<MovieFavouriteEntity>>

    @Query("SELECT * FROM favorite_movies ORDER BY releaseDate DESC")
    fun getAllPaged(): PagingSource<Int, MovieFavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(movie: MovieFavouriteEntity)

    @Delete
    suspend fun delete(movie: MovieFavouriteEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
