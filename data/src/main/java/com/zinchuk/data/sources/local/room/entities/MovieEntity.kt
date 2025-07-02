package com.zinchuk.data.sources.local.room.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id"], tableName = "cached_movies")
internal data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val overview: String
)
