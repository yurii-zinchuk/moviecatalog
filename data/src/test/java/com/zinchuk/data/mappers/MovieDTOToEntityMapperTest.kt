package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDTOToEntityMapperTest {
    private val mapper = MovieDTOToEntityMapper()

    @Test
    fun `map should convert MovieDTO to MovieEntity correctly`() {
        // Arrange
        val dto =
            MovieDTO(
                id = 1,
                title = "DTO Movie",
                posterPath = "/dto_poster.jpg",
                releaseDate = "2023-11-11",
                voteAverage = 6.5,
                overview = "DTO overview",
            )

        val expected =
            MovieEntity(
                id = 1,
                title = "DTO Movie",
                posterPath = "/dto_poster.jpg",
                releaseDate = "2023-11-11",
                voteAverage = 6.5,
                overview = "DTO overview",
            )

        // Act
        val result = mapper.map(dto)

        // Assert
        assertEquals(expected, result)
    }
}
