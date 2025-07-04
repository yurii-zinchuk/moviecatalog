package com.zinchuk.domain.usecases

import androidx.paging.PagingData
import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = repository.getCachedMovies()
}
