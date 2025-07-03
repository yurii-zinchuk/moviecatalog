package com.zinchuk.moviescatalog.ui.components.movie_card

internal data class MovieCardData(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val overview: String,
    val isFavorite: Boolean,
    val releaseDate: String?,
)
