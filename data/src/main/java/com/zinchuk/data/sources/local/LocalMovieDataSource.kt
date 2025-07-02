package com.zinchuk.data.sources.local

import com.zinchuk.data.sources.local.room.dao.MovieCachedDao
import com.zinchuk.data.sources.local.room.dao.MovieFavouriteDao
import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import javax.inject.Inject

internal class LocalMovieDataSource @Inject constructor(
    private val cachedDao: MovieCachedDao,
    private val favouriteDao: MovieFavouriteDao,
) {

    suspend fun updateCache(movies: List<MovieEntity>) {
        cachedDao.run {
            clearAll()
            insert(movies)
        }
    }

    suspend fun getCached(): List<MovieEntity> {
        return cachedDao.getAll()
    }

    suspend fun getFavorites(): List<MovieFavouriteEntity> = favouriteDao.getAll()

    suspend fun addFavorite(movie: MovieFavouriteEntity) = favouriteDao.add(movie)

    suspend fun removeFavorite(movie: MovieFavouriteEntity) = favouriteDao.delete(movie)

    suspend fun isFavorite(id: Int): Boolean = favouriteDao.isFavorite(id)
}
