package com.zinchuk.domain.usecases

import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()
}
