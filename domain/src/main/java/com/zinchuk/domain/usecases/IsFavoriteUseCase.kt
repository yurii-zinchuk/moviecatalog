package com.zinchuk.domain.usecases

import com.zinchuk.domain.repositories.MovieRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Boolean = repository.isFavorite(id)
}
