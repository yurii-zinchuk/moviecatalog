package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import com.zinchuk.domain.Movie
import javax.inject.Inject

internal class MovieFavouriteEntityToDomainMapper @Inject constructor() {
    fun map(data: MovieFavouriteEntity): Movie {
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
