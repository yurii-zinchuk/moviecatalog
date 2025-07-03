package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import com.zinchuk.domain.models.Movie
import javax.inject.Inject

internal class MovieDomainToFavouriteEntityMapper
    @Inject
    constructor() {
        fun map(data: Movie): MovieFavouriteEntity {
            return MovieFavouriteEntity(
                id = data.id,
                title = data.title,
                posterPath = data.posterPath,
                releaseDate = data.releaseDate,
                voteAverage = data.voteAverage,
                overview = data.overview,
            )
        }
    }
