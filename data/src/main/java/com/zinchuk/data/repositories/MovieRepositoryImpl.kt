package com.zinchuk.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zinchuk.data.mappers.MovieDTOToDomainMapper
import com.zinchuk.data.mappers.MovieDTOToEntityMapper
import com.zinchuk.data.mappers.MovieDomainToFavouriteEntityMapper
import com.zinchuk.data.mappers.MovieEntityToDomainMapper
import com.zinchuk.data.mappers.MovieFavouriteEntityToDomainMapper
import com.zinchuk.data.sources.local.LocalMovieDataSource
import com.zinchuk.data.sources.remote.RemoteMovieDataSource
import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 20

internal class MovieRepositoryImpl
    @Inject
    constructor(
        private val remoteMovieDataSource: () -> RemoteMovieDataSource,
        private val localMovieDataSource: LocalMovieDataSource,
        private val movieDTOToDomainMapper: MovieDTOToDomainMapper,
        private val movieEntityToDomainMapper: MovieEntityToDomainMapper,
        private val movieFavouriteEntityToDomainMapper: MovieFavouriteEntityToDomainMapper,
        private val movieDomainToFavouriteEntityMapper: MovieDomainToFavouriteEntityMapper,
        private val movieDTOToEntityMapper: MovieDTOToEntityMapper,
    ) : MovieRepository {
        override fun getPagedMovies(): Flow<PagingData<Movie>> {
            return Pager(
                config =
                    PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        initialLoadSize = PAGE_SIZE,
                    ),
                pagingSourceFactory = {
                    remoteMovieDataSource().apply {
                        onFirstPageLoaded = ::updateCache
                    }
                },
            )
                .flow
                .map { pagingData ->
                    pagingData.map { movieDTOToDomainMapper.map(it) }
                }
        }

        override fun getCachedMovies(): Flow<PagingData<Movie>> {
            return Pager(
                config =
                    PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = { localMovieDataSource.getCached() },
            ).flow.map { pagingData ->
                pagingData.map { movieEntityToDomainMapper.map(it) }
            }
        }

        override suspend fun addFavorite(movie: Movie) {
            movieDomainToFavouriteEntityMapper.map(movie)
                .let { localMovieDataSource.addFavorite(it) }
        }

        override suspend fun removeFavorite(movie: Movie) {
            movieDomainToFavouriteEntityMapper.map(movie)
                .let { localMovieDataSource.removeFavorite(it) }
        }

        override suspend fun isFavorite(id: Int): Boolean {
            return localMovieDataSource.isFavorite(id)
        }

        override fun getFavoriteMovies(): Flow<List<Movie>> {
            return localMovieDataSource.getFavorites()
                .map { list -> list.map { movieFavouriteEntityToDomainMapper.map(it) } }
        }

        override fun getFavoriteMoviesPaged(): Flow<PagingData<Movie>> {
            return Pager(
                config =
                    PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = { localMovieDataSource.getFavoritesPaged() },
            ).flow.map { pagingData ->
                pagingData.map { movieFavouriteEntityToDomainMapper.map(it) }
            }
        }

        private suspend fun updateCache(movies: List<MovieDTO>) {
            movies
                .map { movieDTOToEntityMapper.map(it) }
                .let { localMovieDataSource.updateCache(it) }
        }
    }
