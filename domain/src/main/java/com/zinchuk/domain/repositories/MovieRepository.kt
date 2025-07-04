package com.zinchuk.domain.repositories

import androidx.paging.PagingData
import com.zinchuk.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPagedMovies(): Flow<PagingData<Movie>>
    fun getCachedMovies(): Flow<PagingData<Movie>>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(id: Int): Boolean
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteMoviesPaged(): Flow<PagingData<Movie>>
}
