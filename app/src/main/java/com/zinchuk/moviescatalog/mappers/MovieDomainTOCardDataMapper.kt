package com.zinchuk.moviescatalog.mappers

import com.zinchuk.domain.models.Movie
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import javax.inject.Inject

internal class MovieDomainTOCardDataMapper @Inject constructor() {
    fun map(data: Movie, isFavourite: Boolean): MovieCardData {
        return MovieCardData(
            id = data.id,
            title = data.title,
            posterPath = IMAGE_BASE_URL + data.posterPath,
            voteAverage = data.voteAverage,
            overview = data.overview,
            isFavorite = isFavourite,
            releaseDate = data.releaseDate,
        )
    }

    private companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154"
    }
}
