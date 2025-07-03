package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.domain.models.Movie
import javax.inject.Inject

internal class MovieEntityToDomainMapper
    @Inject
    constructor() {
        fun map(data: MovieEntity): Movie {
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
