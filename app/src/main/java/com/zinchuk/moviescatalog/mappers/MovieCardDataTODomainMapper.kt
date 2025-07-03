package com.zinchuk.moviescatalog.mappers

import com.zinchuk.domain.models.Movie
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import javax.inject.Inject

internal class MovieCardDataTODomainMapper
    @Inject
    constructor() {
        fun map(data: MovieCardData): Movie {
            return Movie(
                id = data.id,
                title = data.title,
                posterPath = data.posterPath,
                voteAverage = data.voteAverage,
                overview = data.overview,
                releaseDate = data.releaseDate,
            )
        }
    }
