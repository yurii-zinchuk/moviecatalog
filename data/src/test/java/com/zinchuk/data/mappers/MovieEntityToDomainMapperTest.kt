package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.domain.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieEntityToDomainMapperTest {
    private val mapper = MovieEntityToDomainMapper()

    @Test
    fun `map should convert MovieEntity to Movie domain model correctly`() {
        // Arrange
        val entity =
            MovieEntity(
                id = 2,
                title = "Entity Movie",
                posterPath = "/entity.jpg",
                releaseDate = "2025-02-02",
                voteAverage = 8.2,
                overview = "Entity overview",
            )

        val expected =
            Movie(
                id = 2,
                title = "Entity Movie",
                posterPath = "/entity.jpg",
                releaseDate = "2025-02-02",
                voteAverage = 8.2,
                overview = "Entity overview",
            )

        // Act
        val result = mapper.map(entity)

        // Assert
        assertEquals(expected, result)
    }
}
