package com.zinchuk.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val overview: String
)