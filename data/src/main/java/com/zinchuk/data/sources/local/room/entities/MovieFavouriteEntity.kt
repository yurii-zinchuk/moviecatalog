package com.zinchuk.data.sources.local.room.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id"], tableName = "favorite_movies")
internal data class MovieFavouriteEntity(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val overview: String
)
