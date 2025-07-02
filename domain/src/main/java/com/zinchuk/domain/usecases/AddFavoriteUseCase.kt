package com.zinchuk.domain.usecases

import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.repositories.MovieRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.addFavorite(movie)
}
