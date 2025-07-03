package com.zinchuk.data.sources.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinchuk.data.sources.local.room.entities.MovieEntity


@Dao
internal interface MovieCachedDao {
    @Query("SELECT * FROM cached_movies ORDER BY releaseDate DESC")
    fun getAll(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MovieEntity>)

    @Query("DELETE FROM cached_movies")
    suspend fun clearAll()
}
