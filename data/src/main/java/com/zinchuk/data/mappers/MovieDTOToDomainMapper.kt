package com.zinchuk.data.mappers

import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import com.zinchuk.domain.models.Movie
import javax.inject.Inject

internal class MovieDTOToDomainMapper @Inject constructor() {
    fun map(data: MovieDTO): Movie {
        return Movie(
            id = data.id,
            title = data.title,
            posterPath = data.posterPath,
            releaseDate = data.releaseDate,
            voteAverage = data.voteAverage,
            overview = data.overview,
        )
    }
}
