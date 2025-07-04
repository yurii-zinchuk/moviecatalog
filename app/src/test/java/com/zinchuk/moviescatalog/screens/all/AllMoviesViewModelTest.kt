package com.zinchuk.moviescatalog.screens.all

import androidx.paging.PagingData
import app.cash.turbine.test
import com.zinchuk.domain.models.Movie
import com.zinchuk.domain.usecases.AddFavoriteUseCase
import com.zinchuk.domain.usecases.GetCachedMoviesUseCase
import com.zinchuk.domain.usecases.GetFavoriteMoviesUseCase
import com.zinchuk.domain.usecases.GetPagedMoviesUseCase
import com.zinchuk.domain.usecases.RemoveFavoriteUseCase
import com.zinchuk.moviescatalog.mappers.MovieCardDataTODomainMapper
import com.zinchuk.moviescatalog.mappers.MovieDomainTOCardDataMapper
import com.zinchuk.moviescatalog.test_utils.MainDispatcherRule
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AllMoviesViewModelTest {
    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val addFavoriteUseCase = mockk<AddFavoriteUseCase>(relaxed = true)
    private val removeFavoriteUseCase = mockk<RemoveFavoriteUseCase>(relaxed = true)
    private val getFavoriteMoviesUseCase = mockk<GetFavoriteMoviesUseCase>()
    private val getPagedMoviesUseCase = mockk<GetPagedMoviesUseCase>()
    private val getCachedMoviesUseCase = mockk<GetCachedMoviesUseCase>()
    private val domainToCardMapper = mockk<MovieDomainTOCardDataMapper>()
    private val cardToDomainMapper = mockk<MovieCardDataTODomainMapper>()

    private lateinit var viewModel: AllMoviesViewModel

    private val dummyMovie =
        Movie(
            id = 1,
            title = "Sample",
            overview = "Overview",
            voteAverage = 8.0,
            posterPath = "/path.jpg",
            releaseDate = "2025-01-01",
        )

    private val dummyCard =
        MovieCardData(
            id = 1,
            title = "Sample",
            overview = "Overview",
            voteAverage = 8.0,
            posterPath = "/path.jpg",
            isFavorite = false,
            releaseDate = "2025-01-01",
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `loadMovies should emit Success state with data`() =
        runTest {
            // Arrange
            val pagingData = PagingData.from(listOf(dummyMovie))
            every { getPagedMoviesUseCase() } returns flowOf(pagingData)
            every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())
            every { domainToCardMapper.map(any(), any()) } returns dummyCard

            // Act
            viewModel =
                AllMoviesViewModel(
                    removeFavoriteUseCase,
                    addFavoriteUseCase,
                    domainToCardMapper,
                    cardToDomainMapper,
                    getFavoriteMoviesUseCase,
                    getPagedMoviesUseCase,
                    getCachedMoviesUseCase,
                )
            advanceUntilIdle()

            // Assert
            viewModel.uiState.test {
                val state = awaitItem()
                assert(state is AllMoviesUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onLoadError should emit Error state with cached data`() =
        runTest {
            // Arrange
            val cachedPagingData = PagingData.from(listOf(dummyMovie))
            val pagingData = PagingData.from(emptyList<Movie>())

            every { getCachedMoviesUseCase() } returns flowOf(cachedPagingData)
            every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())
            every { getPagedMoviesUseCase() } returns flowOf(pagingData)
            every { domainToCardMapper.map(any(), any()) } returns dummyCard

            viewModel =
                AllMoviesViewModel(
                    removeFavoriteUseCase,
                    addFavoriteUseCase,
                    domainToCardMapper,
                    cardToDomainMapper,
                    getFavoriteMoviesUseCase,
                    getPagedMoviesUseCase,
                    getCachedMoviesUseCase,
                )

            // Act
            viewModel.onLoadError()
            advanceUntilIdle()

            // Assert
            viewModel.uiState.test {
                val error = awaitItem()
                assert(error is AllMoviesUiState.Error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onToggleFavorite should call addFavorite when not favorite`() =
        runTest {
            // Arrange
            val pagingData = PagingData.from(listOf(dummyMovie))

            every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())
            every { getPagedMoviesUseCase() } returns flowOf(pagingData)
            every { cardToDomainMapper.map(dummyCard) } returns dummyMovie

            viewModel =
                AllMoviesViewModel(
                    removeFavoriteUseCase,
                    addFavoriteUseCase,
                    domainToCardMapper,
                    cardToDomainMapper,
                    getFavoriteMoviesUseCase,
                    getPagedMoviesUseCase,
                    getCachedMoviesUseCase,
                )

            // Act
            viewModel.onToggleFavorite(dummyCard)
            advanceUntilIdle()

            // Assert
            coVerify(exactly = 1) { addFavoriteUseCase(dummyMovie) }
        }

    @Test
    fun `onToggleFavorite should call removeFavorite when already favorite`() =
        runTest {
            // Arrange
            val favoriteCard = dummyCard.copy(isFavorite = true)
            val pagingData = PagingData.from(listOf(dummyMovie))

            every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())
            every { getPagedMoviesUseCase() } returns flowOf(pagingData)
            every { cardToDomainMapper.map(favoriteCard) } returns dummyMovie

            viewModel =
                AllMoviesViewModel(
                    removeFavoriteUseCase,
                    addFavoriteUseCase,
                    domainToCardMapper,
                    cardToDomainMapper,
                    getFavoriteMoviesUseCase,
                    getPagedMoviesUseCase,
                    getCachedMoviesUseCase,
                )

            // Act
            viewModel.onToggleFavorite(favoriteCard)
            advanceUntilIdle()

            // Assert
            coVerify(exactly = 1) { removeFavoriteUseCase(dummyMovie) }
        }
}
