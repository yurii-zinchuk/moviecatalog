package com.zinchuk.moviescatalog.screens.favorites

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import app.cash.turbine.test
import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.usecases.GetFavoriteMoviesPagedUseCase
import com.zinchuk.domain.usecases.RemoveFavoriteUseCase
import com.zinchuk.moviescatalog.test_utils.MainDispatcherRule
import com.zinchuk.moviescatalog.mappers.MovieCardDataTODomainMapper
import com.zinchuk.moviescatalog.mappers.MovieDomainTOCardDataMapper
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem
import com.zinchuk.moviescatalog.test_utils.MovieListItemDiffCallback
import com.zinchuk.moviescatalog.test_utils.NoopListCallback
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteMoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val removeFavoriteUseCase = mockk<RemoveFavoriteUseCase>(relaxed = true)
    private val movieDomainToCardDataMapper = mockk<MovieDomainTOCardDataMapper>()
    private val movieCardDataTODomainMapper = mockk<MovieCardDataTODomainMapper>()
    private val getFavoriteMoviesPagedUseCase = mockk<GetFavoriteMoviesPagedUseCase>()

    private lateinit var viewModel: FavouriteMoviesViewModel

    private val dummyMovie = Movie(
        id = 1,
        title = "Interstellar",
        overview = "Sci-fi epic",
        voteAverage = 9.0,
        posterPath = "poster.jpg",
        releaseDate = "2025-05-01"
    )

    private val dummyCard = MovieCardData(
        id = 1,
        title = "Interstellar",
        overview = "Sci-fi epic",
        voteAverage = 9.0,
        posterPath = "poster.jpg",
        isFavorite = true,
        releaseDate = "2025-05-01"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        every { getFavoriteMoviesPagedUseCase() } returns flowOf(PagingData.from(listOf(dummyMovie)))
        every { movieDomainToCardDataMapper.map(dummyMovie, true) } returns dummyCard

        viewModel = FavouriteMoviesViewModel(
            removeFavoriteUseCase,
            movieDomainToCardDataMapper,
            movieCardDataTODomainMapper,
            getFavoriteMoviesPagedUseCase
        )
    }

    @Test
    fun `favoriteMovies emits MovieListItems with headers`() = runTest {
        // Arrange
        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieListItemDiffCallback(),
            updateCallback = NoopListCallback(),
            mainDispatcher = StandardTestDispatcher(testScheduler),
            workerDispatcher = StandardTestDispatcher(testScheduler),
        )

        // Act
        viewModel.favoriteMovies.test {
            val pagingData = awaitItem()
            differ.submitData(pagingData)
            advanceUntilIdle()

            // Assert
            val items = differ.snapshot()
            assert(items.isNotEmpty())
            assert(items.first() is MovieListItem.Header)
            assert(items[1] is MovieListItem.Movie)
            val movieItem = items[1] as MovieListItem.Movie
            assertEquals(dummyCard, movieItem.movie)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onToggleFavorite should call removeFavoriteUseCase`() = runTest {
        // Arrange
        every { movieCardDataTODomainMapper.map(dummyCard) } returns dummyMovie

        // Act
        viewModel.onToggleFavorite(dummyCard)
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { removeFavoriteUseCase(dummyMovie) }
    }
}
