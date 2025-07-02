package com.zinchuk.domain.usecases

import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.repositories.MovieRepository
import javax.inject.Inject

class GetCachedFirstPageUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getCachedFirstPage()
}
