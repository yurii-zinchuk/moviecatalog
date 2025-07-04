package com.zinchuk.data.repositories

import androidx.paging.PagingData
import app.cash.turbine.test
import com.zinchuk.data.MainDispatcherRule
import com.zinchuk.data.TestPagingSource
import com.zinchuk.data.mappers.MovieDTOToDomainMapper
import com.zinchuk.data.mappers.MovieDTOToEntityMapper
import com.zinchuk.data.mappers.MovieDomainToFavouriteEntityMapper
import com.zinchuk.data.mappers.MovieEntityToDomainMapper
import com.zinchuk.data.mappers.MovieFavouriteEntityToDomainMapper
import com.zinchuk.data.sources.local.LocalMovieDataSource
import com.zinchuk.data.sources.local.room.entities.MovieEntity
import com.zinchuk.data.sources.local.room.entities.MovieFavouriteEntity
import com.zinchuk.data.sources.remote.RemoteMovieDataSource
import com.zinchuk.domain.models.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remoteDataSource = mockk<RemoteMovieDataSource>(relaxed = true)
    private val localDataSource = mockk<LocalMovieDataSource>(relaxed = true)
    private val dtoToDomainMapper = mockk<MovieDTOToDomainMapper>()
    private val entityToDomainMapper = mockk<MovieEntityToDomainMapper>()
    private val favEntityToDomainMapper = mockk<MovieFavouriteEntityToDomainMapper>()
    private val domainToFavEntityMapper = mockk<MovieDomainToFavouriteEntityMapper>()
    private val dtoToEntityMapper = mockk<MovieDTOToEntityMapper>()

    private lateinit var repository: MovieRepositoryImpl

    private val dummyMovie = Movie(1, "Test", "Overview", "2025-01-01", 8.0, "poster")

    @Before
    fun setup() {
        repository =
            MovieRepositoryImpl(
                remoteMovieDataSource = { remoteDataSource },
                localMovieDataSource = localDataSource,
                movieDTOToDomainMapper = dtoToDomainMapper,
                movieEntityToDomainMapper = entityToDomainMapper,
                movieFavouriteEntityToDomainMapper = favEntityToDomainMapper,
                movieDomainToFavouriteEntityMapper = domainToFavEntityMapper,
                movieDTOToEntityMapper = dtoToEntityMapper,
            )
    }

    @Test
    fun `getCachedMovies should return mapped domain objects`() =
        runTest {
            // Arrange
            val entity = mockk<MovieEntity>()
            every { localDataSource.getCached() } returns TestPagingSource.from(listOf(entity))
            every { entityToDomainMapper.map(entity) } returns dummyMovie

            // Act
            val result = repository.getCachedMovies()

            // Assert
            result.test {
                val item = awaitItem()
                assertTrue(item is PagingData<Movie>)
                cancelAndIgnoreRemainingEvents()
            }

            verify { localDataSource.getCached() }
        }

    @Test
    fun `addFavorite should call local data source with mapped entity`() =
        runTest {
            val favEntity = mockk<MovieFavouriteEntity>()
            every { domainToFavEntityMapper.map(dummyMovie) } returns favEntity

            // Act
            repository.addFavorite(dummyMovie)

            // Assert
            coVerify { localDataSource.addFavorite(favEntity) }
        }

    @Test
    fun `removeFavorite should call local data source with mapped entity`() =
        runTest {
            val favEntity = mockk<MovieFavouriteEntity>()
            every { domainToFavEntityMapper.map(dummyMovie) } returns favEntity

            // Act
            repository.removeFavorite(dummyMovie)

            // Assert
            coVerify { localDataSource.removeFavorite(favEntity) }
        }

    @Test
    fun `isFavorite should return correct result`() =
        runTest {
            coEvery { localDataSource.isFavorite(1) } returns true

            val result = repository.isFavorite(1)

            assertTrue(result)
            coVerify { localDataSource.isFavorite(1) }
        }

    @Test
    fun `getFavoriteMovies should return mapped domain list`() =
        runTest {
            val entity = mockk<MovieFavouriteEntity>()
            every { localDataSource.getFavorites() } returns flowOf(listOf(entity))
            every { favEntityToDomainMapper.map(entity) } returns dummyMovie

            val result = repository.getFavoriteMovies()

            result.test {
                val item = awaitItem()
                assertEquals(listOf(dummyMovie), item)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getFavoriteMoviesPaged should return paged mapped results`() =
        runTest {
            val favEntity = mockk<MovieFavouriteEntity>()
            every { localDataSource.getFavoritesPaged() } returns TestPagingSource.from(listOf(favEntity))
            every { favEntityToDomainMapper.map(favEntity) } returns dummyMovie

            val result = repository.getFavoriteMoviesPaged()

            result.test {
                val item = awaitItem()
                assertTrue(item is PagingData<*>)
                cancelAndIgnoreRemainingEvents()
            }

            verify { localDataSource.getFavoritesPaged() }
        }
}
