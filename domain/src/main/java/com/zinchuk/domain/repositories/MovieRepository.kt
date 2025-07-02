package com.zinchuk.domain.repositories

import androidx.paging.PagingData
import com.zinchuk.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPagedMovies(): Flow<PagingData<Movie>>
    suspend fun getCachedFirstPage(): List<Movie>
    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(id: Int): Boolean
}
