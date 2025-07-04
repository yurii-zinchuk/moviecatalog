package com.zinchuk.moviescatalog.utils

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WithDateHeadersTest {
    private fun movieCard(
        id: Int,
        title: String,
        releaseDate: String,
    ): MovieCardData =
        MovieCardData(
            id = id,
            title = title,
            overview = "",
            voteAverage = 8.0,
            posterPath = "",
            isFavorite = false,
            releaseDate = releaseDate,
        )

    @Test
    fun `adds headers between different months`() =
        runTest {
            // Arrange
            val jan1 = movieCard(1, "Movie A", "2025-01-10")
            val jan2 = movieCard(2, "Movie B", "2025-01-20")
            val feb = movieCard(3, "Movie C", "2025-02-01")

            val input = PagingData.from(listOf(jan1, jan2, feb))

            // Act
            val result = flowOf(input.withDateHeaders()).asSnapshot()

            // Assert
            val expected =
                listOf(
                    MovieListItem.Header("Jan 2025"),
                    MovieListItem.Movie(jan1),
                    MovieListItem.Movie(jan2),
                    MovieListItem.Header("Feb 2025"),
                    MovieListItem.Movie(feb),
                )
            assertEquals(expected, result)
        }

    @Test
    fun `no headers when dates are same month`() =
        runTest {
            val jan1 = movieCard(1, "Movie A", "2025-01-10")
            val jan2 = movieCard(2, "Movie B", "2025-01-15")

            val input = PagingData.from(listOf(jan1, jan2))
            val result = flowOf(input.withDateHeaders()).asSnapshot()

            val expected =
                listOf(
                    MovieListItem.Header("Jan 2025"),
                    MovieListItem.Movie(jan1),
                    MovieListItem.Movie(jan2),
                )
            assertEquals(expected, result)
        }

    @Test
    fun `ignores empty and invalid dates`() =
        runTest {
            val valid = movieCard(1, "Movie A", "2025-01-10")
            val invalid = movieCard(2, "Invalid", "invalid")
            val empty = movieCard(3, "Empty", "")

            val input = PagingData.from(listOf(valid, invalid, empty))
            val result = flowOf(input.withDateHeaders()).asSnapshot()

            val expected =
                listOf(
                    MovieListItem.Header("Jan 2025"),
                    MovieListItem.Movie(valid),
                    MovieListItem.Movie(invalid),
                    MovieListItem.Movie(empty),
                )
            assertEquals(expected, result)
        }

    @Test
    fun `empty list returns empty`() =
        runTest {
            val input = PagingData.empty<MovieCardData>()
            val result = flowOf(input.withDateHeaders()).asSnapshot()
            assertTrue(result.isEmpty())
        }
}
