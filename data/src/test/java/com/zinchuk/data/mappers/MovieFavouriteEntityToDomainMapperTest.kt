package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import com.zinchuk.domain.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieFavouriteEntityToDomainMapperTest {
    private val mapper = MovieFavouriteEntityToDomainMapper()

    @Test
    fun `map should convert FavoriteMovieEntity to Movie correctly`() {
        // Arrange
        val entity =
            MovieFavouriteEntity(
                id = 99,
                title = "Entity Fav Movie",
                posterPath = "/fav_entity.jpg",
                releaseDate = "2021-01-01",
                voteAverage = 7.0,
                overview = "Entity favorite overview",
            )

        val expected =
            Movie(
                id = 99,
                title = "Entity Fav Movie",
                posterPath = "/fav_entity.jpg",
                releaseDate = "2021-01-01",
                voteAverage = 7.0,
                overview = "Entity favorite overview",
            )

        // Act
        val result = mapper.map(entity)

        // Assert
        assertEquals(expected, result)
    }
}
