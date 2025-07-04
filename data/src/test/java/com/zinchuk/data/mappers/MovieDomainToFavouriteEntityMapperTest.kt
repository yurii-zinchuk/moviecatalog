package com.zinchuk.data.mappers

import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import com.zinchuk.domain.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDomainToFavouriteEntityMapperTest {

    private val mapper = MovieDomainToFavouriteEntityMapper()

    @Test
    fun `map should convert Movie to FavoriteMovieEntity correctly`() {
        // Arrange
        val movie = Movie(
            id = 42,
            title = "Favorite Movie",
            posterPath = "/fav_poster.jpg",
            releaseDate = "2022-02-22",
            voteAverage = 9.1,
            overview = "Favorite overview"
        )
        val expected = MovieFavouriteEntity(
            id = 42,
            title = "Favorite Movie",
            posterPath = "/fav_poster.jpg",
            releaseDate = "2022-02-22",
            voteAverage = 9.1,
            overview = "Favorite overview"
        )

        // Act
        val result = mapper.map(movie)

        // Assert
        assertEquals(expected, result)
    }
}
