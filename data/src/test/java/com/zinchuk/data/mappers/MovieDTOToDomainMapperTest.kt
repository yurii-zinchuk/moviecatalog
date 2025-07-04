package com.zinchuk.data.mappers

import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import com.zinchuk.domain.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDTOToDomainMapperTest {
    private val mapper = MovieDTOToDomainMapper()

    @Test
    fun `map should convert MovieDTO to Movie domain model correctly`() {
        // Arrange
        val dto =
            MovieDTO(
                id = 1,
                title = "Test Movie",
                posterPath = "/poster.jpg",
                releaseDate = "2024-01-01",
                voteAverage = 7.8,
                overview = "A test movie overview",
            )

        val expected =
            Movie(
                id = 1,
                title = "Test Movie",
                posterPath = "/poster.jpg",
                releaseDate = "2024-01-01",
                voteAverage = 7.8,
                overview = "A test movie overview",
            )

        // Act
        val result = mapper.map(dto)

        // Assert
        assertEquals(expected, result)
    }
}
