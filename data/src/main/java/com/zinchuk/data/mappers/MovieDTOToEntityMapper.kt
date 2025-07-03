package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import javax.inject.Inject

internal class MovieDTOToEntityMapper
    @Inject
    constructor() {
        fun map(data: MovieDTO): MovieEntity {
            return MovieEntity(
                id = data.id,
                title = data.title,
                posterPath = data.posterPath,
                releaseDate = data.releaseDate,
                voteAverage = data.voteAverage,
                overview = data.overview,
            )
        }
    }
